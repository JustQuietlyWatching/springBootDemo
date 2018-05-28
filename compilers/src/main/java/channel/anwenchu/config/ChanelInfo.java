package channel.anwenchu.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by an_wch on 2018/5/2.
 */

@Data
@NoArgsConstructor
public class ChanelInfo {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 密钥
     */
    private String key;

    /**
     * 功能列表
     */
    private List<Entity> entityList;
}
