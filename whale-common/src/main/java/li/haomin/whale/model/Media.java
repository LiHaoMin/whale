package li.haomin.whale.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(indexes = {
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_tag", columnList = "tag"),
        @Index(name = "idx_area", columnList = "area"),
})
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String img;
    private String memo;
    private String actor;
    private String director;
    private String tag;
    private String area;
    private String time;
    private String status;
    private String language;
    private String dateline;
    private String description;
    @Transient
    private List<MediaUri> mediaUris;

    private Long createTime;
    private Long updateTime;
}
