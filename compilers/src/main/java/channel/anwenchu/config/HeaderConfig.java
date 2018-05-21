package channel.anwenchu.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/5/2.
 */
@Data
@NoArgsConstructor
public class HeaderConfig {

    /**
     * 头名称
     */
    private String headerName;

    /**
     * 值
     */
    private String value;
}
