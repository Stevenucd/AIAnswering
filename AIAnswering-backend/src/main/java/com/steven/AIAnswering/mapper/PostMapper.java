package com.steven.AIAnswering.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.steven.AIAnswering.model.entity.Post;

import java.util.Date;
import java.util.List;

/**
 * Post database operation
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * Query post list (including deleted data)
     */
    List<Post> listPostWithDelete(Date minUpdateTime);

}




