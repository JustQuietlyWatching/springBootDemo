package cn.anwenchu.utils;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by an_wch on 2018/5/29.
 */
@SpringBootApplication
public class UtilsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UtilsApplication.class).web(true).run(args);
    }
}
