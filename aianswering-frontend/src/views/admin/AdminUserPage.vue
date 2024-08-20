<template>
  <a-form
    :model="formSearchParams"
    :style="{ marginBottom: '20px' }"
    layout="inline"
    @submit="doSearch"
  >
    <a-form-item field="userName" label="Username">
      <a-input
        allow-clear
        v-model="formSearchParams.userName"
        placeholder="Please enter username"
      />
    </a-form-item>
    <a-form-item field="userProfile" label="User Profile">
      <a-input
        allow-clear
        v-model="formSearchParams.userProfile"
        placeholder="Please enter user profile"
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
    <template #userAvatar="{ record }">
      <a-image width="64" :src="record.userAvatar" />
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
  deleteUserUsingPost,
  listUserByPageUsingPost,
} from "@/api/userController";
import API from "@/api";
import message from "@arco-design/web-vue/es/message";
import { dayjs } from "@arco-design/web-vue/es/_utils/date";

const formSearchParams = ref<API.UserQueryRequest>({});

// Initialise search criteria (should not be modified)
const initSearchParams = {
  current: 1,
  pageSize: 10,
};

const searchParams = ref<API.UserQueryRequest>({
  ...initSearchParams,
});
const dataList = ref<API.User[]>([]);
const total = ref<number>(0);

/**
 * Load data
 */
const loadData = async () => {
  const res = await listUserByPageUsingPost(searchParams.value);
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
const doDelete = async (record: API.User) => {
  if (!record.id) {
    return;
  }

  const res = await deleteUserUsingPost({
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
    title: "Account",
    dataIndex: "userAccount",
  },
  {
    title: "Username",
    dataIndex: "userName",
  },
  {
    title: "User avatar",
    dataIndex: "userAvatar",
    slotName: "userAvatar",
  },
  {
    title: "User Profile",
    dataIndex: "userProfile",
  },
  {
    title: "Authority",
    dataIndex: "userRole",
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
