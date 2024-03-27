<template>
    <div>
        <el-menu
            background-color="#304157"
            text-color="#b1becd"
            active-text-color="#336cab"
        >
            <!-- 遍历菜单 -->
            <template v-for="menu in menuList" :key="menu.path">
                <el-menu-item :index="menu.path" @click="handleRouter(menu)">
                    <svg-icon
                        v-if="menu.icon"
                        slot="prefix"
                        :name="menu.icon" 
                        width="18px" 
                        height="18px"
                    />
                    <span class="spanStyle">{{ menu.menuName }}</span>
                </el-menu-item>
            </template>
        </el-menu>
    </div>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    import { useMenuStore } from '@/stores/menuStore';
    const menuStore = useMenuStore();
    const router = useRouter();
    const menuList = ref([
        {menuName: '首页', path: '/', icon: 'home'},
        {menuName: '个人信息', path: '/user/info', icon: 'account'},
        {menuName: '我的店铺', path: '/user/game', icon: 'menu'},
        {menuName: '上架商品', path: '/user/shopping', icon: 'shopping'}
    ])
    
    // 切换路由
    function handleRouter(menu) {
        if (menu.path == '/') {
            router.push('/');
            return;
        }

        // 向tabList中添加数据，已经添加过的tab就不需要再添加了
        // tab的结构：{title: '我的店铺', path: '/user/game'}
        let hasNode = menuStore.tabList.filter(item => item.path == menu.path);
        if (hasNode == null || hasNode.length == 0) {
            let data = {title: menu.menuName, path: menu.path};
            menuStore.setTabList(data);
        }
        // 修改activeTab的值
        menuStore.setActive(menu.path);
        router.push(menu.path);
    }
</script>

<style lang="scss" scoped>
.spanStyle {
    margin-left: 10px;
}
</style>