<template>
  <div class="home">
    <el-card class="login-card">
      <h2>登录系统</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginForm">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username"
            prefix-icon="el-icon-user"
            placeholder="请输入账号">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password"
            prefix-icon="el-icon-lock"
            type="password"
            placeholder="请输入密码">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          if (this.loginForm.username === 'admin' && this.loginForm.password === 'admin') {
            this.$message.success('登录成功')
            this.$router.push('/content')
          } else {
            this.$message.error('账号或密码错误')
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.home {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.login-card {
  width: 400px;
}

.login-card h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #303133;
}

.el-form-item {
  margin-bottom: 25px;
}
</style> 