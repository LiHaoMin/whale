package li.haomin.whale.repository;

import li.haomin.whale.model.MediaUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaUriRepository extends JpaRepository<MediaUri, Long> {

    List<MediaUri> findAllByMediaId(Long mediaId);
}
