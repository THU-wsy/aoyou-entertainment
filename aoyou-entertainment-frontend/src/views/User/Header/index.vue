<template>
    <div class="header_container">
        <div class="header_left">
            遨游桌游密室平台
        </div>
        <!-- 右侧是昵称 -->
        <div class="header_right">
            <!-- 昵称 -->
            <div>
                <el-dropdown>
                    <span class="el-dropdown-link">
                    {{ nickname }}
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                    </span>
                    <template #dropdown>
                    <el-dropdown-menu>
                        <!-- <el-dropdown-item>个人中心</el-dropdown-item> -->
                        <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="Header">
    import { useRouter } from 'vue-router';
    import {logout} from '@/api/auth/logout'
    import { getUserInfo } from '@/api/auth/userInfo';
    import { ElMessage } from 'element-plus';
    
    const nickname = ref(getUserInfo('userInfo'));
    const router = useRouter();
    
    // 退出登录的方法
    function handleLogout() {
        // 调用logout方法
        logout().then((res) => {
            if (res.code == 200) {
                // 清除用户的相关数据
                window.localStorage.clear();
                window.sessionStorage.clear();
                // 跳转到登录页面
                ElMessage.success('退出登录成功');
                router.push('/login');
            }
        })
    }

</script>

<style lang="scss" scoped>
.header_container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    height: 100%;

    .header_left { // 制作文字阴影效果
	    color: #b833ff;
        text-shadow: 0 8px 10px #db66ff;
        font-weight: bolder;
        text-align: center;
    }

    .header_right {
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-sizing: border-box;
        .avatar {
            cursor: pointer;
            margin-right: 12px;
            .avatar_img {
                width: 30px;
                height: 30px;
            }
        }
        .el-dropdown-link {
            cursor: pointer;
            display: flex;
            align-items: center;
            border: none;
            outline: none; // 去除边框
        }
    }
}
</style>