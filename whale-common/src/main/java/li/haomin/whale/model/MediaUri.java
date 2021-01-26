package li.haomin.whale.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class MediaUri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mediaId;

    private String title;
    private String url;

    private String begin;
    private String end;

    private Long createTime;
    private Long updateTime;
}
