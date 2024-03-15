<template>
  <!-- 根div -->
  <div class="login_container">
      <!-- 登录表单 -->
      <div class="login_form">
          <h3 class="title">遨游桌游密室平台</h3>
          <div style="margin-left: 50%;">
            <RouterLink to="/" style="color: blue;">进入首页</RouterLink>
          </div>
          <br>
          <el-form ref="formRef" :model="loginForm" :rules="rules"> 
              <!-- 用户名 -->
              <el-form-item prop="account">
                  <el-input v-model="loginForm.account" placeholder="请输入账号">
                      <template #prefix>
                          <el-icon class="el-input__icon"><User /></el-icon>
                      </template>
                  </el-input>
              </el-form-item>
              <!-- 密码 -->
              <el-form-item prop="password">
                  <el-input v-model="loginForm.password" type="password" placeholder="请输入密码">
                      <template #prefix>
                          <el-icon class="el-input__icon"><Lock /></el-icon>
                      </template>                        
                  </el-input>
              </el-form-item>

              <div class="rememberMe">
                  <!-- 记住我 -->
                  <el-checkbox v-model="loginForm.rememberMe" label="记住我" size="large" />
                  <!-- 忘记密码 -->
                  <el-text class="forgetpass mx-1" type="primary">忘记密码?</el-text>
              </div>
              <!-- 分割线 -->
              <el-divider>其他登录方式</el-divider>

              <!-- 其他登录方式 -->
              <div class="other_login">
                  <el-icon class="other_login_item"><ChromeFilled /></el-icon>
                  <el-icon class="other_login_item"><ElemeFilled /></el-icon>
                  <el-icon class="other_login_item"><WindPower /></el-icon>
              </div>

              <!-- 按钮 -->
              <el-form-item>
                  <el-button style="width: 100%;" type="primary" @click="handleLogin">登录</el-button>
              </el-form-item>
          </el-form>
      </div>
  </div>
</template>

<script setup>
  import {ref} from 'vue'
  import {login} from '@/api/auth/login'
  import { ElMessage } from 'element-plus'
import router from '@/router';
  // 声明表单绑定值
  const loginForm = ref({
      account: undefined,
      password: undefined,
      rememberMe: undefined
  })

  // 校验规则
  const rules = {
    account: [
      {required: true, message: '用户名不能为空', trigger: 'blur'}
    ],
    password: [
      {required: true, message: '密码不能为空', trigger: 'blur'},
      {min: 6, max: 14, message: '密码长度要求6-14个字符', trigger: 'blur'}
    ]
  }
  const formRef = ref(null);

  // 声明方法
  function handleLogin() {
      formRef.value.validate(async (valid)=>{
        // 所有内容都通过校验valid才为true
        if (!valid) return; 

        await login(loginForm.value).then(res => {
          if (res.code == 200) {
            ElMessage.success('登录成功');
            // 跳转页面
            router.replace({path: '/'});
          }
        })
      })
  }
</script>

<style lang="scss" scoped>
.login_container {
  // 背景图（放一张背景图到@/assets/images/login-bg.jpg）
  background-image: url('@/assets/images/login-bg.jpg');
  background-size: 100%;
  height: 100vh;
  display: flex;
  justify-content: flex-end;

  .login_form {
      display: flex;
      justify-content: center;
      align-items: center;
      // 设置换行
      flex-direction: column;
      // 登录表单所占宽度
      width: 40%;
      background-color: #fff;

      .title {
          margin-bottom: 20px;
      }
      .rememberMe {
          display: flex;
          justify-content: space-between;
          align-items: center;
          .forgetpass {
              cursor: pointer;
          }
      }
      .other_login {
          display: flex;
          justify-content: center;
          margin-bottom: 20px;
          .other_login_item {
              margin-left: 30px;
              margin-right: 30px;
              cursor: pointer;
          }
      }
  }
}

.el-form {
  width: 60%;
}
.el-form-item {
  width: 100%;
}
</style>