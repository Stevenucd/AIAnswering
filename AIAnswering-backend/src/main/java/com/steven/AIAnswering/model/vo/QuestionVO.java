package com.steven.AIAnswering.model.vo;

import cn.hutool.json.JSONUtil;
import com.steven.AIAnswering.model.dto.question.QuestionContentDTO;
import com.steven.AIAnswering.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Question VO
 *
 */
@Data
public class QuestionVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * question content (json)
     */
    private List<QuestionContentDTO> questionContent;

    /**
     * app id
     */
    private Long appId;

    /**
     * user id
     */
    private Long userId;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * create user information
     */
    private UserVO user;

    /**
     * VO to Object
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<QuestionContentDTO> questionContentDTO = questionVO.getQuestionContent();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContentDTO));
        return question;
    }

    /**
     * Object to VO
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        String questionContent = question.getQuestionContent();
        if (questionContent != null) {
            questionVO.setQuestionContent(JSONUtil.toList(questionContent, QuestionContentDTO.class));
        }
        return questionVO;
    }
}
