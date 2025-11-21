# tools/registry.py
import inspect


class ToolRegistry:
    """
    工具注册管理器
    作用：存储所有可用的工具函数，并生成给大模型看的说明书。
    """

    def __init__(self):
        self._tools = {}

    def register(self, func):
        """
        装饰器函数。
        用法：@registry.register
        """
        func_name = func.__name__
        # 获取文档字符串作为工具描述
        doc = func.__doc__.strip() if func.__doc__ else "该工具无描述"
        # 获取参数签名
        sig = str(inspect.signature(func))

        self._tools[func_name] = {
            "func": func,
            "description": doc,
            "signature": sig
        }
        return func

    def get_tool(self, name):
        """根据名称获取工具函数"""
        return self._tools.get(name)

    def get_prompt_description(self):
        """
        生成 System Prompt 中所需的工具列表文本
        """
        descriptions = []
        for name, info in self._tools.items():
            desc = f"- {name}{info['signature']}: {info['description']}"
            descriptions.append(desc)
        return "\n".join(descriptions)


# 创建一个全局实例供其他模块使用
registry = ToolRegistry()