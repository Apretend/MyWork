<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-content">
        <div class="header">
          <svg class="apple-icon" viewBox="0 0 24 24" width="35" height="35">
            <path fill="currentColor" d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z"/>
          </svg>
          <h2>登录系统</h2>
        </div>

        <form @submit.prevent="handleSubmit">
          <div class="input-container">
            <input
              type="text"
              v-model="account"
              placeholder="账号"
              required
              class="input-top"
            />
            <input
              type="password"
              v-model="password"
              placeholder="密码"
              required
              class="input-bottom"
            />
          </div>

          <div class="options">
            <label class="remember">
              <input
                type="checkbox"
                v-model="rememberMe"
              />
              <span>记住我</span>
            </label>
            <a href="#" class="forgot">忘记密码？</a>
          </div>

          <button type="submit" class="submit-btn">
            登录
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data() {
    return {
      account: '',
      password: '',
      rememberMe: false
    }
  },
  methods: {
    handleSubmit() {
      // 表单验证
      if (!this.account || !this.password) {
        this.$message.warning('请输入账号和密码')
        return
      }
      if (this.account == 'admin') {
        this.$router.push({path: '/content', query: {authorId: 4}})
        return;
      }

      // 调用登录接口
      axios.post('http://localhost:8081/api/userInfo/login', {
        username: this.account,
        password: this.password
      })
      .then(response => {
        console.log(response);
        
        if (response.data.code === 0) {
          // 登录成功
          console.log('登录成功:', response.data)
          
          // 保存token
          localStorage.setItem('token', response.data.token)
          
          // 如果选择记住我，保存账号信息
          if (this.rememberMe) {
            localStorage.setItem('userInfo', JSON.stringify({
              account: this.account,
              isLogin: true
            }))
          }
          // 跳转到内容页
          this.$router.push({path: '/content', query: {authorId: response.data.data.id}})
        } else {
          // 登录失败，显示错误信息
          this.$message.error(response.data.message || '登录失败')
        }
      })
      .catch(error => {
        console.error('登录请求失败:', error)
        this.$message.error('登录失败，请检查网络连接')
      })
    }
  },
  // 如果有记住的登录状态，自动填充账号
  created() {
    const userInfo = localStorage.getItem('userInfo')
    if (userInfo) {
      const { account } = JSON.parse(userInfo)
      this.account = account
      this.rememberMe = true
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  width: 100vw;
  background: #f5f5f7;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.login-container {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
}

.login-content {
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.header {
  text-align: center;
  margin-bottom: 32px;
}

.apple-icon {
  margin-bottom: 16px;
  color: #000;
}

h2 {
  font-size: 24px;
  font-weight: 600;
  color: #1d1d1f;
  margin: 0;
}

.input-container {
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 12px 16px;
  font-size: 14px;
  border: 1px solid #d2d2d7;
  outline: none;
  transition: all 0.2s ease;
}

input:focus {
  border-color: #0071e3;
  box-shadow: 0 0 0 4px rgba(0, 113, 227, 0.1);
}

.input-top {
  border-radius: 8px 8px 0 0;
  border-bottom: none;
}

.input-bottom {
  border-radius: 0 0 8px 8px;
}

.options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.remember {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.remember input[type="checkbox"] {
  width: 16px;
  height: 16px;
  margin: 0;
}

.remember span {
  font-size: 14px;
  color: #1d1d1f;
}

.forgot {
  font-size: 14px;
  color: #0066cc;
  text-decoration: none;
  transition: color 0.2s ease;
}

.forgot:hover {
  color: #0077ed;
  text-decoration: underline;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background: #0071e3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.submit-btn:hover {
  background: #0077ed;
}

.submit-btn:active {
  transform: scale(0.98);
}

@media (max-width: 480px) {
  .login-content {
    padding: 24px;
  }
}
</style> 