package li.haomin.whale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KuYunDetail {
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
    private Collection<KuYunCollection> collections;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KuYunCollection {
        private String name;
        private String url;
    }
}
