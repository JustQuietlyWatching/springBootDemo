package channel.anwenchu.demo.service;


/**
 * Created by an_wch on 2018/5/5.
 */
public abstract class BaseService{

    abstract String getName();


    public String print() {
        return getName();
    }

}
