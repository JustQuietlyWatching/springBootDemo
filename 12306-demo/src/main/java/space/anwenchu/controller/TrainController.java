package space.anwenchu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import space.anwenchu.formBean.LoginFormBean;
import space.anwenchu.service.TrainService;
import space.anwenchu.utils.Http;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Created by tengj on 2017/3/13.
 */
@RestController
@Slf4j
public class TrainController {

    @Autowired
    TrainService trainService;

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginFormBean loginFormBean){
        // 验证码
        String checkCodeResult = trainService.checkCode(loginFormBean);
        JSONObject msgJson = JSON.parseObject(checkCodeResult);
        log.info(msgJson.getString("result_message"));
        if (!"4".equals(msgJson.getString("result_code"))) {
            return msgJson.getString("result_message");
        }
        // 登录
        return trainService.login(loginFormBean);
    }

    @GetMapping("/getcode")
    public void getCode(HttpServletResponse response) {
        // 获取验证码
        byte[] img = Http.getForFile( "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&" + new Random().nextDouble());
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            Http.ByteToFile(img, response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/query")
    public void queryTrain() {
        trainService.queryTrain();
    }

}
