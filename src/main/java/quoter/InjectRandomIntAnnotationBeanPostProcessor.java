package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {


    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Field[] fields = o.getClass().getDeclaredFields();
        for(Field field : fields){
            InjectRandomInt injectRandomInt = field.getAnnotation(InjectRandomInt.class);

            if(injectRandomInt!=null) {
                int min = injectRandomInt.min();
                int max = injectRandomInt.max();
                Random random = new Random();
                int i = min+random.nextInt(max - min);
                field.setAccessible(true);
                ReflectionUtils.setField(field, o, i);

            }
        }

        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
