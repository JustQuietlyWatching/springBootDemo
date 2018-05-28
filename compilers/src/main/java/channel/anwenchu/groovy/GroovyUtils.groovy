package channel.anwenchu.groovy



/**
 * Created by an_wch on 2018/4/27.
 */
class GroovyUtils {


    static def scriptRun(Map map, String script) {
        Binding b = new Binding()
        GroovyShell sh = new GroovyShell(b)
        map.each{k,v->
            b.setVariable(k, v)
        }
        println("===========" + sh.evaluate(script))
        return sh.evaluate(script)

    }

    static String toXML(def obj) {

    }

    static def test(Map map, String script) {


        Eval.me(map.get("str"), map.get("StringUtils"), script)
    }
}
