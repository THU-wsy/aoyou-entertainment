import http from "@/utils/http";

// 登录接口
export function login(data) {
    return http({
        url: 'auth/login',
        method: 'POST',
        data: data
    }) 
}