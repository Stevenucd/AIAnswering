package com.steven.AIAnswering.aop;

import com.steven.AIAnswering.annotation.AuthCheck;
import com.steven.AIAnswering.common.ErrorCode;
import com.steven.AIAnswering.exception.BusinessException;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.model.enums.UserRoleEnum;
import com.steven.AIAnswering.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Authority verification AOP
 *
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * Executive Interception
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // Current logged in user
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // Permission not required. Pass.
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // You must have this permission to pass
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // If blocked, Reject
        if (UserRoleEnum.BAN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Must have administrator authority
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)) {
            // User do not have administrator authority, Reject
            if (!UserRoleEnum.ADMIN.equals(userRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // Pass the authority verification, Pass
        return joinPoint.proceed();
    }
}

