package li.haomin.whale.crawl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import li.haomin.whale.model.KuYunDetail;
import li.haomin.whale.model.KuYunPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static KuYunDetail getDetail(String url) {
        String htmlBody = httpGet(url == null ? URL : url);
        Document document = Jsoup.parse(htmlBody, URL);
        KuYunDetail kuYunDetail = new KuYunDetail();
        Element img = document.selectFirst("table .img img");
        if (img != null) {
            kuYunDetail.setImg(img.attr("src"));
        }
        Elements info = document.select("table table[cellspacing='1'] font");
        if (info != null && info.size() == 11) {
            kuYunDetail.setName(info.get(0).text());
            kuYunDetail.setMemo(info.get(1).text());
            kuYunDetail.setActor(info.get(2).text());
            kuYunDetail.setDirector(info.get(3).text());
            kuYunDetail.setTag(info.get(4).text());
            kuYunDetail.setArea(info.get(5).text());
            kuYunDetail.setTime(info.get(6).text());
            kuYunDetail.setStatus(info.get(7).text());
            kuYunDetail.setLanguage(info.get(8).text());
            kuYunDetail.setDateline(info.get(9).text());
            kuYunDetail.setDescription(info.get(10).text());
        } else {
            System.out.println("信息不完整...");
        }

        Elements title = document.select("table .bt h1");
        Element table = null;
        for (Element element : title) {
            if (element.text().contains("kkm3u8")) {
                table = element.parent().selectFirst("table");
            }
        }

        if (table != null) {
            Elements a = table.select("a");
            List<KuYunDetail.KuYunCollection> collect = a.stream().map(o -> {
                String[] split = o.text().split("\\$");
                return new KuYunDetail.KuYunCollection(split[0], split[1]);
            }).collect(Collectors.toList());
            kuYunDetail.setCollections(collect);
        }
        return kuYunDetail;
    }

    private static String httpGet(String url) {
        HttpResponse response = HttpUtil.createGet(url)
//                .setHttpProxy("127.0.0.1", 10809)
                .execute();
        return response.body();
    }
}
