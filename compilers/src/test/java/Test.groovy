import channel.anwenchu.Application
import channel.anwenchu.request.OrgRequest
import channel.anwenchu.request.RequestHeader
import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.reflect.FieldUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.springframework.boot.test.context.SpringBootTest

/**
 * Created by an_wch on 2018/4/27.
 */
@RunWith(JUnit4.class)
@Slf4j
@SpringBootTest(classes = Application.class)
class Test {

    RequestHeader header = new RequestHeader()
    OrgRequest body = new OrgRequest()

    @org.junit.Test
    void envlTest(){
        Map map = new HashMap()
        map.put("str", "123123123")
        map.put("StringUtils", new StringUtils())

        String script = 'StringUtils.isNotEmpty(str)'
        Binding b = new Binding()
        GroovyShell sh = new GroovyShell(b)
        map.each{k,v->
            b.setVariable(k, v)
        }
        println("===========" + sh.evaluate(script))
//        return sh.evaluate(script)


    }

    @org.junit.Test
    void toXmlTest() {
        def sw = new StringWriter()
        def xml = new MarkupBuilder(sw)
        xml.mkp.xmlDeclaration([version: '1.0', encoding: 'UTF-8'])
        header.reqDate = DateFormatUtils.format(new Date(), 'yyyyMMddHHmmss')

//        header.metaClass.properties.each {
//            if (null != it.field) {
//                println("${header[it.name]}")
//            }
//        }

        xml.merchant {
            head {
                header.metaClass.properties.each {
                    if (it.type != FieldUtils.getAllFields(RequestHeader.class)) {
                        "$it.name" {
                            mkp.yieldUnescaped("<![CDATA[${header[it.name]}]]>")
                        }
                    }
                }
            }

            body {
                body.metaClass.properties.each {
                    if (null != it.field) {
                        "$it.name" {
                            mkp.yieldUnescaped("<![CDATA[${body[it.name]}]]>")
                        }
                    }
                }
            }
        }
        println(sw.toString())

    }


}
