package channel.anwenchu.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by an_wch on 2018/5/2.
 */
@Component
public class SpringUtils implements ApplicationContextAware,Serializable {
    private static ApplicationContext applicationContext = null;
    //    /**
//     * Set the ApplicationContext that this object runs in.
//     * Normally this call will be used to initialize the object.
//     * <p>Invoked after population of normal bean properties but before an init callback such
//     * as {@link org.springframework.beans.factory.InitializingBean#afterPropertiesSet()}
//     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
//     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
//     * {@link MessageSourceAware}, if applicable.
//     * @param applicationContext the ApplicationContext object to be used by this object
//     * @throws ApplicationContextException in case of context initialization errors
//     * @throws BeansException if thrown by application context methods
//     * @see org.springframework.beans.factory.BeanInitializationException
//     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> requiredType){

        return applicationContext.getBean(requiredType);

    }

    public static Class getClassByName(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        Class obj=Class.forName(className);
        return obj;
    }
}
