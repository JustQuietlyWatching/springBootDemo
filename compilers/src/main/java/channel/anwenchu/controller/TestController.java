package channel.anwenchu.controller;

import channel.anwenchu.config.SpringUtils;
import channel.anwenchu.config.CUPZChannel;
import channel.anwenchu.config.BJUMSChannel;
import channel.anwenchu.config.TestConfig;
import channel.anwenchu.groovy.GroovyUtils;
import channel.anwenchu.request.OrgRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by an_wch on 2018/4/27.
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private SpringUtils springUtils;

    @Autowired
    private TestConfig testConfig;


    @Autowired
    private BJUMSChannel testArrary;

    @Autowired
    private CUPZChannel testArrary2;

    @GetMapping
    public BJUMSChannel index(){

//        Map map = new HashMap();
//        map.put("str", 123123);
//        map.put("StringUtils", new StringUtils());
//
//        String script = "$StringUtils.isNotEmpty($str)";
//        GroovyUtils.scriptRun(map, script);
//        GroovyUtils.test(map, script);


//        OrgRequest req = new OrgRequest();
//        req.setAccNo("hahahhaha");

//        GroovyUtils.getName()
//        springUtils.get()
//        Object obj = springUtils.getClassByName();

//        return testArrary.toString() + " <br/>" + testArrary2.toString();
//        return GroovyUtils.scriptRun(map, script) instanceof String;
        return testArrary;
    }
}
