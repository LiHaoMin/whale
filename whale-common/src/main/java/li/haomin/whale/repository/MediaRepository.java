package li.haomin.whale.repository;

import li.haomin.whale.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    Media findFirstByName(String name);
}
