# core/llm_client.py
from openai import OpenAI
import config

class LLMClient:
    def __init__(self):
        self.client = OpenAI(
            api_key=config.API_KEY,
            base_url=config.BASE_URL
        )

    def one_chat(self, messages):
        """普通非流式调用 (旧方法保留用于兼容)"""
        try:
            response = self.client.chat.completions.create(
                model=config.MODEL_NAME,
                messages=messages,
                temperature=0.7
            )
            return response.choices[0].message.content
        except Exception as e:
            print(f"[LLM Error] {e}")
            return ""

    def stream_chat(self, messages):
        """
        [新增] 流式调用
        返回一个生成器，每次 yield 一个字符片段
        """
        try:
            stream = self.client.chat.completions.create(
                model=config.MODEL_NAME,
                messages=messages,
                temperature=0.7,
                stream=True  # 开启流式
            )
            for chunk in stream:
                content = chunk.choices[0].delta.content
                if content:
                    yield content
        except Exception as e:
            yield f"[LLM Error] {str(e)}"