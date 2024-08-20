package com.steven.AIAnswering.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.steven.AIAnswering.model.entity.Post;

import java.util.Date;
import java.util.List;

/**
 * Post database operation
 *
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * Query post list (including deleted data)
     */
    List<Post> listPostWithDelete(Date minUpdateTime);

}




