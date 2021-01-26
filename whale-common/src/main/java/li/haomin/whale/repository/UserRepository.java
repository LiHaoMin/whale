package li.haomin.whale.repository;

import li.haomin.whale.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByAddress(String xxx);
}
