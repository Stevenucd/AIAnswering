package com.steven.AIAnswering.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.steven.AIAnswering.common.ErrorCode;
import com.steven.AIAnswering.exception.BusinessException;
import com.steven.AIAnswering.mapper.PostThumbMapper;
import com.steven.AIAnswering.model.entity.Post;
import com.steven.AIAnswering.model.entity.PostThumb;
import com.steven.AIAnswering.model.entity.User;
import com.steven.AIAnswering.service.PostService;
import com.steven.AIAnswering.service.PostThumbService;

import javax.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Post Likes Service Implementation
 *
 */
@Service
public class PostThumbServiceImpl extends ServiceImpl<PostThumbMapper, PostThumb>
        implements PostThumbService {

    @Resource
    private PostService postService;

    /**
     * Likes
     *
     * @param postId
     * @param loginUser
     * @return
     */
    @Override
    public int doPostThumb(long postId, User loginUser) {
        // Determine if an entity exists, get entity based on category
        Post post = postService.getById(postId);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // Has liked or not
        long userId = loginUser.getId();
        // Serial Likes per User
        PostThumbService postThumbService = (PostThumbService) AopContext.currentProxy();
        synchronized (String.valueOf(userId).intern()) {
            return postThumbService.doPostThumbInner(userId, postId);
        }
    }

    /**
     * 封装了事务的方法
     *
     * @param userId
     * @param postId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doPostThumbInner(long userId, long postId) {
        PostThumb postThumb = new PostThumb();
        postThumb.setUserId(userId);
        postThumb.setPostId(postId);
        QueryWrapper<PostThumb> thumbQueryWrapper = new QueryWrapper<>(postThumb);
        PostThumb oldPostThumb = this.getOne(thumbQueryWrapper);
        boolean result;
        // Liked
        if (oldPostThumb != null) {
            result = this.remove(thumbQueryWrapper);
            if (result) {
                // Likes number - 1
                result = postService.update()
                        .eq("id", postId)
                        .gt("thumbNum", 0)
                        .setSql("thumbNum = thumbNum - 1")
                        .update();
                return result ? -1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        } else {
            // Unliked
            result = this.save(postThumb);
            if (result) {
                // Likes number + 1
                result = postService.update()
                        .eq("id", postId)
                        .setSql("thumbNum = thumbNum + 1")
                        .update();
                return result ? 1 : 0;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

}




