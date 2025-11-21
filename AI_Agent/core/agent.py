# core/agent.py
import json
import traceback
from core.llm_client import LLMClient
from tools.registry import registry
import tools.skills_basic # 注册工具
import config



class Agent:
    def __init__(self):
        self.llm = LLMClient()
        self.history = []  # 短期记忆
        self._init_system_prompt()

    def _init_system_prompt(self):
        """初始化人设和工具说明书"""
        tools_desc = registry.get_prompt_description()

        prompt = f"""
            你是一个智能助手。
            【可用工具】:
            {tools_desc}
            
            【规则】:
            1. 如需使用工具，只输出 JSON 格式：
            ```json
            {{ "tool": "name", "args": {{...}} }}
            输出 JSON 后等待结果。
            """
        self.history.append({"role": "system", "content": prompt.strip()})
    def chat_generator(self, user_input):
        """
        [流式版] 交互主入口
        yield 返回格式: {"type": "think"|"answer"|"system", "content": "..."}
        """
        self.history.append({"role": "user", "content": user_input})
        turn_count = 0
        while turn_count < config.MAX_THOUGHT_STEPS:
            turn_count += 1

            # === 1. LLM 生成阶段 (流式) ===
            full_response = ""
            is_thinking_block = False  # 标记当前是否在生成代码块

            # 调用 LLM 流式接口
            stream = self.llm.stream_chat(self.history)

            for chunk in stream:
                full_response += chunk

                # [逻辑] 简单的启发式判断：检测是否在输出 JSON
                # 如果包含 ```json，说明开始思考/调用工具
                if "```json" in full_response and not is_thinking_block:
                    is_thinking_block = True

                # 根据状态决定输出类型
                msg_type = "think" if is_thinking_block else "answer"

                yield {"type": msg_type, "content": chunk}

            # 记录完整回复到历史
            self.history.append({"role": "assistant", "content": full_response})

            # === 2. 工具执行阶段 ===
            tool_data = self._extract_json(full_response)

            if tool_data:
                # 提取到 JSON，说明上一轮全是思考，进入执行环节
                tool_name = tool_data.get("tool")
                args = tool_data.get("args", {})

                # 发送系统通知
                yield {"type": "system", "content": f"\n[系统] 正在调用工具: {tool_name} {args}...\n"}

                # 执行工具
                result = self._execute_tool(tool_name, args)

                yield {"type": "system", "content": f"[系统] 执行结果: {result}\n"}

                # 将结果存入历史，进入下一轮思考
                self.history.append({
                    "role": "user",
                    "content": f"工具执行结果: {result}"
                })
            else:
                # 没有提取到 JSON，说明是纯文本回复，任务结束
                break

    def _extract_json(self, text):
        """提取 JSON (增强版，过滤不需要的字符)"""
        try:
            if "json" in text:
                start = text.find("json") + 7
                end = text.find("```", start)
                return json.loads(text[start:end].strip())
            if text.strip().startswith("{") and text.strip().endswith("}"):
                return json.loads(text.strip())
        except:
            pass
            return None

    def _execute_tool(self, name, args):
        tool_info = registry.get_tool(name)

        if not tool_info: return f"找不到工具 {name}"
        try:
            return tool_info
            'func'
        except Exception as e:
            return f"错误: {e}"

    def chat(self, user_input):
        """
        处理用户输入的主入口。包含“思考-行动”循环。
        """
        # 1. 接收用户输入
        self.history.append({"role": "user", "content": user_input})
        turn_count = 0

        while turn_count < config.MAX_THOUGHT_STEPS:
            turn_count += 1

            # 2. 向大脑提问
            print(f"  >> [思考中...] (第 {turn_count} 轮)")
            response_text = self.llm.one_chat(self.history)

            # 3. 记录大脑的回复
            self.history.append({"role": "assistant", "content": response_text})

            # 4. 解析是否需要行动 (提取 JSON)
            tool_data = self._extract_json(response_text)

            if tool_data:
                # --- 进入行动模式 ---
                tool_name = tool_data.get("tool")
                args = tool_data.get("args", {})

                print(f"  >> [调用工具] {tool_name} | 参数: {args}")

                # 执行函数
                result = self._execute_tool(tool_name, args)

                # --- 观察结果 ---
                print(f"  >> [工具反馈] {result}")

                # 将结果作为 User 消息返回给大脑，让它继续处理
                self.history.append({
                    "role": "user",
                    "content": f"工具 '{tool_name}' 的运行结果: {result}"
                })
                # 循环继续，Agent 会看到结果并决定下一步
            else:
                # --- 纯对话模式 ---
                # 如果没有提取到 JSON，说明 Agent 认为任务结束或只是在闲聊
                return response_text

        return "错误：思考次数超限，强制停止。"

    def _extract_json(self, text):
        """从文本中提取 JSON 块"""
        try:
            if "json" in text:
                start = text.find("json") + 7
                end = text.find("```", start)
                return json.loads(text[start:end].strip())
            # 兼容大模型直接输出 JSON 的情况
            if text.strip().startswith("{") and text.strip().endswith("}"):
                return json.loads(text.strip())
        except:
            pass
        return None
    def _execute_tool(self, name, args):
        """在注册表中查找并执行工具"""
        tool_info = registry.get_tool(name)
        if not tool_info:
            return f"错误：找不到名为 {name} 的工具"

        try:
            func = tool_info['func']
            # 使用 **args 自动将字典解包为函数参数
            return func(**args)
        except Exception as e:
            traceback.print_exc()
            return f"工具执行出错: {str(e)}"