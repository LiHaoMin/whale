package li.haomin.whale;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import li.haomin.whale.crawl.KuYunParser;
import li.haomin.whale.model.KuYunPage;

import java.util.List;

public class CrawlParser {

    public static void main(String[] args) {

        String url = null;
        DateTime now = DateUtil.date();

        KuYunParser.getDetail("https://www.kuyunzy.co/?m=vod-detail-id-51156.html");

//        do {
//            List<KuYunPage> pages = KuYunParser.getPage(url);
//            System.out.println(pages);
//            for (KuYunPage kuYunPage : pages) {
//                DateTime time = DateUtil.parse(kuYunPage.getTime());
//                if (!DateUtil.isSameDay(time, now)) {
//                    url = null;
//                    break;
//                }
//
////                url = kuYunPage.getNextPage();
//            }
//        } while (url != null);

        System.out.println(0);
    }
}
