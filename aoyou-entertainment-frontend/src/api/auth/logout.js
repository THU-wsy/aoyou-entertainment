import http from "@/utils/http";

// 退出登录接口
export function logout() {
    return http({
        url: 'auth/logout',
        method: 'POST'
    })
}