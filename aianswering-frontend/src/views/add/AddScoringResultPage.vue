<template>
  <div id="addScoringResultPage">
    <h2 style="margin-bottom: 32px">Edit rating</h2>
    <a-form
      :model="form"
      :style="{ width: '480px' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item label="App id">
        {{ appId }}
      </a-form-item>
      <a-form-item v-if="updateId" label="Edit rating id">
        {{ updateId }}
      </a-form-item>
      <a-form-item field="resultName" label="Result name">
        <a-input
          v-model="form.resultName"
          placeholder="Please enter result name"
        />
      </a-form-item>
      <a-form-item field="resultDesc" label="Result description">
        <a-input
          v-model="form.resultDesc"
          placeholder="Please enter result description"
        />
      </a-form-item>
      <a-form-item field="resultPicture" label="Result picture">
        <a-input
          v-model="form.resultPicture"
          placeholder="Please enter result picture address"
        />
      </a-form-item>
      <a-form-item field="resultProp" label="Result prop">
        <a-input-tag
          v-model="form.resultProp"
          :style="{ width: '320px' }"
          placeholder="Please output the result prop and press enter to confirm"
          allow-clear
        />
      </a-form-item>
      <a-form-item field="resultScoreRange" label="Range of outcome scores">
        <a-input-number
          v-model="form.resultScoreRange"
          placeholder="Please enter range of outcome scores"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 120px">
          Submit
        </a-button>
      </a-form-item>
    </a-form>
    <h2 style="margin-bottom: 32px">Admin rating</h2>
    <ScoringResultTable :appId="appId" :doUpdate="doUpdate" ref="tableRef" />
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, withDefaults } from "vue";
import API from "@/api";
import { useRouter } from "vue-router";
import ScoringResultTable from "@/views/add/components/ScoringResultTable.vue";
import {
  addScoringResultUsingPost,
  editScoringResultUsingPost,
} from "@/api/scoringResultController";
import message from "@arco-design/web-vue/es/message";

interface Props {
  appId: string;
}

const props = withDefaults(defineProps<Props>(), {
  appId: () => {
    return "";
  },
});

const router = useRouter();
const tableRef = ref();

// Form parameters
const form = ref({
  resultDesc: "",
  resultName: "",
  resultPicture: "",
} as API.ScoringResultAddRequest);

const updateId = ref<any>();

const doUpdate = (scoringResult: API.ScoringResultVO) => {
  updateId.value = scoringResult.id;
  form.value = scoringResult;
};

/**
 * Submit
 */
const handleSubmit = async () => {
  if (!props.appId) {
    return;
  }
  let res: any;
  // If it is a modification
  if (updateId.value) {
    res = await editScoringResultUsingPost({
      id: updateId.value as any,
      ...form.value,
    });
  } else {
    // Create
    res = await addScoringResultUsingPost({
      appId: props.appId as any,
      ...form.value,
    });
  }
  if (res.data.code === 0) {
    message.success("Successful operation");
  } else {
    message.error("Operation failed，" + res.data.message);
  }
  if (tableRef.value) {
    tableRef.value.loadData();
    updateId.value = undefined;
  }
};
</script>
