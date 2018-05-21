package channel.anwenchu.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by an_wch on 2018/5/2.
 */
@Data
@NoArgsConstructor
public class ResponseConfig {

    /**
     * 签名
     */
    private String sign;

    /**
     * 字段列表
     */
    private List<Field> fields;

}
