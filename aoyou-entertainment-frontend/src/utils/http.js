import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'
import { getToken, removeToken } from '@/api/auth/token';

// 创建axios实例
const http = axios.create({
  // 根请求地址
  baseURL: 'http://127.0.0.1:11110/',
  withCredentials: false, // 用于配置请求接口跨域时是否需要凭证
  timeout: 30000 // 超时时间，单位ms
})

// 配置请求头的参数类型和编码格式
axios.defaults.headers['Content-Type'] = 'application/json?charset=utf-8'

// 配置请求的拦截器
http.interceptors.request.use(config => {
  // 在请求头添加token, 判断是否需要发送token
  if (getToken('userToken')) {
    // 添加Bearer 前缀
    config.headers['Authorization'] = 'Bearer ' + getToken('userToken');
  }
  return config;
}, e => Promise.reject(e))

// 配置响应拦截器
http.interceptors.response.use((response) => {
  // 判断响应码，后端返回的数据是code, data, msg
  let {msg, code} = response.data;
  
  if (code == null || code == 200) {
      return response.data;
  } else if (code == 401) {
      ElMessage.error(msg);
      // 需要重新登陆，清除用户的相关数据，然后跳转到登录页面
      window.sessionStorage.clear();
      window.localStorage.clear();
      router.push('/login');
  } else {
      ElMessage.error(msg);
  }
  return Promise.reject(msg);
}, (error) => {
  // 出现异常
  ElMessage.error(error);
  window.sessionStorage.clear();
  window.localStorage.clear();
  router.push('/login');
  return Promise.reject(error);
})


export default http