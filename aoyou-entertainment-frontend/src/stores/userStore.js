export const useUserStore = defineStore('user', {
    state: () => ({ 
        nickname: undefined
    }),
    getters: {

    },
    actions: {
        setUserInfo(data) {
            this.nickname = data.nickname;
        }
    },
    // 使用持久化
    persist: {
    	enabled: true,
        storage: localStorage,
        key: 'userInfo',
        paths: ['nickname']
    }
})