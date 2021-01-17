package li.haomin.whale.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KuYunPage {
    private String name;
    private String area;
    private String tag;
    private String time;
    private String detailPage;
    private String nextPage;

    public boolean isNextPage() {
        return this.nextPage != null;
    }
}
