package channel.anwenchu.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by an_wch on 2018/5/2.
 */
@Data
@NoArgsConstructor
public class ConnectionConfig {

    /**
     * 请求体
     */
    private String body;

    /**
     * 数据传输类型
     */
    private String contentType;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求时间
     */
    private String outTime;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求头
     */
    private List<HeaderConfig> headers;

}
