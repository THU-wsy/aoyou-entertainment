import router from '@/router'
import { getToken } from '@/api/auth/token';
import { getUserInfo } from '@/api/auth/userInfo';
import { ElMessage } from 'element-plus';

// 全局前置路由守卫
router.beforeEach((to, from, next) => {
    // 判断该路由是否需要登录权限
    if (to.meta.requireAuth) {
        if (getToken('userToken') && getUserInfo('userInfo'))
            next();
        else {
            ElMessage.warning('请先登录！');
            next('/login');
        }
    } else {
        next();
    }
})