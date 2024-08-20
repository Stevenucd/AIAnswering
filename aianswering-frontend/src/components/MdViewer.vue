<template>
  <Viewer :value="value" :plugins="plugins" />
</template>

<script setup lang="ts">
import gfm from "@bytemd/plugin-gfm";
import highlight from "@bytemd/plugin-highlight";
import { Viewer } from "@bytemd/vue-next";
import { withDefaults, defineProps } from "vue";

/**
 * Defining component property types
 */
interface Props {
  value: string;
}

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
];

/**
 * Assigning Initial Values to Components
 */
const props = withDefaults(defineProps<Props>(), {
  value: () => "",
});
</script>
