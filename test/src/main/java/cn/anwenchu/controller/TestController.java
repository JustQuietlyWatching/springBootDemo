package cn.anwenchu.controller;

import cn.anwenchu.domain.ChildA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by an_wch on 2018/6/13.
 */
@RestController
@Slf4j
public class TestController {

    @Value("${anwenchu}")
    String anwenchu;

    @GetMapping
    public void test() {
        ChildA a = new ChildA();

        log.info("getSharedVal======{}", a.getSharedVal());
        log.info("getSharedVal======{}", anwenchu);
    }
}
