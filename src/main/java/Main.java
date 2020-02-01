import org.springframework.context.support.ClassPathXmlApplicationContext;
import quoter.Quoter;
import quoter.TerminatorQuoter;

public class Main {


    public static void main(String[] args) throws  Exception{


        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

//
        while (true) {
            Thread.sleep(1000);
            context.getBean(Quoter.class).sayQuote();
        }



    }
}
