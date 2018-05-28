package channel.anwenchu.demo.controller;

import channel.anwenchu.demo.reposrity.DemoRepository;
import channel.anwenchu.demo.service.BaseService;
import channel.anwenchu.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by an_wch on 2018/5/4.
 */
@RequestMapping
@RestController
public class TestController {

    @Autowired
    DemoRepository demoRepository;

    @Autowired
    TestService testService;



    @GetMapping("/test")
    public String findAll(){
        return testService.print();
    }
}
