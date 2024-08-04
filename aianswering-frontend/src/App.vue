<template>
  <div id="app">
    <template v-if="route.path.startsWith('/user')">
      <router-view />
    </template>
    <template v-else>
      <BasicLayout />
    </template>
  </div>
</template>

<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { useRoute } from "vue-router";
import { onMounted } from "vue";

const route = useRoute();

/**
 * Global initialisation functions that have global single call code can be written here
 */
const doInit = () => {
  console.log("Welcome to my project");
};

onMounted(() => {
  doInit();
});
</script>

<style scoped></style>
