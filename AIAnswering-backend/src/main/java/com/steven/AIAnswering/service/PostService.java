package com.steven.AIAnswering.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.steven.AIAnswering.model.dto.post.PostQueryRequest;
import com.steven.AIAnswering.model.entity.Post;
import com.steven.AIAnswering.model.vo.PostVO;

import javax.servlet.http.HttpServletRequest;

/**
 * Post Service
 *
 */
public interface PostService extends IService<Post> {

    /**
     * Verification
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);

    /**
     * Get Query Wrapper
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * Get Post VO
     *
     * @param post
     * @param request
     * @return
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * Get Post VO by Page
     *
     * @param postPage
     * @param request
     * @return
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request);
}
