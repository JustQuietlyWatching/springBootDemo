package channel.anwenchu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Created by an_wch on 2018/5/2.
 */
@Data
//@RefreshScope
@Component
@ConfigurationProperties(BJUMSChannel.PREFIX)
public class BJUMSChannel {

    public static final String PREFIX = "bjums";


    /**
     * 通道配置
     */
    private ChanelInfo channel;

    /**
     * 通道名称
     */
    private String channelName;
}
