package channel.anwenchu.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/5/2.
 */
@Data
@NoArgsConstructor
public class Field {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String value;

    /**
     * 字段赋值
     */
    private String setValue;


}
