package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<String, Class>();

    private ProfilingControllerMBean controller = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception{

        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class<?> beanClass = o.getClass();

        if(beanClass.isAnnotationPresent(Profiling.class)){
            map.put(s, o.getClass());
        }

        return o;
    }

    public Object postProcessAfterInitialization(final Object bean, String s) throws BeansException {

        Class beanClass = map.get(s);

        if(beanClass!=null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("PROFILING START");
                        long before = System.nanoTime();
                        Object retVal = method.invoke(bean, args);
                        long after = System.nanoTime();
                        System.out.println(after-before);
                        System.out.println("PROFILING DONE");
                        return retVal;
                    } else {
                        return  method.invoke(bean, args);
                    }
                }
            });
        }
        return bean;
    }
}
