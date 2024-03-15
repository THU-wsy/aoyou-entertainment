<template>
  <LayoutNav></LayoutNav>
  <LayoutHeader></LayoutHeader>
  <RouterView/>
  <LayoutFooter></LayoutFooter>
</template>

<script setup>
import LayoutNav from "@/views/Layout/components/LayoutNav.vue";
import LayoutHeader from "@/views/Layout/components/LayoutHeader.vue";
import LayoutFooter from "@/views/Layout/components/LayoutFooter.vue";
</script>

<style scoped lang="scss">

</style>