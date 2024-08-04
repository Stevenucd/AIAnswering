<template>
  <div id="addAppPage">
    <h2 style="margin-bottom: 32px">Create app</h2>
    <a-form
      :model="form"
      :style="{ width: '480px' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="appName" label="App name">
        <a-input v-model="form.appName" placeholder="Please enter app name" />
      </a-form-item>
      <a-form-item field="appDesc" label="App Description">
        <a-input
          v-model="form.appDesc"
          placeholder="Please enter app description"
        />
      </a-form-item>
      <a-form-item field="appIcon" label="App icon">
        <a-input v-model="form.appIcon" placeholder="Please enter app icon" />
      </a-form-item>
      <!--      <a-form-item field="appIcon" label="App icon">-->
      <!--        <PictureUploader-->
      <!--          :value="form.appIcon"-->
      <!--          :onChange="(value) => (form.appIcon = value)"-->
      <!--        />-->
      <!--      </a-form-item>-->
      <a-form-item field="appType" label="App type">
        <a-select
          v-model="form.appType"
          :style="{ width: '320px' }"
          placeholder="Please select the app type"
        >
          <a-option
            v-for="(value, key) of APP_TYPE_MAP"
            :value="Number(key)"
            :label="value"
          />
        </a-select>
      </a-form-item>
      <a-form-item field="scoringStrategy" label="Scoring strategy">
        <a-select
          v-model="form.scoringStrategy"
          :style="{ width: '320px' }"
          placeholder="Please enter scoring strategy"
        >
          <a-option
            v-for="(value, key) of APP_SCORING_STRATEGY_MAP"
            :value="Number(key)"
            :label="value"
          />
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 120px">
          Submit
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import {
  addAppUsingPost,
  editAppUsingPost,
  getAppVoByIdUsingGet,
} from "@/api/appController";
import { APP_SCORING_STRATEGY_MAP, APP_TYPE_MAP } from "@/constant/app";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

const router = useRouter();

const form = ref({
  appDesc: "",
  appIcon: "",
  appName: "",
  appType: 0,
  scoringStrategy: 0,
} as API.AppAddRequest);

const oldApp = ref<API.AppVO>();

/**
 * Load data
 */
const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getAppVoByIdUsingGet({
    id: props.id as any,
  });
  if (res.data.code === 0 && res.data.data) {
    oldApp.value = res.data.data;
    form.value = res.data.data;
  } else {
    message.error("Failed to get data，" + res.data.message);
  }
};

// Getting old data
watchEffect(() => {
  loadData();
});

/**
 * Submit
 */
const handleSubmit = async () => {
  let res: any;
  // If it is a modification
  if (props.id) {
    res = await editAppUsingPost({
      id: props.id as any,
      ...form.value,
    });
  } else {
    // Create
    res = await addAppUsingPost(form.value);
  }
  if (res.data.code === 0) {
    message.success(
      "Successful, you will be redirected to the app details page soon"
    );
    setTimeout(() => {
      router.push(`/app/detail/${props.id || res.data.data}`);
    }, 3000);
  } else {
    message.error("Operation failed，" + res.data.message);
  }
};
</script>
