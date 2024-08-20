import { defineStore } from "pinia";
import { ref } from "vue";
import { getLoginUserUsingGet } from "@/api/userController";
import ACCESS_ENUM from "@/access/accessEnum";

/**
 * Login user information global status
 */
export const useLoginUserStore = defineStore("loginUser", () => {
  const loginUser = ref<API.LoginUserVO>({
    userName: "Not logged in",
  });

  function setLoginUser(newLoginUser: API.LoginUserVO) {
    loginUser.value = newLoginUser;
  }

  async function fetchLoginUser() {
    const res = await getLoginUserUsingGet();
    if (res.data.code === 0 && res.data.data) {
      loginUser.value = res.data.data;
    } else {
      loginUser.value = { userRole: ACCESS_ENUM.NOT_LOGIN };
    }
  }

  return { loginUser, setLoginUser, fetchLoginUser };
});
