<script setup>
defineProps({
  store: {
    type: Object,
    default: () => { }
  }
})
</script>

<template>
  <RouterLink :to="`/detail/game/${store.id}`" class="goods-item">
    <img :src="store.picture" alt="" />
    <p class="name ellipsis">{{ store.name }}</p>
    <div class="city-star">
      <span class="desc ellipsis">{{ store.city }}</span>
      <span class="star">&hearts; {{ store.star }}</span>
    </div>
    <!-- <p class="price">&yen;{{ good.price }}</p> -->
  </RouterLink>
</template>


<style scoped lang="scss">
.goods-item {
  display: block;
  width: 220px;
  padding: 20px 30px;
  text-align: center;
  transition: all .5s;

  &:hover {
    transform: translate3d(0, -3px, 0);
    box-shadow: 0 3px 8px rgb(0 0 0 / 20%);
  }

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

  .city-star {
    display:flex;
    justify-content:space-between
  }

  .star {
    color: $priceColor;
    font-size: 14px;
  }
}
</style>