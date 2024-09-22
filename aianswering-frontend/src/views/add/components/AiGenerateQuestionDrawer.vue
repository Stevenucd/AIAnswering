<template>
  <a-config-provider :locale="locale">
    <a-button type="outline" @click="handleClick"
      >AI Generate Question</a-button
    >
    <a-drawer
      :width="340"
      :visible="visible"
      @ok="handleOk"
      @cancel="handleCancel"
      unmountOnClose
    >
      <template #title>AI Generate Question</template>
      <div>
        <a-form
          :model="form"
          label-align="left"
          auto-label-width
          @submit="handleSubmit"
        >
          <a-form-item label="App id">
            {{ appId }}
          </a-form-item>
          <a-form-item field="questionNumber" label="Number of questions">
            <a-input-number
              min="0"
              max="20"
              v-model="form.questionNumber"
              placeholder="Please enter number of questions"
            />
          </a-form-item>
          <a-form-item field="optionNumber" label="Number of options">
            <a-input-number
              min="0"
              max="6"
              v-model="form.optionNumber"
              placeholder="Please enter number of options"
            />
          </a-form-item>
          <a-form-item>
            <a-space direction="vertical">
              <a-button
                :loading="submitting"
                type="primary"
                html-type="submit"
                style="width: 120px"
              >
                {{ submitting ? "Generating" : "Generation" }}
              </a-button>
              <a-button
                :loading="sseSubmitting"
                style="width: 150px"
                @click="handleSSESubmit"
              >
                {{ sseSubmitting ? "Generating" : "Real-Time Generation" }}
              </a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </div>
    </a-drawer>
  </a-config-provider>
</template>

<script setup lang="ts">
import enUS from "@arco-design/web-vue/es/locale/lang/en-us";
import { defineProps, reactive, ref, withDefaults } from "vue";
import API from "@/api";
import { aiGenerateQuestionUsingPost } from "@/api/questionController";
import message from "@arco-design/web-vue/es/message";

const locale = ref(enUS);

interface Props {
  appId: string;
  onSuccess?: (result: API.QuestionContentDTO[]) => void;
  onSSESuccess?: (result: API.QuestionContentDTO) => void;
  onSSEStart?: (event: any) => void;
  onSSEClose?: (event: any) => void;
}

const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});

const form = reactive({
  optionNumber: 2,
  questionNumber: 10,
} as API.AiGenerateQuestionRequest);

const visible = ref(false);
const submitting = ref(false);
const sseSubmitting = ref(false);

const handleClick = () => {
  visible.value = true;
};
const handleOk = () => {
  visible.value = false;
};
const handleCancel = () => {
  visible.value = false;
};

/**
 * Submit
 */
const handleSubmit = async () => {
  if (!props.appId) {
    return;
  }
  submitting.value = true;
  const res = await aiGenerateQuestionUsingPost({
    appId: props.appId as any,
    ...form,
  });
  if (res.data.code === 0 && res.data.data.length > 0) {
    if (props.onSuccess) {
      props.onSuccess(res.data.data);
    } else {
      message.success("Generate Question Successfully");
    }
    handleCancel();
  } else {
    message.error("Operation failed，" + res.data.message);
  }
  submitting.value = false;
};

/**
 * SSE Submit (Real-Time Generation)
 */
const handleSSESubmit = async () => {
  if (!props.appId) {
    return;
  }
  sseSubmitting.value = true;

  // Create SSE request
  const eventSource = new EventSource(
    `http://localhost:8101/api/question/ai_generate/sse` +
      `?appId=${props.appId}&optionNumber=${form.optionNumber}&questionNumber=${form.questionNumber}`
  );
  let first = true;
  // Receive message
  eventSource.onmessage = function (event) {
    if (first) {
      props.onSSEStart?.(event);
      handleCancel();
      first = !first;
    }
    props.onSSESuccess?.(JSON.parse(event.data));
  };
  // Error or normal disconnect
  eventSource.onerror = function (event) {
    if (event.eventPhase === EventSource.CLOSED) {
      console.log("Disconnect");
      props.onSSEClose?.(event);
      eventSource.close();
    } else {
      eventSource.close();
    }
  };
  sseSubmitting.value = false;
};
</script>
