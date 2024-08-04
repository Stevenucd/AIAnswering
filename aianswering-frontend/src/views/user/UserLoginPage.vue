<template>
  <div id="userLoginPage">
    <h2 style="margin-bottom: 16px">User Login</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="Account">
        <a-input
          v-model="form.userAccount"
          placeholder="Please enter account"
        />
      </a-form-item>
      <a-form-item
        field="userPassword"
        tooltip="Password not less than 8 digits"
        label="Password"
      >
        <a-input-password
          v-model="form.userPassword"
          placeholder="Please enter password"
        />
      </a-form-item>
      <a-form-item>
        <div
          style="
            display: flex;
            width: 100%;
            align-items: center;
            justify-content: space-between;
          "
        >
          <a-button type="primary" html-type="submit" style="width: 120px">
            Login
          </a-button>
          <a-link href="/user/register">New user register</a-link>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { userLoginUsingPost } from "@/api/userController";
import { useLoginUserStore } from "@/store/userStore";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const loginUserStore = useLoginUserStore();
const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
} as API.UserLoginRequest);

/**
 * Submit
 */
const handleSubmit = async () => {
  const res = await userLoginUsingPost(form);
  if (res.data.code === 0) {
    await loginUserStore.fetchLoginUser();
    message.success("Login Success");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    message.error("Login Failed，" + res.data.message);
  }
};
</script>
