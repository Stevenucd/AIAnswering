<template>
  <div id="appDetailPage">
    <a-card>
      <a-row style="margin-bottom: 16px">
        <a-col flex="auto" class="content-wrapper">
          <h2>{{ data.appName }}</h2>
          <p>{{ data.appDesc }}</p>
          <p>App type：{{ APP_TYPE_MAP[data.appType] }}</p>
          <p>
            Scoring strategy：{{
              APP_SCORING_STRATEGY_MAP[data.scoringStrategy]
            }}
          </p>
          <p>
            <a-space>
              Author：
              <div :style="{ display: 'flex', alignItems: 'center' }">
                <a-avatar
                  :size="24"
                  :image-url="data.user?.userAvatar"
                  :style="{ marginRight: '8px' }"
                />
                <a-typography-text
                  >{{ data.user?.userName ?? "Unknown" }}
                </a-typography-text>
              </div>
            </a-space>
          </p>
          <p>
            Create Time：{{
              dayjs(data.createTime).format("YYYY-MM-DD HH:mm:ss")
            }}
          </p>
          <a-space size="medium">
            <a-button type="primary" :href="`/answer/do/${id}`"
              >Start answering</a-button
            >
            <a-button v-if="isMy" :href="`/add/question/${id}`"
              >Edit question
            </a-button>
            <a-button v-if="isMy" :href="`/add/scoring_result/${id}`"
              >Setting up ratings
            </a-button>
            <a-button v-if="isMy" :href="`/add/app/${id}`">Edit app</a-button>
          </a-space>
        </a-col>
        <a-col flex="320px">
          <a-image width="100%" :src="data.appIcon" />
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { computed, defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import { getAppVoByIdUsingGet } from "@/api/appController";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
import { useLoginUserStore } from "@/store/userStore";
import { APP_SCORING_STRATEGY_MAP, APP_TYPE_MAP } from "../../constant/app";

interface Props {
  id: string;
}

const props = withDefaults(defineProps<Props>(), {
  id: () => {
    return "";
  },
});

const router = useRouter();

const data = ref<API.AppVO>({});

// Get logged in user
const loginUserStore = useLoginUserStore();
let loginUserId = loginUserStore.loginUser?.id;
// Created by user or not
const isMy = computed(() => {
  return loginUserId && loginUserId === data.value.userId;
});

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
  if (res.data.code === 0) {
    data.value = res.data.data as any;
  } else {
    message.error("Failed to get data，" + res.data.message);
  }
};

/**
 * Listens to the searchParams variable and triggers a reload of the data when it changes.
 */
watchEffect(() => {
  loadData();
});
</script>

<style scoped>
#appDetailPage {
}

#appDetailPage .content-wrapper > * {
  margin-bottom: 24px;
}
</style>
