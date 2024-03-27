import http from "@/utils/http"

// 分页查询该用户的所有店铺
export function listGameStoreSelf(data) {
    return http({
        url: 'game/list',
        method: "POST",
        data: data
    })
}

// 新增店铺
export function addGameStore(data) {
    return http({
        url: 'game/detail',
        method: "POST",
        data: data
    })
}

// 修改店铺
export function updateGameStore(data) {
    return http({
        url: 'game/detail',
        method: "PUT",
        data: data
    })
}

// 删除店铺，传数组
export function removeGameStore(ids) {
    return http({
        url: 'game/detail',
        method: "DELETE",
        data: ids
    })
}