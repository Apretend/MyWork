<template>
  <div class="loginPage">
    <div class="dataShowPlace"></div>
    <div class="loginPlace">
      <el-form class="loginForm" ref="loginForm" :model="loginFormData" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="loginFormData.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="loginFormData.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loginTo">登录</el-button>
          <el-button @click="registerFormData">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script setup lang="ts">
  import {ref, reactive} from 'vue'
  import {useRouter} from 'vue-router'
  import { ElMessage } from 'element-plus'
  import axios from 'axios'

  const responseUrl = ref('http://127.0.0.1:8081');

  const loginFormData = reactive({
    username: '',
    password: ''
  });

  const router = useRouter();

  const loginTo = async () => {
    try {
      const response = await axios.post('/api/userInfo/login', loginFormData);
      console.log(response);
      
      if (response.data.code === 0) {
        localStorage.setItem('userId', response.data.data.id.toString());
        localStorage.setItem('username', response.data.data.username);
        await router.push('/content');
      } else {
        ElMessage.error(response.data.message);
      }
    } catch (e) {
      console.log("登陆失败：", e);
      
    }
  }
</script>
<style lang="scss" scoped>
  @media (min-width: 1024px) {
    .loginPage {
      height: 100vh;
      width: 100vw;
      background: white;
      display: flex;
      align-items: center;
      flex-direction: row;
      justify-content: center;
    }
    .dataShowPlace {
      height: 100%;
      width: 900px;
      background: white;
    }
    .loginPlace {
      height: 100%;
      width: 550px;
      background: #F3F4F6;
      display: flex;
      align-items: center;
      justify-content: center;

      .loginForm {
        width: 400px;
        margin-top: -200px;
      }

    }

  }

</style>






