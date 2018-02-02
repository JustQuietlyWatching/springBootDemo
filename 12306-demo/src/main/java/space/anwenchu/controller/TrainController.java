package space.anwenchu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import space.anwenchu.Bean.AutoBuyTicketModel;
import space.anwenchu.formBean.LoginFormBean;
import space.anwenchu.service.TrainService;
import space.anwenchu.utils.Http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by tengj on 2017/3/13.
 */
@Controller
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
    public String login(HttpServletRequest request) throws IOException {
        LoginFormBean loginFormBean = new LoginFormBean();
        loginFormBean.setCode(request.getParameter("code"));
        loginFormBean.setUsername(request.getParameter("username"));
        loginFormBean.setPassword(request.getParameter("password"));
        // 验证码
        String checkCodeResult = trainService.checkCode(loginFormBean);
        JSONObject msgJson = JSON.parseObject(checkCodeResult);
        log.info(msgJson.getString("result_message"));
        if (!"4".equals(msgJson.getString("result_code"))) {
//            return msgJson.getString("result_message");
            return "index";
        }
        // 登录
        trainService.login(loginFormBean);
        return "redirect:getusers";
    }


    /**
     * 登录
     */
    @PostMapping("/login2")
    public String login2(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        LoginFormBean loginFormBean = new LoginFormBean();
        loginFormBean.setCode(request.getParameter("code"));
        loginFormBean.setUsername(request.getParameter("username"));
        loginFormBean.setPassword(request.getParameter("password"));
        if (trainService.checkImg(loginFormBean.getCode())) {
            if (trainService.login(loginFormBean.getUsername(), loginFormBean.getPassword())) {
                session.setAttribute("userName", loginFormBean.getUsername());
                return "redirect:getusers";
            }
        }
        // 登录
        return "index";
    }

    @GetMapping("/getusers")
    public String getUsers(Model model) {
        model.addAttribute("users", trainService.getUsers());

        return "buy";
    }

    @GetMapping("/buy")
    public void buy() {
        AutoBuyTicketModel.PeriodTime periodTime = new AutoBuyTicketModel.PeriodTime();
        periodTime.setEndTime(12);
        periodTime.setStartTime(0);
        AutoBuyTicketModel.PeriodTime [] times = {periodTime};

//        String [] traintype = {"O"};

        AutoBuyTicketModel autoBuyTicketModel = new AutoBuyTicketModel();
        autoBuyTicketModel.setFromCity("北京");
        autoBuyTicketModel.setToCity("石家庄");
        autoBuyTicketModel.setTrainDate("2018-02-03");
        autoBuyTicketModel.setPeriodTime(times);
        autoBuyTicketModel.setPassengerTicketStr("#,0,1,安文楚,1,,,N");
        autoBuyTicketModel.setOldPassengerStr("安文楚,1,,1_");
//        autoBuyTicketModel.setTicketType(traintype);
        trainService.start(autoBuyTicketModel);
    }

//    @GetMapping("/getcode2")
//    public void getCode2(HttpServletResponse response) {
//        // 获取验证码
//        byte[] img = Http.getForFile( "https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&" + new Random().nextDouble());
//        response.setContentType("image/jpeg");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        try {
//            Http.ByteToFile(img, response);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @GetMapping("/getcode")
    public void getCode(HttpServletResponse response) {
        // 获取验证码
        byte[] img = trainService.init();
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
    public void queryTrain() throws IOException {
        trainService.queryTrain();
    }

}
