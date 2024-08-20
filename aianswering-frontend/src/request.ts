import axios from "axios";
import { Message } from "@arco-design/web-vue";

const myAxios = axios.create({
  baseURL: "http://localhost:8101",
  timeout: 60000,
  withCredentials: true,
});

// Global Request Interceptor
myAxios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Global Response Interceptor
myAxios.interceptors.response.use(
  function (response) {
    console.log(response);
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    const { data } = response;

    // not logged in
    if (data.code === 40100) {
      // Not a request to get user information and the user is not currently on the user login page, then jump to the login page
      if (
        !response.request.responseURL.includes("user/get/login") &&
        !window.location.pathname.includes("/user/login")
      ) {
        Message.warning("Login First");
        window.location.href = `/user/login?redirect=${window.location.href}`;
      }
    }

    return response;
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  }
);

export default myAxios;
