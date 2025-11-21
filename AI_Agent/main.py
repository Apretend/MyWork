# main.py
import sys
from colorama import init, Fore, Style
from core.agent import Agent

# 初始化颜色库
init(autoreset=True)


def print_stream(text, color=Fore.WHITE):
    """辅助函数：不换行打印"""
    sys.stdout.write(color + text)
    sys.stdout.flush()


def main():
    print(Style.BRIGHT + "====== AI Agent v0.2 (流式增强版) ======")
    my_agent = Agent()

    while True:
        try:
            q = input(Fore.RESET + "\n\n👤 你: ")
            if not q: continue
            if q.lower() in ['exit', 'quit']: break

            print("🤖 Agent: ", end="")

            # 获取生成器
            generator = my_agent.chat_generator(q)

            # 实时循环处理流式数据
            for event in generator:
                msg_type = event["type"]
                content = event["content"]

                if msg_type == "think":
                    # 思考过程/代码生成用灰色显示
                    print_stream(content, Fore.LIGHTBLACK_EX)

                elif msg_type == "system":
                    # 系统工具执行日志用黄色显示
                    print_stream(content, Fore.YELLOW)

                elif msg_type == "answer":
                    # 最终回复用青色显示
                    print_stream(content, Fore.CYAN)

        except KeyboardInterrupt:
            print("\n程序已停止。")
            break


if __name__ == "__main__":
    main()