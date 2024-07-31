package com.steven.AIAnswering.model.dto.file;

import java.io.Serializable;
import lombok.Data;

/**
 * File Upload Request
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UploadFileRequest implements Serializable {

    /**
     * service
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}