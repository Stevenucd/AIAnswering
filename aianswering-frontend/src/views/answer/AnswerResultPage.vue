<template>
  <div id="answerResultPage">
    <a-card>
      <a-row style="margin-bottom: 16px">
        <a-col flex="auto" class="content-wrapper">
          <h2>{{ data.resultName }}</h2>
          <p>Result description：{{ data.resultDesc }}</p>
          <p>Result id：{{ data.resultId }}</p>
          <p>Result score：{{ data.resultScore }}</p>
          <p>My answer：{{ data.choices }}</p>
          <p>App id：{{ data.appId }}</p>
          <p>App type：{{ APP_TYPE_MAP[data.appType] }}</p>
          <p>
            Scoring strategy：{{
              APP_SCORING_STRATEGY_MAP[data.scoringStrategy]
            }}
          </p>
          <p>
            <a-space>
              Respondent：
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
            Answer time：{{
              dayjs(data.createTime).format("YYYY-MM-DD HH:mm:ss")
            }}
          </p>
          <a-space size="medium">
            <a-button type="primary" :href="`/answer/do/${data.appId}`"
              >Go answer the question
            </a-button>
          </a-space>
        </a-col>
        <a-col flex="320px">
          <a-image width="100%" :src="data.userAnswerIcon" />
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, watchEffect, withDefaults } from "vue";
import API from "@/api";
import { getUserAnswerVoByIdUsingGet } from "@/api/userAnswerController";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
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

const data = ref<API.UserAnswerVO>({});

/**
 * Load data
 */
const loadData = async () => {
  if (!props.id) {
    return;
  }
  const res = await getUserAnswerVoByIdUsingGet({
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
#answerResultPage {
}

#answerResultPage .content-wrapper > * {
  margin-bottom: 24px;
}
</style>
