package channel.anwenchu.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by an_wch on 2018/5/2.
 */
@Data
@NoArgsConstructor
public class Entity {

    /**
     * 请求类型
     */
    private String type;

    /**
     * 回调地址
     */
    private String notify;


    /**
     * 请求配置
     */
    private RequestConfig req;

    /**
     * 返回配置
     */
    private ResponseConfig res;

    /**
     * Http配置
     */
    private ConnectionConfig connectionConfig;


//    public String toString() {
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
//    }


}
