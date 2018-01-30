package space.anwenchu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import space.anwenchu.formBean.LoginFormBean;
import space.anwenchu.utils.Http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by an_wch on 2018/1/30.
 */
@Service
@Slf4j
public class TrainService {
    public static final String HOST = "https://kyfw.12306.cn";
    final String [] XY_ARRAY = {"37,47", "111,51", "181,48", "259,45", "34,121", "105,114", "173,118", "256,118"};

    /**
     * 校验验证码
     * @param loginFormBean
     * @return
     */
    public String checkCode(LoginFormBean loginFormBean) {
        // 校验验证码
        String [] code = loginFormBean.getCode().split(",");
        String login_code = "";
        for (int i =0; i < code.length; i++) {
            if (!"".equals(login_code)) {
                login_code = login_code + "," + XY_ARRAY[Integer.valueOf(code[i]) - 1];
            } else {
                login_code = login_code + XY_ARRAY[Integer.valueOf(code[i]) - 1];
            }
        }
        String result = Http.post( "https://kyfw.12306.cn/passport/captcha/captcha-check",
                "answer=" + login_code + "&login_site=E&rand=sjrand");


        return result;
    }

    /**
     * 登录
     * @param loginFormBean
     * @return
     */
    public String login(LoginFormBean loginFormBean) {
        String result = Http.post("https://kyfw.12306.cn/passport/web/login", String.format("username=%s&password=%s&appid=otn", loginFormBean.getUsername(), loginFormBean.getPassword()));

        JSONObject msgJson = JSON.parseObject(result);
        if (!"0".equals(msgJson.getString("result_code"))) {
            return msgJson.getString("result_message");
        }
        Http.post("https://kyfw.12306.cn/otn/login/userLogin", "_json_att=");
        Http.get("https://kyfw.12306.cn/otn/passport?redirect=/otn/login/userLogin");
        result = Http.post("https://kyfw.12306.cn/passport/web/auth/uamtk", "appid=otn");
        msgJson = JSON.parseObject(result);

        Http.post("https://kyfw.12306.cn/otn/uamauthclient", "tk="+msgJson.getString("newapptk"));

        Http.get("https://kyfw.12306.cn/otn/index/initMy12306");
        return result;
    }

    public void queryTrain() {
        Map<String, String> config = new HashMap<>();
        config.put("train_date", "2018-02-12");
        config.put("from_station", "BJP");
        config.put("to_station", "HDP");
//        config.put("purpose_codes", "ADULT");
        String result = Http.get(HOST + String.format("/otn/leftTicket/log?leftTicketDTO.train_date=%s&" +
                        "leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&" +
                        "purpose_codes=ADULT", config.get("train_date"),
                config.get("from_station"), config.get("to_station")));
        JSONObject msgJson = JSON.parseObject(result);
        if (!msgJson.getBoolean("status")){
            System.exit(0);
        }
        result = Http.get(HOST + String.format("/otn/leftTicket/queryZ?leftTicketDTO.train_date=%s&" +
                        "leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=ADULT", config.get("train_date"),
                config.get("from_station"), config.get("to_station")));
        msgJson = JSON.parseObject(result);
    }
}
