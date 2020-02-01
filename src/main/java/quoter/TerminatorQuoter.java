package quoter;

import javax.annotation.PostConstruct;


@Profiling
public class TerminatorQuoter implements Quoter {

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;


    @PostConstruct
    public void init(){
        System.out.println("Phase 2");
        System.out.println(repeat);
    }

    public TerminatorQuoter(){
        System.out.println("Phase 1");
    }


    @InjectRandomInt(min = 1, max = 7)
    private int repeat;

    @PostProxy
    public void sayQuote() {
        System.out.println("Phase 3");
        for (int i = 0; i < repeat; i++) {
            System.out.println("message = " + message);
        }

    }
}
