package li.haomin.whale.crawl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import li.haomin.whale.model.KuYunPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public final class KuYunParser {
    private final static String URL = "https://www.kuyunzy.co";

    public static List<KuYunPage> getPage(String url) {
        String htmlBody = httpGet(url == null ? URL : url);
        Document document = Jsoup.parse(htmlBody, URL);

        String nextPage = null;
        Elements pages = document.select(".pages .pagelink_a");
        for (Element page : pages) {
            if ("下一页".equals(page.text())) {
                nextPage = page.absUrl("href");
            }
        }

        Elements rows = document.select("table tr.row");
        List<KuYunPage> kuYunPages = new ArrayList<>(rows.size());
        for (Element row : rows) {
            Element nameEl = row.child(0).selectFirst("a[href]");
            String name = nameEl.text();
            String detailPage = nameEl.absUrl("href");
            String area = row.child(1).text();
            String tag = row.child(2).text();
            String time = row.child(3).text();
            kuYunPages.add(new KuYunPage(name, area, tag, time, detailPage, nextPage));
        }
        return kuYunPages;
    }

    private static String httpGet(String url) {
        HttpResponse response = HttpUtil.createGet(url)
                .setHttpProxy("127.0.0.1", 10809)
                .execute();
        return response.body();
    }
}
