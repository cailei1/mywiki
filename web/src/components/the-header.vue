<template>
  <a-layout-header class="header">
    <div class="logo"/>
    <a-menu
        v-model:selectedKeys="selectedKeys1"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="home">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item key="ebook_manager">
        <router-link to="/ebook_manager">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="category_manager">
        <router-link to="/category_manager">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="about_us">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script lang="ts">
import {defineComponent} from 'vue';

export default defineComponent({
  name: 'the-header',

});
</script>

<style scoped>

</style>