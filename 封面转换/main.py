from PIL import Image
import os


def convert_png_to_jpg(png_path, jpg_path, background_color=(255, 255, 255)):
    try:
        with Image.open(png_path) as img:
            # 如果有透明通道，则合成到指定背景色上
            if img.mode in ('RGBA', 'LA') or (img.mode == 'P' and 'transparency' in img.info):
                background = Image.new("RGB", img.size, background_color)
                background.paste(img, mask=img.split()[-1])
                img = background
            else:
                img = img.convert("RGB")  # 确保为 RGB 模式

            # 保存为 JPG，quality 设置高一些（95 接近无损）
            img.save(jpg_path, "JPEG", quality=95)
            print(f"✅ 成功转换: {png_path} -> {jpg_path}")
    except Exception as e:
        print(f"❌ 转换失败 {png_path}: {e}")


if __name__ == "__main__":
    # 获取当前脚本所在目录并构建图片路径
    script_dir = os.path.dirname(os.path.abspath(__file__))
    image_dir = os.path.join(script_dir, "Image")

    # 示例PNG文件名
    png_file_name = "外卖修仙：从送丹药到无敌1.png"
    png_file_path = os.path.join(image_dir, png_file_name)

    if not os.path.isfile(png_file_path):
        print(f"提供的文件不是有效的 .png 文件或不存在: {png_file_path}")
        sys.exit(1)

    jpg_file_name = os.path.splitext(png_file_name)[0] + ".jpg"
    jpg_file_path = os.path.join(image_dir, jpg_file_name)

    convert_png_to_jpg(png_file_path, jpg_file_path)