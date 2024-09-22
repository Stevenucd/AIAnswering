<template>
  <div id="addQuestionPage">
    <h2 style="margin-bottom: 32px">Edit question</h2>
    <a-form
      :model="questionContent"
      :style="{ width: '480px' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item label="App id">
        {{ appId }}
      </a-form-item>
      <a-form-item
        label="Question list"
        :content-flex="false"
        :merge-props="false"
      >
        <a-space size="medium">
          <a-button @click="addQuestion(questionContent.length)">
            Add question at the bottom
          </a-button>
          <AiGenerateQuestionDrawer
            :appId="appId"
            :onSuccess="onAiGenerateSuccess"
            :onSSESuccess="onAiGenerateSuccessSSE"
            :onSSEClose="onSSEClose"
            :onSSEStart="onSSEStart"
          />
        </a-space>
        <div v-for="(question, index) in questionContent" :key="index">
          <a-space size="large">
            <h3>Question {{ index + 1 }}</h3>
            <a-button size="small" @click="addQuestion(index + 1)">
              Add questions
            </a-button>
            <a-button
              size="small"
              status="danger"
              @click="deleteQuestion(index)"
            >
              Delete questions
            </a-button>
          </a-space>
          <a-form-item
            field="posts.post1"
            :label="`Question ${index + 1} Title`"
          >
            <a-input
              v-model="question.title"
              placeholder="Please enter title"
            />
          </a-form-item>
          <a-space size="large">
            <h4>Question {{ index + 1 }} Option list</h4>
            <a-button
              size="small"
              @click="addQuestionOption(question, question.options.length)"
            >
              Add option at the bottom
            </a-button>
          </a-space>
          <a-form-item
            v-for="(option, optionIndex) in question.options"
            :key="optionIndex"
            :label="`Option ${optionIndex + 1}`"
            :content-flex="false"
            :merge-props="false"
          >
            <a-form-item label="Option key">
              <a-input
                v-model="option.key"
                placeholder="Please enter the option key"
              />
            </a-form-item>
            <a-form-item label="Option value">
              <a-input
                v-model="option.value"
                placeholder="Please enter the option value"
              />
            </a-form-item>
            <a-form-item label="Option result">
              <a-input
                v-model="option.result"
                placeholder="Please enter the option result"
              />
            </a-form-item>
            <a-form-item label="Option score">
              <a-input-number
                v-model="option.score"
                placeholder="Please enter the option score"
              />
            </a-form-item>
            <a-space size="large">
              <a-button
                size="mini"
                @click="addQuestionOption(question, optionIndex + 1)"
              >
                Add Options
              </a-button>
              <a-button
                size="mini"
                status="danger"
                @click="deleteQuestionOption(question, optionIndex as any)"
              >
                Delete Options
              </a-button>
            </a-space>
          </a-form-item>
        </div>
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
import { useRouter } from "vue-router";
import {
  addQuestionUsingPost,
  editQuestionUsingPost,
  listQuestionVoByPageUsingPost,
} from "@/api/questionController";
import message from "@arco-design/web-vue/es/message";
import AiGenerateQuestionDrawer from "@/views/add/components/AiGenerateQuestionDrawer.vue";

interface Props {
  appId: string;
}

const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});

const router = useRouter();

// Structure of question content (understood as a list of questions)
const questionContent = ref<API.QuestionContentDTO[]>([]);

/**
 * Add questions
 * @param index
 */
const addQuestion = (index: number) => {
  questionContent.value.splice(index, 0, {
    title: "",
    options: [],
  });
};

/**
 * Delete questions
 * @param index
 */
const deleteQuestion = (index: number) => {
  questionContent.value.splice(index, 1);
};

/**
 * Add question option
 * @param question
 * @param index
 */
const addQuestionOption = (question: API.QuestionContentDTO, index: number) => {
  if (!question.options) {
    question.options = [];
  }
  question.options.splice(index, 0, {
    key: "",
    value: "",
  });
};

/**
 * Delete question option
 * @param question
 * @param index
 */
const deleteQuestionOption = (
  question: API.QuestionContentDTO,
  index: number
) => {
  if (!question.options) {
    question.options = [];
  }
  question.options.splice(index, 1);
};

const oldQuestion = ref<API.QuestionVO>();

/**
 * Load data
 */
const loadData = async () => {
  if (!props.appId) {
    return;
  }
  const res = await listQuestionVoByPageUsingPost({
    appId: props.appId as any,
    current: 1,
    pageSize: 1,
    sortField: "createTime",
    sortOrder: "descend",
  });
  if (res.data.code === 0 && res.data.data?.records) {
    oldQuestion.value = res.data.data?.records[0];
    if (oldQuestion.value) {
      questionContent.value = oldQuestion.value.questionContent ?? [];
    }
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
  if (!props.appId || !questionContent.value) {
    return;
  }
  let res: any;
  // If it is a modification
  if (oldQuestion.value?.id) {
    res = await editQuestionUsingPost({
      id: oldQuestion.value.id,
      questionContent: questionContent.value,
    });
  } else {
    // Create
    res = await addQuestionUsingPost({
      appId: props.appId as any,
      questionContent: questionContent.value,
    });
  }
  if (res.data.code === 0) {
    message.success(
      "Successful, you will be redirected to the app details page soon"
    );
    setTimeout(() => {
      router.push(`/app/detail/${props.appId}`);
    }, 3000);
  } else {
    message.error("Operation failed，" + res.data.message);
  }
};

/**
 * Execution after AI successful generate questions
 */
const onAiGenerateSuccess = (result: API.QuestionContentDTO[]) => {
  message.success(
    `AI generate title successful, generate ${result.length} question(s)`
  );
  questionContent.value = [...questionContent.value, ...result];
};

/**
 * Execution after AI successful generate questions (Real-Time)
 */
const onAiGenerateSuccessSSE = (result: API.QuestionContentDTO) => {
  questionContent.value = [...questionContent.value, result];
};

/**
 * SSE Generate Start
 */
const onSSEStart = (event: any) => {
  message.success("Generate Start");
};

/**
 * SSE Generate Success
 */
const onSSEClose = (event: any) => {
  message.success("Generate Success");
};
</script>
