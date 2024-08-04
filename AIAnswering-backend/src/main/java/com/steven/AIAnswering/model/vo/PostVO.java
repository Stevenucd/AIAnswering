package com.steven.AIAnswering.model.vo;

import cn.hutool.json.JSONUtil;
import com.steven.AIAnswering.model.entity.Post;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * Post VO
 *
 */
@Data
public class PostVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * Title
     */
    private String title;

    /**
     * content
     */
    private String content;

    /**
     * Likes number
     */
    private Integer thumbNum;

    /**
     * Favour number
     */
    private Integer favourNum;

    /**
     * Create user id
     */
    private Long userId;

    /**
     * Create time
     */
    private Date createTime;

    /**
     * Update time
     */
    private Date updateTime;

    /**
     * Tags list
     */
    private List<String> tagList;

    /**
     * Creator information
     */
    private UserVO user;

    /**
     * Has liked or not
     */
    private Boolean hasThumb;

    /**
     * Has favour or not
     */
    private Boolean hasFavour;

    /**
     * VO to Object
     *
     * @param postVO
     * @return
     */
    public static Post voToObj(PostVO postVO) {
        if (postVO == null) {
            return null;
        }
        Post post = new Post();
        BeanUtils.copyProperties(postVO, post);
        List<String> tagList = postVO.getTagList();
        post.setTags(JSONUtil.toJsonStr(tagList));
        return post;
    }

    /**
     * Object to VO
     *
     * @param post
     * @return
     */
    public static PostVO objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);
        postVO.setTagList(JSONUtil.toList(post.getTags(), String.class));
        return postVO;
    }
}
