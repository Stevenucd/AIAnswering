import router from "@/router";
import { useLoginUserStore } from "@/store/userStore";
import ACCESS_ENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";

// Permission checking before entering the page
router.beforeEach(async (to, from, next) => {
  // Get current logged in user
  const loginUserStore = useLoginUserStore();
  let loginUser = loginUserStore.loginUser;

  // Only log in automatically if no previous attempts have been made to obtain the logged in user information.
  if (!loginUser || !loginUser.userRole) {
    // The purpose of await is to wait for the user to log in successfully and get the value before performing the subsequent actions.
    await loginUserStore.fetchLoginUser();
    loginUser = loginUserStore.loginUser;
  }

  // Permissions required for the current page
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN;
  // You must be logged in to jump to the page
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // If you are not logged in, you will be redirected to the login page
    if (
      !loginUser ||
      !loginUser.userRole ||
      loginUser.userRole === ACCESS_ENUM.NOT_LOGIN
    ) {
      next(`/user/login?redirect=${to.fullPath}`);
    }
    // If you are already logged in, determine if you have enough permissions, if not, jump to the no permissions page
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
