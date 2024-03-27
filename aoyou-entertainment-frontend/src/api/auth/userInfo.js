// 存储用户信息
export function setUserInfo(key, value) {
    return localStorage.setItem(key, value);
}

// 获取用户信息
export function getUserInfo(key) {
    return localStorage.getItem(key);
}
