package ${packageName}.model.dto.${dataKey};

import ${packageName}.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询${dataName}请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ${upperDataKey}QueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * id
     */
    private Long notId;

    /**
     * Search text
     */
    private String searchText;

    /**
     * Title
     */
    private String title;

    /**
     * Content
     */
    private String content;

    /**
     * Tags list
     */
    private List<String> tags;

    /**
     * Create user id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}