package com.steven.AIAnswering.model.vo;

import cn.hutool.json.JSONUtil;
import com.steven.AIAnswering.model.entity.App;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Application VO
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@Data
public class AppVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * application name
     */
    private String appName;

    /**
     * application description
     */
    private String appDesc;

    /**
     * application icon
     */
    private String appIcon;

    /**
     * Type of application (0-scoring category, 1-assessment category)
     */
    private Integer appType;

    /**
     * Scoring strategy (0-custom, 1-AI)
     */
    private Integer scoringStrategy;

    /**
     * Audit Status: 0-Pending, 1-Passed, 2-Rejected
     */
    private Integer reviewStatus;

    /**
     * review message
     */
    private String reviewMessage;

    /**
     * reviewer id
     */
    private Long reviewerId;

    /**
     * review time
     */
    private Date reviewTime;

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
     * @param appVO
     * @return
     */
    public static App voToObj(AppVO appVO) {
        if (appVO == null) {
            return null;
        }
        App app = new App();
        BeanUtils.copyProperties(appVO, app);
        return app;
    }

    /**
     * Object to VO
     *
     * @param app
     * @return
     */
    public static AppVO objToVo(App app) {
        if (app == null) {
            return null;
        }
        AppVO appVO = new AppVO();
        BeanUtils.copyProperties(app, appVO);
        return appVO;
    }
}
