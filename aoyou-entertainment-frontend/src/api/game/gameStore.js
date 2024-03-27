import http from "@/utils/http";

// 分页查询店铺的基本信息
export function queryGameStore(data) {
    return http({
        url: 'game/info/list',
        method: 'POST',
        data: data
    }) 
}

// 查询某一店铺的具体信息
export function getGameStoreDetail(id) {
    return http.get(`game/info/detail/${id}`);
}