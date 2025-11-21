# tools/skills_basic.py
import datetime
import os
from tools.registry import registry

# ==========================================
# 在这里写你的自定义 Python 函数
# ==========================================

@registry.register
def get_current_time():
    """
    获取当前的系统时间。
    """
    return datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

@registry.register
def calculate(expression: str):
    """
    计算数学表达式的结果。
    参数 expression: 字符串形式的数学公式，如 '12 * 34'
    """
    try:
        # 注意：eval 在生产环境中不安全，但在个人本地 Agent 中是最高效的计算器
        return str(eval(expression))
    except Exception as e:
        return f"计算错误: {e}"

@registry.register
def write_file(filename: str, content: str):
    """
    创建一个文件并写入内容。
    参数 filename: 文件名 (包含扩展名)
    参数 content: 要写入的文本内容
    """
    try:
        with open(filename, 'w', encoding='utf-8') as f:
            f.write(content)
        return f"成功写入文件: {os.path.abspath(filename)}"
    except Exception as e:
        return f"写入失败: {e}"