<template>
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="resultName" label="Result name">
      <a-input
        v-model="formSearchParams.resultName"
        placeholder="Please enter result name"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="resultDesc" label="Result description">
      <a-input
        v-model="formSearchParams.resultDesc"
        placeholder="Please enter result description"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="appId" label="App id">
      <a-input
        v-model="formSearchParams.appId"
        placeholder="Please enter app id"
        allow-clear
      />
    </a-form-item>
    <a-form-item field="userId" label="User id">
      <a-input
        v-model="formSearchParams.userId"
        placeholder="Please enter user id"
        allow-clear
      />
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit" style="width: 100px">
        Search
      </a-button>
    </a-form-item>
  </a-form>
  <a-table
    :columns="columns"
    :data="dataList"
    :pagination="{
      showTotal: true,
      pageSize: searchParams.pageSize,
      current: searchParams.current,
      total,
    }"
    @page-change="onPageChange"
  >
    <template #resultPicture="{ record }">
      <a-image width="64" :src="record.resultPicture" />
    </template>
    <template #createTime="{ record }">
      {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #updateTime="{ record }">
      {{ dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
    </template>
    <template #optional="{ record }">
      <a-space>
        <a-button status="danger" @click="doDelete(record)">Delete</a-button>
      </a-space>
    </template>
  </a-table>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import {
  deleteScoringResultUsingPost,
  listScoringResultByPageUsingPost,
} from "@/api/scoringResultController";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";

const formSearchParams = ref<API.ScoringResultQueryRequest>({});

// Initialise search criteria (should not be modified)
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

const searchParams = ref<API.ScoringResultQueryRequest>({
  ...initSearchParams,
});
const dataList = ref<API.ScoringResult[]>([]);
const total = ref<number>(0);

/**
 * Load data
 */
const loadData = async () => {
  const res = await listScoringResultByPageUsingPost(searchParams.value);
  if (res.data.code === 0) {
    dataList.value = res.data.data?.records || [];
    total.value = res.data.data?.total || 0;
  } else {
    message.error("Failed to get data，" + res.data.message);
  }
};

/**
 * Perform a search
 */
const doSearch = () => {
  searchParams.value = {
    ...initSearchParams,
    ...formSearchParams.value,
  };
};

/**
 * Change search criteria to trigger data loading when paging changes
 * @param page
 */
const onPageChange = (page: number) => {
  searchParams.value = {
    ...searchParams.value,
    current: page,
  };
};

/**
 * Delete
 * @param record
 */
const doDelete = async (record: API.ScoringResult) => {
  if (!record.id) {
    return;
  }

  const res = await deleteScoringResultUsingPost({
    id: record.id,
  });
  if (res.data.code === 0) {
    loadData();
  } else {
    message.error("Delete failed，" + res.data.message);
  }
};

/**
 * Listens to the searchParams variable and triggers a reload of the data when it changes.
 */
watchEffect(() => {
  loadData();
});

// Table Column Configuration
const columns = [
  {
    title: "id",
    dataIndex: "id",
  },
  {
    title: "Name",
    dataIndex: "resultName",
  },
  {
    title: "Description",
    dataIndex: "resultDesc",
  },
  {
    title: "Picture",
    dataIndex: "resultPicture",
    slotName: "resultPicture",
  },
  {
    title: "Result Properties",
    dataIndex: "resultProp",
  },
  {
    title: "Score Range",
    dataIndex: "resultScoreRange",
  },
  {
    title: "App id",
    dataIndex: "appId",
  },
  {
    title: "User id",
    dataIndex: "userId",
  },
  {
    title: "Create Time",
    dataIndex: "createTime",
    slotName: "createTime",
  },
  {
    title: "Update Time",
    dataIndex: "updateTime",
    slotName: "updateTime",
  },
  {
    title: "Optional",
    slotName: "optional",
  },
];
</script>
