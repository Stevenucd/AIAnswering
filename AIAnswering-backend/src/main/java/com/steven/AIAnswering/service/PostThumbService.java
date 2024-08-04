package com.steven.AIAnswering.service;

import com.steven.AIAnswering.model.entity.PostThumb;
import com.steven.AIAnswering.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Post Likes Service
 *
 */
public interface PostThumbService extends IService<PostThumb> {

    /**
     * Likes
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * Post Likes (Internal services)
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
