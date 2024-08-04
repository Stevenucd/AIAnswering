package com.steven.AIAnswering.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.steven.AIAnswering.model.entity.Post;
import com.steven.AIAnswering.model.entity.PostFavour;
import org.apache.ibatis.annotations.Param;

/**
 * Posy favour database operation
 *
 */
public interface PostFavourMapper extends BaseMapper<PostFavour> {

    /**
     * List favour post by page
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, @Param(Constants.WRAPPER) Wrapper<Post> queryWrapper,
                                    long favourUserId);

}




