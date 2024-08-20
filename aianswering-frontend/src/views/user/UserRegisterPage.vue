<template>
  <div id="userRegisterPage">
    <h2 style="margin-bottom: 16px">User register</h2>
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
      <a-form-item
        field="checkPassword"
        tooltip="Confirm password not less than 8 digits"
        label="Confirm password"
      >
        <a-input-password
          v-model="form.checkPassword"
          placeholder="Please enter confirm password"
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
            Register
          </a-button>
          <a-link href="/user/login">Old user login</a-link>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import API from "@/api";
import { userRegisterUsingPost } from "@/api/userController";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";

const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
} as API.UserRegisterRequest);

/**
 * Submit
 */
const handleSubmit = async () => {
  const res = await userRegisterUsingPost(form);
  if (res.data.code === 0) {
    message.success("Registration Success");
    router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    message.error("Registration Failed，" + res.data.message);
  }
};
</script>
