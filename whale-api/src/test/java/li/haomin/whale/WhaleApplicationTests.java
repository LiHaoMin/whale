package li.haomin.whale;

import li.haomin.whale.model.User;
import li.haomin.whale.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.Optional;

@SpringBootTest
public class WhaleApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        User user = new User();
        user.setName("zzzx");
        user.setAddress("中国");
        userRepository.save(user);
        Optional<User> byId = userRepository.findById(1L);
        System.out.println(byId.get().getName());

        Boolean exists = userRepository.existsByAddress("中国");
        System.out.println("exists = " + exists);
        Optional<User> one = userRepository.findOne(Example.of(user));
        System.out.println("one.get() = " + one.get());

    }
}
