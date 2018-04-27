package space.anwenchu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import space.anwenchu.bean.AutoBuyTicketModel;
import space.anwenchu.bean.ConfirmOrderModel;
import space.anwenchu.bean.PreOrderModel;
import space.anwenchu.bean.TrainBean;
import space.anwenchu.formBean.LoginFormBean;
import space.anwenchu.formBean.QueryTrainFormBean;
import space.anwenchu.utils.Http;
import space.anwenchu.utils.HttpKeepSessionUtil;
import space.anwenchu.utils.StationCodeUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by an_wch on 2018/1/30.
 */
@Service
@Slf4j
public class TrainService {

    @Autowired
    private HttpKeepSessionUtil httpService;

    public static final String HOST = "https://kyfw.12306.cn";
    final String [] XY_ARRAY = {"37,47", "111,51", "181,48", "259,45", "34,121", "105,114", "173,118", "256,118"};
    private final static Map<String,StationCodeUtil.StationCode> stationCodes = StationCodeUtil.getData();


    private final static String domain = "kyfw.12306.cn";

    private final static String url1 = "https://kyfw.12306.cn/otn/leftTicket/init";
    private final static String url2 = "https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=${trainDate}&leftTicketDTO.from_station=${from}&leftTicketDTO.to_station=${to}&purpose_codes=ADULT";
    private final static String url3 = "https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
    private final static String url4 = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
    //	private final static String url5 = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
    private final static String url5 = "https://kyfw.12306.cn/passport/web/login";
    private final static String url6 = "https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
    private final static String url7 = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&0." + (new Random()).nextInt(1000000);
    private final static String url8 = "https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
    private final static String url9 = "https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?random=${stampTime}&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN=${REPEAT_SUBMIT_TOKEN}";
    private final static String url10 = "https://kyfw.12306.cn/otn/confirmPassenger/resultOrderForDcQueue";
    private final static String url11 ="https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn1";
    private final static String url12 ="https://kyfw.12306.cn/otn/passengers/query";

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
    public String login(LoginFormBean loginFormBean) throws IOException {
        String result = Http.post("https://kyfw.12306.cn/passport/web/login", String.format("username=%s&password=%s&appid=otn", loginFormBean.getUserName(), loginFormBean.getPassword()));

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

        getUsers();

//        queryTrain();
        return result;
    }

    public List<String> getUsers() {
        List<String> users = new ArrayList<>();
        try {
            String res = httpService.post(url12, "pageIndex=1&pageSize=1000");
            if(res.equals("")) {
                return users;
            }
            List datas = (List)((Map)(mapper.readValue(res, HashMap.class).get("data"))).get("datas");
            for(Object o : datas) {
                String user = "";
                Map m = (Map)o;
                user = user+m.get("passenger_name") +
                        "," + m.get("passenger_type") +
                        "," + m.get("passenger_id_no") +
                        "," + m.get("passenger_id_type_code");
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return users;
        }
    }

    public List<TrainBean> queryTrain(QueryTrainFormBean queryTrainFormBean) throws IOException {
        try {

            if(stationCodes.get(queryTrainFormBean.getFromCity()) == null) {
                throw new RuntimeException("出发城市不存在");
            }
            if(stationCodes.get(queryTrainFormBean.getToCity()) == null) {
                throw new RuntimeException("目标城市不存在");
            }
            httpService.get(url1);
            String queryUrl = url2.replace("${trainDate}", queryTrainFormBean.getTrainDate())
                    .replace("${from}", stationCodes.get(queryTrainFormBean.getFromCity()).getCode())
                    .replace("${to}", stationCodes.get(queryTrainFormBean.getToCity()).getCode());
            log.info("queryUrl: {}", queryUrl);
            byte[] result = httpService.get(queryUrl);
//            addCookieByQueryZ(preOrderModel, stationCodes);
            String res = new String(result,"UTF-8");
            log.info(res);
            Map maps = (Map)JSON.parse(res);
            List<String> trains = (List<String>)((Map)maps.get("data")).get("result");

            List<StationCodeUtil.StationCode> formStationCodeList = StationCodeUtil.getDataListByLikeName(queryTrainFormBean.getFromCity());
            List<StationCodeUtil.StationCode> toStationCodeList = StationCodeUtil.getDataListByLikeName(queryTrainFormBean.getToCity());

            List<TrainBean> trainBeanList = new ArrayList<>();
            for(String train : trains) {
                String[] data = train.split("\\|");
                TrainBean trainBean = new TrainBean();
                trainBean.setSecretStr(data[0]);
                trainBean.setTrainNo(data[3]);

                for (StationCodeUtil.StationCode it : formStationCodeList) {
                    if (data[6].equals(it.getCode())) {
                        trainBean.setStartStation(it.getName());
                        break;
                    }
                }
                for (StationCodeUtil.StationCode it : toStationCodeList) {
                    if (data[7].equals(it.getCode())) {
                        trainBean.setEndStation(it.getName());
                        break;
                    }
                }
                trainBean.setStartTime(data[8]);
                trainBean.setEndTime(data[9]);
                trainBean.setLongDate(data[10]);
                trainBean.setTrainDate(data[13]);
                trainBean.setSoftSeat(data[32]);
                trainBean.setOneSeat(data[31]);
                trainBean.setTwoSeat(data[30]);
                trainBean.setSpecialSleep(data[21]);
                trainBean.setSoftSleep(data[23]);
                trainBean.setMoveSleep(data[33]);
                trainBean.setHardSleep(data[28]);
                trainBean.setSoftSeat(data[24]);
                trainBean.setHardSeat(data[29]);
                trainBean.setNoSeat(data[26]);
                trainBean.setIsBuy(data[11]);
                trainBeanList.add(trainBean);
            }
            return trainBeanList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    private boolean submit(Map<String, String> config, TrainBean trainBean) {
        String result = Http.post(HOST + "/otn/login/checkUser", "_json_att=");
        String parameter = "secretStr=" + trainBean.getSecretStr() +"&train_date=2018-02-25&back_train_date=2018-02-25&tour_flag=dc&purpose_codes=ADULT&query_from_station_name=北京&query_to_station_name=邯郸%s&undefined=";
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":"N","messages":[],"validateMessages":{}}
//        result = Http.post(HOST + "/otn/leftTicket/submitOrderRequest", String.format(parameter, trainBean.getSecretStr(),
//                "2018-02-25", "2018-02-25", "北京", "邯郸"));
        String test = "secretStr=SHPxyDEDjkvfdVZzczKATiLgMMgDBAuImeGrdeEEweJK%2F0fdUSZtU2sG1LL8IPV8ja1PoSax8PZn%0AUoerUXANzdP6OyP4Dil3GQKxiOWhAlNlCGbCB4%2B7rEAyRouTKqIIw6ysuJVhzKHJUNo1Kp55D108%0AmbU18zgTa9hUCPElylKRkdvMb3o7TNGPI97rNtRyCg2Ha%2FBLxWcBdQk9cjKYuHrenHH4jgS7gTCU%0AxLbcVxdqWaLovCZ8xbaw8EJrOykW&train_date=2018-02-25&back_train_date=2018-01-31&tour_flag=dc&purpose_codes=ADULT&query_from_station_name=北京&query_to_station_name=邯郸&undefined";
        try {
            /**
             * "{\"validateMessagesShowId\":\"_validatorMessage\",\"status\":true,\"httpstatus\":200,\"data\":\"N\",\"messages\":[],\"validateMessages\":{}}"
             */
            String res = httpService.post(url3, test);
            log.info(res);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        result = Http.post(HOST + "/otn/confirmPassenger/initDc", "_json_att=");
        JSONObject msgJson = JSON.parseObject(result);
        return msgJson.getBoolean("status");

    }



    public byte[] init() {

        try {
            String res = new String(httpService.get("https://kyfw.12306.cn/otn/login/init"),"UTF-8");
            String param = res.substring(res.indexOf("/otn/dynamicJs/") + 15, res.indexOf("/otn/dynamicJs/") + 15 + 7);
            httpService.get("https://kyfw.12306.cn/otn/dynamicJs/${param}".replace("${param}", param));
            res = httpService.post("https://kyfw.12306.cn/passport/web/auth/uamtk","appid=otn");
            log.info(res);
            byte[] d = httpService.get("https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0." + (new Random()).nextInt(1000000));
            res = new String(httpService.get("https://kyfw.12306.cn/otn/HttpZF/logdevice?algID=G6OqpQOFB9&hashCode=vnaoGDin6xyyXFMY3tH8s8XowIhp5ITIBxtYPQBcdYM&FMQw=0&q4f3=zh-CN&"
                    + "VPIf=1&custID=133&VEek=unknown&dzuS=0&yD16=0&EOQP=8f58b1186770646318a429cb33977d8c&lEnu=3232235930&jp76=e237f9703f53d448d77c858b634154a5&hAqN=Win32&platform=WEB&"
                    + "ks0Q=b9a555dce60346a48de933b3e16ebd6e&TeRS=1040x1920&tOHY=24xx1080x1920&Fvje=i1l1o1s1&q5aJ=-8&"
                    + "wNLf=99115dfb07133750ba677d055874de87&0aew=Mozilla/5.0%20(Windows%20NT%2010.0;%20WOW64)%20AppleWebKit/537.36%20(KHTML,%20like%20Gecko)%20Chrome/63.0.3239.132%20Safari/537.36&"
                    + "E3gR=5fd7dade1220db783b42e35a2fd353d7&timestamp=" + System.currentTimeMillis()),"UTF-8");
            log.info(res);
//			res = res.replace("callbackFunction('", "").replace(")", "");
//			Map<String, String> data = mapper.readValue(res, HashMap.class);
//			httpService.addCookie("RAIL_DEVICEID", data.get("dfp"), domain, "");
//			httpService.addCookie("RAIL_EXPIRATION", data.get("exp"), domain, "");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



    public boolean login(String username, String password) {
        try {
            String result = httpService.post("https://kyfw.12306.cn/passport/web/login","appid=otn&username=" + username + "&password=" + password);
            log.info(result);
            result = httpService.post("https://kyfw.12306.cn/passport/web/auth/uamtk","appid=otn");
            log.info(result);
            ObjectMapper mapper = new ObjectMapper();
            Map d = mapper.readValue(result, HashMap.class);
            if(d.get("result_message").equals("验证通过")) {
                result = httpService.post("https://kyfw.12306.cn/otn/uamauthclient","tk=" + d.get("newapptk"));
                log.info(result);
                if(result.indexOf("验证通过") > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }




    public boolean checkImg(String imgCode) throws IOException {
        // 校验验证码
        String [] code = imgCode.split(",");
        String login_code = "";
        for (int i =0; i < code.length; i++) {
            if (!"".equals(login_code)) {
                login_code = login_code + "," + XY_ARRAY[Integer.valueOf(code[i]) - 1];
            } else {
                login_code = login_code + XY_ARRAY[Integer.valueOf(code[i]) - 1];
            }
        }
        String result = httpService.post("https://kyfw.12306.cn/passport/captcha/captcha-check", "login_site=E&rand=sjrand&answer="+login_code);
        log.info(result);
        if(result.indexOf("\"result_code\":\"4\"") > 0) {
            return true;
        }
        return false;
    }


    private static ObjectMapper mapper = new ObjectMapper();


    public Map<String,String> start(AutoBuyTicketModel model){
        Map<String,String> res = new HashMap<>();
        try {
            PreOrderModel preOrderModel = new PreOrderModel();
            BeanUtils.copyProperties(model,preOrderModel);
            Map<String,Object> data = mapper.readValue(queryZ(preOrderModel), HashMap.class);
            data = (Map<String, Object>) data.get("data");
            List<String> arr = (List<String>)data.get("result");
            if( screeningTrain(model,arr,preOrderModel)) {
                res.put("messageStatus", "success");
                return res;
            } else {
                res.put("messageStatus", "fail");
                return res;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String queryZ(PreOrderModel preOrderModel) {
        try {
            if(stationCodes.get(preOrderModel.getFromCity()) == null) {
                throw new RuntimeException("出发城市不存在");
            }
            if(stationCodes.get(preOrderModel.getToCity()) == null) {
                throw new RuntimeException("目标城市不存在");
            }
            httpService.get(url1);
            byte[] result = httpService.get(url2.replace("${trainDate}", preOrderModel.getTrainDate())
                    .replace("${from}", stationCodes.get(preOrderModel.getFromCity()).getCode())
                    .replace("${to}", stationCodes.get(preOrderModel.getToCity()).getCode()));
            addCookieByQueryZ(preOrderModel, stationCodes);
            String res = new String(result,"UTF-8");
            log.info(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



    private boolean screeningTrain(AutoBuyTicketModel model,List<String> arr,PreOrderModel preOrderModel) {
        for(String train : arr) {
            String[] data = train.split("\\|");
            if(data[0].equals(""))
                continue;
            int i = 0;
            for(;i<model.getPeriodTime().length; i++) {
                int hour = Integer.parseInt(data[8].split(":")[0]);
                if( hour >= model.getPeriodTime()[i].getStartTime() && hour <= model.getPeriodTime()[i].getEndTime()) {
                    break;
                }
            }
            if(i == model.getPeriodTime().length)
                continue;
            //硬座
            if(!data[29].equals("") && !data[29].equals("无") && model.getTrainDate().contains("1")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","1"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //软座
            if(!data[24].equals("") && !data[24].equals("无") && model.getTrainDate().contains("2")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","2"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //硬卧
            if(!data[28].equals("") && !data[28].equals("无") && model.getTrainDate().contains("3")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","3"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //软卧
            if(!data[23].equals("") && !data[23].equals("无") && model.getTrainDate().contains("5")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","5"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //高级软卧
            if(!data[21].equals("") && !data[21].equals("无") && model.getTrainDate().contains("6")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","6"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //二等
            if(!data[30].equals("") && !data[30].equals("无") && model.getTrainDate().contains("O")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","O"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //一等
            if(!data[31].equals("") && !data[31].equals("无") && model.getTrainDate().contains("M")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","M"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //特等
            if(!data[32].equals("") && !data[32].equals("无") && model.getTrainDate().contains("P")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","P"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //动卧
            if(!data[33].equals("") && !data[33].equals("无") && model.getTrainDate().contains("4")) {
                try {
                    preOrderModel.setStationTrainCode(data[3]);
                    preOrderModel.setPassengerTicketStr(model.getPassengerTicketStr().replaceAll("#","4"));
                    Map<String,String> res = preOrder(preOrderModel);
                    if(res.get("status").equals("2") && res.get("messageStatus").equals("success")) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }



    public Map<String,String> preOrder(PreOrderModel preOrderModel) {

        try {
            Map<String,Object> data = mapper.readValue(queryZ(preOrderModel), HashMap.class);
            data = (Map<String, Object>) data.get("data");
            List<String> arr = (List<String>)data.get("result");
            for(String train : arr) {
                String[] t = train.split("\\|");
                if(t[3].equals(preOrderModel.getStationTrainCode())) {
                    if(t[0] == null || t[0].equals("")) {
                        throw new RuntimeException("没有票了");
                    }
                    String res = sendSubmitOrderRequest(t,preOrderModel);
                    if(res.indexOf("200") > 0) {
                        res = httpService.post(url4, "_json_att=");
                        log.info(res);
                        Map<String,String> cachData = new HashMap<>();
                        getInitDcData(res,cachData);
                        getQueryZData(t,cachData);

                        res = sendCheckOrderInfo(cachData,preOrderModel);
                        ConfirmOrderModel confirmOrderModel = new ConfirmOrderModel();
                        confirmOrderModel.setPassengerTicketStr(preOrderModel.getPassengerTicketStr());
                        confirmOrderModel.setOldPassengerStr(preOrderModel.getOldPassengerStr());
                        confirmOrderModel.setRepeatSubmitToken(cachData.get("REPEAT_SUBMIT_TOKEN"));
                        confirmOrderModel.setTrainLocation(cachData.get("train_location"));
                        confirmOrderModel.setKeyCheckIsChange(cachData.get("keyCheckIsChange"));
                        confirmOrderModel.setLeftTicketStr(cachData.get("leftTicketStr"));
                        if(confirmOrder(confirmOrderModel).get("messageStatus").equals("success")) {
                            cachData.put("status", "2");
                            cachData.put("messageStatus", "success");
                        } else {
                            cachData.put("status", "2");
                            cachData.put("messageStatus", "fail");
                        }

                        return cachData;
                    } else {
                        throw new RuntimeException("重新提交");
                    }
                }
            }
            throw new RuntimeException("没找到" + preOrderModel.getStationTrainCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<String,String> confirmOrder(ConfirmOrderModel confirmOrderModel) {
        Map<String,String> resultData = new HashMap<>();
        try {
            String res = sendConfirmSingleForQueue(confirmOrderModel);
            if(res.equals("")) {
                throw new RuntimeException("请重新请求");
            }
            Map data = mapper.readValue(res, HashMap.class);
            if(!(Boolean)data.get("status")) {
                throw new RuntimeException(data.get("data").toString());
            }
            data = (Map)(data.get("data"));
            if((Boolean)data.get("submitStatus")){
                String orderId = sendQueryOrderWaitTime(confirmOrderModel);
                if(orderId == null) {
                    resultData.put("messageStatus", "fail");
                    resultData.put("message", "没抢到");
                } else {
                    if(resultOrderForDcQueue(orderId,confirmOrderModel.getRepeatSubmitToken())) {
                        resultData.put("messageStatus", "success");
                    } else {
                        resultData.put("messageStatus", "fail");
                        resultData.put("message", "没抢到");
                    }
                }
                return resultData;
            } else {
                throw new RuntimeException(data.get("errMsg").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String sendConfirmSingleForQueue(ConfirmOrderModel confirmOrderModel) {
        try {
            String params = "randCode=&purpose_codes=00" +
                    "&passengerTicketStr=" +  confirmOrderModel.getPassengerTicketStr() +
                    "&oldPassengerStr=" + confirmOrderModel.getOldPassengerStr() +
                    "&choose_seats=&seatDetailType=000&whatsSelect=1&_json_att=&roomType=00" +
                    "&dwAll=N" +
                    "&REPEAT_SUBMIT_TOKEN=" + confirmOrderModel.getRepeatSubmitToken() +
                    "&train_location=" + confirmOrderModel.getTrainLocation() +
                    "&key_check_isChange=" + confirmOrderModel.getKeyCheckIsChange() +
                    "&leftTicketStr=" + confirmOrderModel.getLeftTicketStr();
            String res = httpService.post(url8, params);
            log.info(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String sendSubmitOrderRequest(String[] t,PreOrderModel preOrderModel) {
        try {
            String params = "secretStr=" + t[0] + "&train_date=" + preOrderModel.getTrainDate() +
                    "&back_train_date=" +  (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) +
                    "&tour_flag=dc&purpose_codes=ADULT" +
                    "&query_from_station_name=" + preOrderModel.getFromCity() +
                    "&query_to_station_name=" + preOrderModel.getToCity() +"&undefined";
            String res = httpService.post(url3, params);
            log.info(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String sendCheckOrderInfo(Map<String,String> cachData,PreOrderModel preOrderModel) {
        try {
            String params = "cancel_flag=2&bed_level_order_num=000000000000000000000000000000" +
                    "&passengerTicketStr=" +  preOrderModel.getPassengerTicketStr() +
                    "&oldPassengerStr=" + preOrderModel.getOldPassengerStr() +
                    "&tour_flag=dc&randCode=&whatsSelect=1&_json_att=" +
                    "&REPEAT_SUBMIT_TOKEN=" + cachData.get("REPEAT_SUBMIT_TOKEN");
            String res = httpService.post(url6, params);
            log.info(res);
            String paramsQueueCount = "train_date=" + new Date() +
                    "&train_no=" + cachData.get("train_no") +
                    "&stationTrainCode=" + preOrderModel.getStationTrainCode() +
                    "&seatType=1" +
                    "&fromStationTelecode=" + cachData.get("fromStationTelecode") +
                    "&toStationTelecode=" + cachData.get("toStationTelecode") +
                    "&leftTicket=" + cachData.get("leftTicketStr") +
                    "&purpose_codes=00" +
                    "&train_location=PC" +
                    "&_json_att=" +
                    "&REPEAT_SUBMIT_TOKEN=" + cachData.get("REPEAT_SUBMIT_TOKEN");
            String resQueueCount = httpService.post("https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount", paramsQueueCount);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String sendQueryOrderWaitTime(ConfirmOrderModel confirmOrderModel) {
        try {
            int i = 10;
            while(i>0) {
                i--;
                byte[] b = httpService.get(url9.replace("${stampTime}", (new Date().getTime()) + "")
                        .replace("${REPEAT_SUBMIT_TOKEN}", confirmOrderModel.getRepeatSubmitToken()));
                String res = new String(b,"UTF-8");
                log.info(res);
                Map data = mapper.readValue(res, HashMap.class);
                data = (Map)data.get("data");
                if(data.get("orderId") != null) {
                    return data.get("orderId").toString();
                } else {
                    if(data.get("msg") != null) {
                        throw new RuntimeException(data.get("msg").toString());
                    }
                }
                Thread.sleep(3000);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean resultOrderForDcQueue(String orderId,String repeatSubmitToken) {
        try {
            int i = 3;
            while(i>0) {
                i--;
                String params = "&orderSequence_no=" +  orderId +
                        "&_json_att=" +
                        "&REPEAT_SUBMIT_TOKEN=" + repeatSubmitToken ;
                String res = httpService.post(url10, params);
                log.info(res);
                Map data = (Map)(mapper.readValue(res, HashMap.class).get("data"));
                if((Boolean)data.get("submitStatus")){
                    return true;
                }
//                Thread.sleep(3000);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void getQueryZData(String[] arr,Map<String,String> data){
        data.put("train_no",arr[2]);
        data.put("fromStationTelecode",arr[6]);
        data.put("toStationTelecode",arr[7]);
        data.put("leftTicketStr",arr[12]);
        data.put("train_date",arr[13]);
        data.put("train_location",arr[15]);
    }

    private void getInitDcData(String data,Map<String,String> d){
        d.put("REPEAT_SUBMIT_TOKEN",data.substring(data.indexOf("globalRepeatSubmitToken = '")+27, data.indexOf("globalRepeatSubmitToken = '")+32+27));
        d.put("keyCheckIsChange",data.substring(data.indexOf("key_check_isChange':'")+21, data.indexOf("key_check_isChange':'")+21+56));
    }

    private void addCookieByQueryZ(PreOrderModel preOrderModel,Map<String,StationCodeUtil.StationCode> data) {
        try {
            httpService.addCookie("_jc_save_toDate", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()), domain, "");
            httpService.addCookie("_jc_save_fromDate", preOrderModel.getTrainDate(), domain, "");
            httpService.addCookie("_jc_save_wfdc_flag", "dc", domain, "");
            httpService.addCookie("_jc_save_toStation", URLEncoder.encode(preOrderModel.getToCity() + "," + data.get(preOrderModel.getToCity()).getCode(),"UTF-8"), domain, "");
            httpService.addCookie("_jc_save_fromStation", URLEncoder.encode(preOrderModel.getFromCity() + "," + data.get(preOrderModel.getFromCity()).getCode(),"UTF-8"), domain, "");
            httpService.addCookie("_jc_save_showIns", "true", domain, "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
