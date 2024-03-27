import '@/styles/common.scss'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
import router from './router'

import 'element-plus/theme-chalk/el-loading.css';
import 'element-plus/theme-chalk/el-message.css';
import 'element-plus/theme-chalk/el-message-box.css';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import SvgIcon from '@/components/SvgIcon/index.vue';
import 'virtual:svg-icons-register';
import '@/router/permission'

const app = createApp(App)
app.component('svg-icon', SvgIcon);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.use(router)
app.mount('#app')
