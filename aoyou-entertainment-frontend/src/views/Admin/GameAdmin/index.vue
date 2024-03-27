<template>
    <!-- 头部搜索框 -->
    <el-form :model="queryForm" :inline="true">
        <el-form-item label="店铺名称">
            <el-input v-model="queryForm.name" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleQuery">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
        </el-form-item>
    </el-form>

    <!-- 功能按钮 -->
    <el-row :gutter="4">
        <el-col :span="6">
            <el-button type="primary" @click="handleAdd">新增</el-button>
            <el-button type="danger" @click="handleRemove(0)">删除</el-button>
        </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table :data="gameStoreList" style="width: 100%" row-key="id" default-expand-all @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" width="55" />
        <el-table-column prop="name" label="店铺名称" width="300" />
        <el-table-column prop="city" label="所在城市" width="150" />
        <el-table-column prop="star" label="点赞数" width="150" />
        <el-table-column prop="address" label="详细地址" width="400" />
        <el-table-column label="操作" width="100">
            <template #default="scope">
                <el-button link type="success" size="small" @click="handleEdit(scope.row.id)">修改</el-button>
                <el-button link type="danger" size="small" @click="handleRemove(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination_container">
        <el-pagination v-model:current-page="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
            layout="prev, pager, next, jumper" :total="total"
            @current-change="handleCurrentChange" />
    </div>

    <!-- 新增和修改的弹窗 -->
    <el-dialog v-model="gameFormShow" :title="formTitle" width="50%" :before-close="handleClose">
        <!-- 表单 -->
        <el-form :model="form" label-width="120px">

            <el-row>
                <el-col :span="12">
                    <el-form-item label="店铺名称" prop="form.name">
                        <el-input v-model="form.name" placeholder="请输入店铺名称"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="所在城市" prop="form.city">
                        <el-select v-model="form.city" class="m-2" placeholder="请选择所在城市">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
           
            <el-row>
                <el-col :span="12">
                    <el-form-item label="店铺点赞" prop="form.star">
                        <el-input-number :min="0" v-model="form.star"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="详细地址" prop="form.address">
                        <el-input v-model="form.address" placeholder="请输入详细地址"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="人均价格" prop="form.price">
                        <el-input-number :min="0" v-model="form.price"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注" prop="form.detail">
                        <el-input v-model="form.detail" placeholder=""/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-form-item label="店铺图片" prop="form.picture">
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:11110/game/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        :headers="headers"
                        name="picture"
                    >
                        <img v-if="form.picture" :src="form.picture" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                    </el-upload>
                </el-form-item>
            </el-row>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="gameFormShow = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
    import { ElMessage, ElMessageBox  } from 'element-plus';
    import { Plus } from '@element-plus/icons-vue';
    import { listGameStoreSelf, addGameStore, updateGameStore, removeGameStore } from '@/api/game/gameAdmin';
    import { getGameStoreDetail } from '@/api/game/gameStore';

    /* 数据 */
    // 所在地区数据
    const options = ref([
        {label: "上海", value: "上海"},
        {label: "北京", value: "北京"},
        {label: "天津", value: "天津"},
        {label: "重庆", value: "重庆"},
        {label: "河北", value: "河北"},
        {label: "山西", value: "山西"},
        {label: "辽宁", value: "辽宁"},
        {label: "吉林", value: "吉林"},
        {label: "黑龙江", value: "黑龙江"},
        {label: "江苏", value: "江苏"},
        {label: "浙江", value: "浙江"},
        {label: "安徽", value: "安徽"},
        {label: "福建", value: "福建"},
        {label: "江西", value: "江西"},
        {label: "山东", value: "山东"},
        {label: "河南", value: "河南"},
        {label: "湖北", value: "湖北"},
        {label: "湖南", value: "湖南"},
        {label: "广东", value: "广东"},
        {label: "海南", value: "海南"},
        {label: "四川", value: "四川"},
        {label: "贵州", value: "贵州"},
        {label: "云南", value: "云南"},
        {label: "陕西", value: "陕西"},
        {label: "甘肃", value: "甘肃"},
        {label: "青海", value: "青海"},
        {label: "台湾", value: "台湾"},
        {label: "广西", value: "广西"},
        {label: "西藏", value: "西藏"},
        {label: "宁夏", value: "宁夏"},
        {label: "新疆", value: "新疆"},
        {label: "内蒙古", value: "内蒙古"},
        {label: "香港", value: "香港"},
        {label: "澳门", value: "澳门"}
    ])

    let total = ref(0); // 一共有几条数据
    let selectIds = ref([]); // 多选选中的数据
    let gameFormShow = ref(false); // 表单弹窗是否显式，默认不显示
    let formTitle = ref(""); // 弹窗的名称：新增店铺/修改店铺
    
    // 头部搜索框的数据
    let queryForm = ref({
        name: undefined,
        pageNum: 1,
        pageSize: 15
    })
    // 弹窗表单数据
    let form = ref({
        id: undefined,
        name: undefined,
        city: undefined,
        star: 0,
        address: undefined,
        price: 0,
        detail: '',
        picture: undefined
    })

    // 菜单数据列表
    let gameStoreList = ref([]);

    /* 挂载时，查询自己的所有店铺信息 */
    onMounted(() => {
        handleSearchGameStoreList();
    })
    // 查询自己的所有店铺信息
    function handleSearchGameStoreList() {
        listGameStoreSelf(queryForm.value).then(res => {
            if (res.code == 200) {
                gameStoreList.value = res.data.list;
                total.value = res.data.total;
            }
        })
    }

    /* 头部搜索框相关方法 */
    // 搜索
    function handleQuery() {
        queryForm.value.pageNum = 1;
        listGameStoreSelf(queryForm.value).then(res => {
            if (res.code == 200) {
                gameStoreList.value = res.data.list;
                total.value = res.data.total;
            }
        })
    }

    // 重置
    function handleReset() {
        queryForm.value.pageNum = 1;
        queryForm.value.name = undefined;
        listGameStoreSelf(queryForm.value).then(res => {
            if (res.code == 200) {
                gameStoreList.value = res.data.list;
                total.value = res.data.total;
            }
        })
    }

    /* 功能按钮相关方法 */
    
    // 新增按钮（弹出表单即可）
    function handleAdd() {
        form.value.id = undefined;
        gameFormShow.value = true;
        formTitle.value = "新增店铺";
    }

    // 删除按钮，弹出是否要删除数据，确定就删除，取消就不删除
    function handleRemove(id) {
        // ids是要删除的菜单id列表
        let ids = undefined; 
        if (id > 0) {
            ids = [id]
        } else {
            if (selectIds.value.length == 0)
                return;
            ids = selectIds.value;
        }

        ElMessageBox.confirm(
            `确定要删除店铺吗?`,
            '删除店铺',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }
        ).then(() => {
            removeGameStore(ids).then(res => {
                if (res.code == 200) {
                    ElMessage.success('删除店铺成功');
                    handleSearchGameStoreList();
                }
            })
        }).catch(() => {})
    }
    /* 列表相关方法 */

    // 修改按钮（首先根据id查询对应的店铺数据，然后弹出表单并回显数据）
    function handleEdit(id) {
        getGameStoreDetail(id).then(res => {
            if (res.code == 200) {
                form.value.id = res.data.id;
                form.value.name = res.data.name;
                form.value.city = res.data.city;
                form.value.star = res.data.star;
                form.value.address = res.data.address;
                form.value.price = res.data.price;
                form.value.detail = res.data.detail;
                form.value.picture = res.data.picture;
                gameFormShow.value = true;
                formTitle.value = '修改店铺';
            } else {
                ElMessage.error('该店铺已被删除');
            }
        })
    }

    // 多选变化
    function handleSelectionChange(selection) {
        let ids = selection.map(item => item.id)
        selectIds.value = ids;
    }

    /* 分页相关方法 */
    // 点击下一页，上一页
    function handleCurrentChange(pageNumber) {
        queryForm.value.pageNum = pageNumber;
        listGameStoreSelf(queryForm.value).then(res => {
            if (res.code == 200) {
                gameStoreList.value = res.data.list;
                total.value = res.data.total;
            }
        })
    }

    /* 表单相关方法 */
    // 数据校验，如果字符串为空，则返回true
    function isStrBlank(data) {
        return data === undefined || data === null ||
            data == '' || data.trim() === '';
    }

    // 提交表单，根据form.id值判断是新增还是修改【有id值则为修改】
    function handleSubmit() {
        // 做数据校验
        if (isStrBlank(form.value.name)) {
            ElMessage.warning('店铺名称不能为空');
            return;
        }
        if (isStrBlank(form.value.city)) {
            ElMessage.warning('所在城市不能为空');
            return;            
        }
        if (isStrBlank(form.value.address)) {
            ElMessage.warning('详细地址不能为空');
            return;            
        }
        if (isStrBlank(form.value.picture)) {
            ElMessage.warning('店铺图片不能为空');
            return;            
        }        

        if (form.value.id) {
            // 修改
            updateGameStore(form.value).then(res => {
                if (res.code == 200) {
                    // 关闭窗口
                    gameFormShow.value = false;
                    // 刷新列表
                    handleSearchGameStoreList();
                    ElMessage.success('修改店铺成功')
                }
            })
        } else {
            // 新增
            addGameStore(form.value).then(res => {
                if (res.code == 200) {
                    // 关闭窗口
                    gameFormShow.value = false;
                    // 刷新列表
                    handleSearchGameStoreList();
                    ElMessage.success('新增店铺成功');
                }
            })
        }
    }

    // 关闭弹窗
    function handleClose() {
        gameFormShow.value = false;
    }

    // 图片上传相关
    const headers = ref({
        // 携带token传递到后端
        Authorization: "Bearer " + localStorage.getItem("userToken")
    });
    // 图片传输成功之后执行的回调
    const handleAvatarSuccess = (response, uploadFile) => {
        ElMessage.success('上传成功');
        // 获取后端返回的图片地址
        form.value.picture = response.data;
    };
    // 请求之前进行判断
    const beforeAvatarUpload = (rawFile) => {
        if (rawFile.type !== "image/jpeg" && rawFile.type !== "image/jpg" && rawFile.type !== "image/png") {
            ElMessage.error("图片格式必须为png或jpg");
            return false;
        } else if (rawFile.size / 1024 / 1024 > 10) {
            ElMessage.error("图片大小不能超过10MB");
            return false;
        }
        return true;
    };

</script>

<style lang="scss" scoped>
.pagination_container {
    position: relative;
    height: 40px;
    margin-top: 15px;
}

.el-pagination {
    position: absolute;
    right: 110px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>

