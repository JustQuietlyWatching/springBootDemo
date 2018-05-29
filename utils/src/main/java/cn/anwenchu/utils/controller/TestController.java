package cn.anwenchu.utils.controller;

import cn.anwenchu.utils.formBean.OlFormBean;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

/**
 * Created by an_wch on 2018/5/29.
 */
@RestController
@RequestMapping("/")
public class TestController {


    @GetMapping
    public String toHtmlString(OlFormBean olFormBean) throws IllegalAccessException {

        olFormBean.setSignature(olFormBean.getSignature().replaceAll(" ", "+"));
        StringBuffer sb = new StringBuffer();
        sb.append("<html>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>" +
                "<title>浙江银联测试页面</title>" +
                "</head>" +
                "<body>" +
                "<form action='https://gateway.95516.com/gateway/api/frontTransReq.do' id='toPay' method='post'>");
        Field[] fields = FieldUtils.getAllFields(olFormBean.getClass());
        for (Field f : fields) {
            f.setAccessible(true);
            sb.append("<textarea name=\"" + f.getName() + "\">"+ String.valueOf(f.get(olFormBean)) + "</textarea>" );
        }
        sb.append("</form>" +
                "<script type='text/javascript'>document.getElementById('toPay').submit();</script>" +
                "</body>" +
                "</html>");
        return sb.toString();
    }
}
