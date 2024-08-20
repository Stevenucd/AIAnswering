import ACCESS_ENUM from "@/access/accessEnum";

/**
 * Check Access (Determine if the currently logged in user has a certain privilege)
 * @param loginUser
 * @param needAccess
 * @return boolean
 */
const checkAccess = (
  loginUser: API.LoginUserVO,
  needAccess = ACCESS_ENUM.NOT_LOGIN
) => {
  // Get the permissions that the currently logged in user has (if there is no loginUser, it means not logged in)
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN;
  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  // If the user has to log in to access the
  if (needAccess === ACCESS_ENUM.USER) {
    // If the user is not logged in, then no privileges
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
  }
  // Admin access only
  if (needAccess === ACCESS_ENUM.ADMIN) {
    // If you are not an admin, you do not have permission
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false;
    }
  }
  return true;
};

export default checkAccess;
