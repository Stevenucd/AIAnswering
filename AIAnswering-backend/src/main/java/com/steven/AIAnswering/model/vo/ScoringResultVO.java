package com.steven.AIAnswering.model.vo;

import cn.hutool.json.JSONUtil;
import com.steven.AIAnswering.model.entity.ScoringResult;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * scoringResult视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class ScoringResultVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * Result name, e.g. logistician
     */
    private String resultName;

    /**
     * Result description
     */
    private String resultDesc;

    /**
     * result picture
     */
    private String resultPicture;

    /**
     * Collection of Resulting Properties, JSON，e.g. [I,S,T,J]
     */
    private List<String> resultProp;

    /**
     * Range of result scores, e.g. 80, means scores of 80 and above hit this result
     */
    private Integer resultScoreRange;

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
     * 封装类转对象
     *
     * @param scoringResultVO
     * @return
     */
    public static ScoringResult voToObj(ScoringResultVO scoringResultVO) {
        if (scoringResultVO == null) {
            return null;
        }
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultVO, scoringResult);
        scoringResult.setResultProp(JSONUtil.toJsonStr(scoringResultVO.getResultProp()));
        return scoringResult;
    }

    /**
     * 对象转封装类
     *
     * @param scoringResult
     * @return
     */
    public static ScoringResultVO objToVo(ScoringResult scoringResult) {
        if (scoringResult == null) {
            return null;
        }
        ScoringResultVO scoringResultVO = new ScoringResultVO();
        BeanUtils.copyProperties(scoringResult, scoringResultVO);
        scoringResultVO.setResultProp(JSONUtil.toList(scoringResult.getResultProp(), String.class));
        return scoringResultVO;
    }
}
