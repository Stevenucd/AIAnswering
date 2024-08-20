<template>
  <a-config-provider :locale="locale">
    <a-form
      :model="formSearchParams"
      :style="{ marginBottom: '20px' }"
      layout="inline"
      @submit="doSearch"
    >
      <a-form-item field="appName" label="App name">
        <a-input
          v-model="formSearchParams.appName"
          placeholder="Please enter app name"
          allow-clear
        />
      </a-form-item>
      <a-form-item field="appDesc" label="App Description">
        <a-input
          v-model="formSearchParams.appDesc"
          placeholder="Please enter app description"
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
      <template #appIcon="{ record }">
        <a-image width="64" :src="record.appIcon" />
      </template>
      <template #appType="{ record }">
        {{ APP_TYPE_MAP[record.appType] }}
      </template>
      <template #scoringStrategy="{ record }">
        {{ APP_SCORING_STRATEGY_MAP[record.scoringStrategy] }}
      </template>
      <template #reviewStatus="{ record }">
        {{ REVIEW_STATUS_MAP[record.reviewStatus] }}
      </template>
      <template #reviewTime="{ record }">
        {{
          record.reviewTime &&
          dayjs(record.reviewTime).format("YYYY-MM-DD HH:mm:ss")
        }}
      </template>
      <template #createTime="{ record }">
        {{ dayjs(record.createTime).format("YYYY-MM-DD HH:mm:ss") }}
      </template>
      <template #updateTime="{ record }">
        {{ dayjs(record.updateTime).format("YYYY-MM-DD HH:mm:ss") }}
      </template>
      <template #optional="{ record }">
        <a-space>
          <a-button
            v-if="record.reviewStatus !== REVIEW_STATUS_ENUM.PASS"
            status="success"
            @click="doReview(record, REVIEW_STATUS_ENUM.PASS, '')"
          >
            Pass
          </a-button>
          <a-button
            v-if="record.reviewStatus !== REVIEW_STATUS_ENUM.REJECT"
            status="warning"
            @click="
              doReview(
                record,
                REVIEW_STATUS_ENUM.REJECT,
                'Failure to meet requirements'
              )
            "
          >
            Reject
          </a-button>
          <a-button status="danger" @click="doDelete(record)">Delete</a-button>
        </a-space>
      </template>
    </a-table>
  </a-config-provider>
</template>

<script setup lang="ts">
import enUS from "@arco-design/web-vue/es/locale/lang/en-us";
import { ref, watchEffect } from "vue";
import {
  deleteAppUsingPost,
  doAppReviewUsingPost,
  listAppByPageUsingPost,
} from "@/api/appController";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";
import {
  APP_SCORING_STRATEGY_MAP,
  APP_TYPE_MAP,
  REVIEW_STATUS_ENUM,
  REVIEW_STATUS_MAP,
} from "@/constant/app";

const locale = ref(enUS);
const formSearchParams = ref<API.AppQueryRequest>({});

// Initialise search criteria (should not be modified)
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

const searchParams = ref<API.AppQueryRequest>({
  ...initSearchParams,
});
const dataList = ref<API.App[]>([]);
const total = ref<number>(0);

/**
 * Load data
 */
const loadData = async () => {
  const res = await listAppByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.App) => {
  if (!record.id) {
    return;
  }

  const res = await deleteAppUsingPost({
    id: record.id,
  });
  if (res.data.code === 0) {
    loadData();
  } else {
    message.error("Delete failed，" + res.data.message);
  }
};

/**
 * Review
 * @param record
 * @param reviewStatus
 * @param reviewMessage
 */
const doReview = async (
  record: API.App,
  reviewStatus: number,
  reviewMessage?: string
) => {
  if (!record.id) {
    return;
  }

  const res = await doAppReviewUsingPost({
    id: record.id,
    reviewStatus,
    reviewMessage,
  });
  if (res.data.code === 0) {
    loadData();
  } else {
    message.error("Audit failures，" + res.data.message);
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
    dataIndex: "appName",
  },
  {
    title: "Description",
    dataIndex: "appDesc",
  },
  {
    title: "Icon",
    dataIndex: "appIcon",
    slotName: "appIcon",
  },
  {
    title: "App type",
    dataIndex: "appType",
    slotName: "appType",
  },
  {
    title: "Scoring strategy",
    dataIndex: "scoringStrategy",
    slotName: "scoringStrategy",
  },
  {
    title: "Review Status",
    dataIndex: "reviewStatus",
    slotName: "reviewStatus",
  },
  {
    title: "Review Message",
    dataIndex: "reviewMessage",
  },
  {
    title: "Review Time",
    dataIndex: "reviewTime",
    slotName: "reviewTime",
  },
  {
    title: "Reviewer id",
    dataIndex: "reviewerId",
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
