export const useMenuStore = defineStore('menu', {
    state: () => ({
        tabList: [], // 所有的tab
        activeTab: null // 当前选中的tab
    }),
    actions: {
        // 设置tabList
        setTabList(data) {
            this.tabList.push(data);
        },
        // 删除tabList（根据path）
        delTabList(path) {
            this.tabList = this.tabList.filter(item => {
                return item.path != path;
            })
        },
        // 设置activeTab
        setActive(path) {
            this.activeTab = path;
        }
    },
    persist: {
    	enabled: true,
        storage: localStorage,
        key: 'useMenu',
        paths: ['tabList', 'activeTab']
    }
})