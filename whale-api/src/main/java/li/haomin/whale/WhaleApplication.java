package li.haomin.whale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WhaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhaleApplication.class, args);
    }

}
