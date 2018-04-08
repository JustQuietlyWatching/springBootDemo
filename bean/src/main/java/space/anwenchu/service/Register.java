package space.anwenchu.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by an_wch on 2018/4/4.
 */
@Service("register")
public class Register implements InitializingBean, ApplicationContextAware {
    private Map<String, StorageType> serviceImpMap = new HashMap<String, StorageType>();

    private ApplicationContext applicationContext;

    // 获取Spring的上下文
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // 注册接口StorageType的所有实现的bean，
    // 可以按照自己的规则放入 注册中心 serviceImpMap里
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, UpStorageType> beanMap = applicationContext.getBeansOfType(UpStorageType.class);
        String name = null;
        for (UpStorageType storageType : beanMap.values()) {

            name = storageType.getClass().getSimpleName();

            System.out.println("---key:\t" + name);

            // 将类名，作为 key,
            serviceImpMap.put(name, storageType);
        }
    }

    public void getStorageType(String name) {
        serviceImpMap.get(name).handleStorage();
    }
}

