package com.steven.AIAnswering.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.steven.AIAnswering.model.entity.Post;
import com.steven.AIAnswering.model.entity.PostFavour;
import com.steven.AIAnswering.model.entity.User;

/**
 * Post Favour Service
 *
 */
public interface PostFavourService extends IService<PostFavour> {

    /**
     * Post Favour
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostFavour(long postId, User loginUser);

    /**
     * List Favour Post by Page
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, Wrapper<Post> queryWrapper,
                                    long favourUserId);

    /**
     * Post Favour (Internal services)
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostFavourInner(long userId, long postId);
}
