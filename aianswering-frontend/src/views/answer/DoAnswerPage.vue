<template>
  <div id="doAnswerPage">
    <a-card>
      <h1>{{ app.appName }}</h1>
      <p>{{ app.appDesc }}</p>
      <h2 style="margin-bottom: 16px">
        {{ current }}. {{ currentQuestion?.title }}
      </h2>
      <div>
        <a-radio-group
          direction="vertical"
          v-model="currentAnswer"
          :options="questionOptions"
          @change="doRadioChange"
        />
      </div>
      <div style="margin-top: 24px">
        <a-space size="large">
          <a-button
            type="primary"
            circle
            v-if="current < questionContent.length"
            :disabled="!currentAnswer"
            @click="current += 1"
          >
            Next question
          </a-button>
          <a-button
            type="primary"
            v-if="current === questionContent.length"
            :loading="submitting"
            circle
            :disabled="!currentAnswer"
            @click="doSubmit"
          >
            {{ submitting ? "Rating" : "View results" }}
          </a-button>
          <a-button v-if="current > 1" circle @click="current -= 1">
            Previous question
          </a-button>
        </a-space>
      </div>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import {
  computed,
  defineProps,
  reactive,
  ref,
  watchEffect,
  withDefaults,
} from "vue";
import API from "@/api";
import { useRouter } from "vue-router";
import { listQuestionVoByPageUsingPost } from "@/api/questionController";
import message from "@arco-design/web-vue/es/message";
import { getAppVoByIdUsingGet } from "@/api/appController";
import {
  addUserAnswerUsingPost,
  generateUserAnswerIdUsingGet,
} from "@/api/userAnswerController";

interface Props {
  appId: string;
}

const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});

const router = useRouter();

const app = ref<API.AppVO>({});
// Structure of question content (understood as a list of questions)
const questionContent = ref<API.QuestionContentDTO[]>([]);

// Serial number of the current question (from 1)
const current = ref(1);
// Current question
const currentQuestion = ref<API.QuestionContentDTO>({});
// Current question options
const questionOptions = computed(() => {
  return currentQuestion.value?.options
    ? currentQuestion.value.options.map((option) => {
        return {
          label: `${option.key}. ${option.value}`,
          value: option.key,
        };
      })
    : [];
});
// Current answer
const currentAnswer = ref<string>();
// Answer list
const answerList = reactive<string[]>([]);
// Whether the result is being submitted
const submitting = ref(false);

// Unique id
const id = ref<number>();
// Generate unique id
const generateId = async () => {
  const res = await generateUserAnswerIdUsingGet();
  if (res.data.code === 0) {
    id.value = res.data.data as any;
  } else {
    message.error("Failed to get unique id，" + res.data.message);
  }
};

// Generate a unique id when entering the page
watchEffect(() => {
  generateId();
});

/**
 * Load data
 */
const loadData = async () => {
  if (!props.appId) {
    return;
  }
  // Get app
  let res: any = await getAppVoByIdUsingGet({
    id: props.appId as any,
  });
  if (res.data.code === 0) {
    app.value = res.data.data as any;
  } else {
    message.error("Failed to get app，" + res.data.message);
  }
  // Get question
  res = await listQuestionVoByPageUsingPost({
    appId: props.appId as any,
    current: 1,
    pageSize: 1,
    sortField: "createTime",
    sortOrder: "descend",
  });
  if (res.data.code === 0 && res.data.data?.records) {
    questionContent.value = res.data.data.records[0].questionContent;
  } else {
    message.error("Failed to get question，" + res.data.message);
  }
};

// Getting old data
watchEffect(() => {
  loadData();
});

// Changing the current question number will automatically update the current question and answer.
watchEffect(() => {
  currentQuestion.value = questionContent.value[current.value - 1];
  currentAnswer.value = answerList[current.value - 1];
});

/**
 * Save the option record when the option is selected
 * @param value
 */
const doRadioChange = (value: string) => {
  answerList[current.value - 1] = value;
};

/**
 * Submit
 */
const doSubmit = async () => {
  if (!props.appId || !answerList) {
    return;
  }
  submitting.value = true;
  const res = await addUserAnswerUsingPost({
    appId: props.appId as any,
    choices: answerList,
    id: id.value as any,
  });
  if (res.data.code === 0 && res.data.data) {
    router.push(`/answer/result/${res.data.data}`);
  } else {
    message.error("Failed to submit answer，" + res.data.message);
  }
  submitting.value = false;
};
</script>
