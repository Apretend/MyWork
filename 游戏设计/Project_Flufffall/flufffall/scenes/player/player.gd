extends CharacterBody2D

# 玩家移动速度
@export var speed: float = 200.0

func _physics_process(delta: float) -> void:
	# 获取输入方向（支持上下左右）
	var input_vector = Vector2.ZERO
	input_vector.x = Input.get_action_strength("ui_right") - Input.get_action_strength("ui_left")
	input_vector.y = Input.get_action_strength("ui_down") - Input.get_action_strength("ui_up")

	# 归一化方向（防止斜向移动过快）
	if input_vector.length() > 0:
		input_vector = input_vector.normalized()

	# 设置速度并移动
	velocity = input_vector * speed
	move_and_slide()
