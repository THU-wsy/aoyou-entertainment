<script setup>
  // 导入消息提示
  import { ElMessage, ElMessageBox } from 'element-plus';
  import GameStoreItem from '@/views/Game/components/GameStoreItem.vue'
  import { queryGameStore } from '@/api/game/gameStore';

  /* 数据 */
  let total = ref(0); // 一共有几条数据
  
  // 头部搜索框的数据
  let queryForm = ref({
      name: undefined,
      city: undefined,
      pageNum: 1,
      pageSize: 20
  })

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

  // 店铺数据列表
  let gameStoreList = ref([]);

  /* 挂载时，查询第一页的店铺 */
  onMounted(() => {
    handleQuery();
  })

  /* 头部搜索框相关方法 */
  // 搜索
  function handleQuery() {
      queryForm.value.pageNum = 1;
      queryGameStore(queryForm.value).then(res => {
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
      queryForm.value.city = undefined;
      queryGameStore(queryForm.value).then(res => {
          if (res.code == 200) {
            gameStoreList.value = res.data.list;
            total.value = res.data.total;
          }
      })
  }

  /* 分页相关方法 */
  // 点击下一页，上一页
  function handleCurrentChange(pageNumber) {
      queryForm.value.pageNum = pageNumber;
      queryGameStore(queryForm.value).then(res => {
          if (res.code == 200) {
            gameStoreList.value = res.data.list;
            total.value = res.data.total;
          }
      })
      
  }

</script>

<template>
  <div class="container">
    <div class="sub-container">
      <!-- <el-tabs>
        <el-tab-pane label="最新商品" name="publishTime"></el-tab-pane>
        <el-tab-pane label="最高人气" name="orderNum"></el-tab-pane>
        <el-tab-pane label="评论最多" name="evaluateNum"></el-tab-pane>
      </el-tabs> -->

      <!-- 头部搜索框 -->
      <el-form :model="queryForm" :inline="true">
          <el-form-item label="店铺名称">
              <el-input v-model="queryForm.name" />
          </el-form-item>

          <el-form-item label="所在地区">
            <el-select v-model="queryForm.city" clearable placeholder="全部" style="width: 120px">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="handleQuery">搜索</el-button>
              <el-button @click="handleReset">重置</el-button>
          </el-form-item>
      </el-form>

      <div class="body">
         <!-- 店铺列表-->
         <GameStoreItem v-for="store in gameStoreList" :store="store" :key="store.id"></GameStoreItem>
      </div>

      <!-- 分页 -->
      <div class="pagination_container">
          <el-pagination v-model:current-page="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
              layout="prev, pager, next, jumper" :total="total"
              @current-change="handleCurrentChange" />
      </div>
    </div>
  </div>

</template>


<style lang="scss" scoped>

.sub-container {
  padding: 20px 10px;
  background-color: #fff;

  .body {
    display: flex;
    flex-wrap: wrap;
    padding: 0 10px;
  }

  .goods-item {
    display: block;
    width: 220px;
    margin-right: 20px;
    padding: 20px 30px;
    text-align: center;

    img {
      width: 160px;
      height: 160px;
    }

    p {
      padding-top: 10px;
    }

    .name {
      font-size: 16px;
    }

    .desc {
      color: #999;
      height: 29px;
    }

    .price {
      color: $priceColor;
      font-size: 20px;
    }
  }

  .pagination-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
  }


}
</style>


<!-- <style lang="scss" scoped>
.pagination_container {
  position: relative;
  height: 40px;
  margin-top: 15px;
}

.el-pagination {
  position: absolute;
  right: 110px;
}
</style> -->