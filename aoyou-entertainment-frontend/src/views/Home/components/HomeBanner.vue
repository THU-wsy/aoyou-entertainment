<script setup>
    import img1 from '@/assets/images/banner-1.png';
    import img2 from '@/assets/images/banner-2.png';
    import img3 from '@/assets/images/banner-3.png';
    import img4 from '@/assets/images/banner-4.png';
    import img5 from '@/assets/images/banner-5.png';
    const bannerList = ref([
        {id: 1, url: img1},
        {id: 2, url: img2},
        {id: 3, url: img3},
        {id: 4, url: img4},
        {id: 5, url: img5}
    ]);
</script>

<template>
  <div class="home-banner">
    <el-carousel height="500px">
      <el-carousel-item v-for="item in bannerList" :key="item.id">
        <img :src="item.url" alt="">
      </el-carousel-item>
    </el-carousel>
  </div>
  <div style="height: 500px;"></div>
</template>

<style scoped lang='scss'>
.home-banner {
  width: 1240px;
  height: 500px;
  position: absolute;
  left: 0;
  top: 0;
  z-index: 98;

  img {
    width: 100%;
    height: 500px;
  }
}
</style>