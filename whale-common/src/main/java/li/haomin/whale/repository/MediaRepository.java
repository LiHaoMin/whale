package li.haomin.whale.repository;

import li.haomin.whale.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    Media findFirstByName(String name);

    @Query(nativeQuery = true, value="SELECT tag FROM media GROUP BY tag ORDER BY tag")
    List<String> findAllTags();

    @Query(nativeQuery = true, value="SELECT area FROM media GROUP BY area ORDER BY area")
    List<String> findAllAreas();
}
