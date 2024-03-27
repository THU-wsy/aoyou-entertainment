<script setup>
  import { RouterView } from 'vue-router'
  import { ref } from 'vue'
  import { ElConfigProvider } from 'element-plus'
  import zhCn from 'element-plus/es/locale/lang/zh-cn'
  let locale = ref(zhCn)
</script>

<template>
  <!-- 容器 -->
  <el-config-provider :locale="locale">
    <RouterView />
  </el-config-provider>
</template>

<style scoped lang="scss">
header{
  color: $priceColor
}
</style>
