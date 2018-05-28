package channel.anwenchu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by an_wch on 2018/5/3.
 */
@Component
public class TestConfig {

    @Value("${anwenchu}")
    private String anwenchu;
}
