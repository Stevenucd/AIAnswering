package com.steven.AIAnswering.controller;

import com.steven.AIAnswering.common.BaseResponse;
import com.steven.AIAnswering.common.ErrorCode;
import com.steven.AIAnswering.common.ResultUtils;
import com.steven.AIAnswering.exception.BusinessException;
import com.steven.AIAnswering.model.dto.postthumb.PostThumbAddRequest;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.service.PostThumbService;
import com.steven.AIAnswering.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Post Likes controller
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/post_thumb")
@Slf4j
public class PostThumbController {

    @Resource
    private PostThumbService postThumbService;

    @Resource
    private UserService userService;

    /**
     * Likes / Cancel Likes
     *
     * @param postThumbAddRequest
     * @param request
     * @return resultNum
     */
    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
                                         HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Login to like
        final User loginUser = userService.getLoginUser(request);
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return ResultUtils.success(result);
    }

}
