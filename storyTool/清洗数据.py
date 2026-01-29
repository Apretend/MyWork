import os
import re
import json
import time
from openai import OpenAI

# ================= 配置区 =================
API_KEY = "你的API_KEY"
BASE_URL = "你的API地址"
MODEL_NAME = "deepseek-chat"  # 建议使用逻辑能力强的模型

LIBRARY_DIR = "./my_novels"  # 存放书籍文件夹的根目录
OUTPUT_DIR = "./processed_data"  # 结果输出目录
# ==========================================

client = OpenAI(api_key=API_KEY, base_url=BASE_URL)


def get_chapter_files(book_path):
    """提取文件名数字序号并排序"""
    files = [f for f in os.listdir(book_path) if f.endswith('.md')]
    try:
        # 匹配文件名开头的数字，如 001, 01, 1 等
        files.sort(key=lambda x: int(re.search(r'^\d+', x).group()))
    except (AttributeError, ValueError):
        print(f"  [跳过] 文件夹 {book_path} 中部分文件未以数字开头。")
        return []
    return files


def process_chapter(book_name, file_name, content, prev_summary=""):
    """
    调用大模型解析章节逻辑并重构精华
    """
    # 提取当前章节序号
    chapter_num_match = re.search(r'^\d+', file_name)
    chapter_num = int(chapter_num_match.group()) if chapter_num_match else 0

    # 针对 1-10 章和后续章节采用不同的指令策略
    if chapter_num <= 10:
        instruction_focus = (
            "这是【黄金开篇】章节。请深度解构：\n"
            "1. 人设构建：主角的核心性格标签和动机是什么？\n"
            "2. 黄金钩子：作者是如何在章末制造悬念或期待感的？\n"
            "3. 细节保留：严禁过度压缩，保留所有具有张力的对白、环境烘托和心理描写。"
        )
    else:
        instruction_focus = (
            "这是【剧情推进】章节。请结构化整理：\n"
            "1. 逻辑骨架：本章推动了哪个主线进度？\n"
            "2. 节奏控制：剔除所有转场废话、灌水描写、无意义的属性面板。\n"
            "3. 精华提炼：只保留核心爽点、高光台词和关键剧情转折。"
        )

    system_prompt = "你是一位网文界的王牌主编，擅长从原始小说中抽离创作算法，并将其实例化为高质量的训练教材。"
    user_content = f"""
【书籍信息】：{book_name}
【当前章节】：{file_name}
【前情回顾】：{prev_summary}

【章节原文】：
{content}

【处理要求】：
{instruction_focus}

请将以上内容整理为JSON格式，用于微调大模型。输出字段要求：
1. instruction: 简述本章的创作教学目标。
2. input: 简述前情背景与本章的任务挑战。
3. output: 包含【创作逻辑分析】（分析本章为什么这么写）和【精华重现】（保留神韵的改写内容）。
4. summary: 50字内的本章梗概（用于衔接下一章）。
"""

    try:
        response = client.chat.completions.create(
            model=MODEL_NAME,
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": user_content}
            ],
            response_format={"type": "json_object"}
        )
        return json.loads(response.choices[0].message.content)
    except Exception as e:
        print(f"    API调用失败: {e}")
        return None


def main():
    if not os.path.exists(OUTPUT_DIR):
        os.makedirs(OUTPUT_DIR)

    # 扫描所有书籍文件夹
    if not os.path.exists(LIBRARY_DIR):
        print(f"错误：找不到目录 {LIBRARY_DIR}")
        return

    books = [d for d in os.listdir(LIBRARY_DIR) if os.path.isdir(os.path.join(LIBRARY_DIR, d))]
    print(f">>> 检测到库中共有 {len(books)} 本书。")

    master_data = []

    for book in books:
        book_path = os.path.join(LIBRARY_DIR, book)
        chapters = get_chapter_files(book_path)

        if not chapters:
            continue

        print(f"\n[开始处理书籍]: {book} (共{len(chapters)}章)")
        prev_summary = "新书开篇"

        for ch_file in chapters:
            print(f"  正在加工: {ch_file}")
            file_full_path = os.path.join(book_path, ch_file)

            with open(file_full_path, 'r', encoding='utf-8') as f:
                content = f.read()

            # 处理每一章
            result = process_chapter(book, ch_file, content, prev_summary)

            if result:
                # 存入最终数据集
                master_data.append({
                    "instruction": result.get('instruction', ''),
                    "input": result.get('input', ''),
                    "output": result.get('output', '')
                })
                # 更新摘要供下一章使用
                prev_summary = result.get('summary', '剧情继续推进')

            # 频率控制（可选）
            time.sleep(0.5)

    # 保存最终结果
    output_path = os.path.join(OUTPUT_DIR, "master_finetune_data.jsonl")
    with open(output_path, 'w', encoding='utf-8') as f:
        for entry in master_data:
            f.write(json.dumps(entry, ensure_ascii=False) + "\n")

    print(f"\n>>> 处理完成！结果已保存至: {output_path}")


if __name__ == "__main__":
    main()