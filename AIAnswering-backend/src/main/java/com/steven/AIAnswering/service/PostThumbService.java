package com.steven.AIAnswering.service;

import com.steven.AIAnswering.model.entity.PostThumb;
import com.steven.AIAnswering.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Post Likes Service
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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
