package li.haomin.whale.schedule;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import li.haomin.whale.crawl.KuYunParser;
import li.haomin.whale.model.KuYunDetail;
import li.haomin.whale.model.KuYunPage;
import li.haomin.whale.model.Media;
import li.haomin.whale.model.MediaUri;
import li.haomin.whale.repository.MediaRepository;
import li.haomin.whale.repository.MediaUriRepository;
import lombok.extern.java.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Component
public class ParserSchedule implements CommandLineRunner {

    private String url;

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private MediaUriRepository mediaUriRepository;

    @Override
    public void run(String... args) throws Exception {
        long count = mediaRepository.count();
        log.info("count = " + count);
        if (count > 0 && args.length == 0) return;
        this.url = args[0];
        parser(true);
        log.info("run count " + mediaRepository.count());
    }

    @Scheduled(cron = "0 0 01 * * ?")
    public void everyDay() {
        this.url = null;
        log.info("everyDay start");
        parser(false);
        log.info("everyDay end");
    }

    private void parser(boolean isAll) {
        String url = this.url;
        DateTime now = DateUtil.date();

        do {
            log.info("page " + url);
            long begin = System.currentTimeMillis();
            List<KuYunPage> pages = KuYunParser.getPage(url);
            for (KuYunPage kuYunPage : pages) {
                DateTime time = DateUtil.parse(kuYunPage.getTime());
                if (!DateUtil.isSameDay(time, now) && !isAll) {
                    url = null;
                    break;
                }
                KuYunDetail kuYunDetail = KuYunParser.getDetail(kuYunPage.getDetailPage());
                log.info(kuYunDetail.toString());
                save(kuYunDetail);
                url = kuYunPage.getNextPage();
            }
            long end = System.currentTimeMillis();
            log.info("page cost " + (begin - end) + "ms");
        } while (url != null);
    }

    private void save(KuYunDetail detail) {
        final Long now = System.currentTimeMillis();
        final Long mediaId;
        Media first = mediaRepository.findFirstByName(detail.getName());
        if (first != null) {
            BeanUtils.copyProperties(detail, first, "img");
            first.setUpdateTime(now);
            mediaRepository.save(first);
            mediaId = first.getId();
            List<MediaUri> all = mediaUriRepository.findAllByMediaId(mediaId);
            mediaUriRepository.deleteAll(all);
        } else {
            Media media = new Media();
            media.setCreateTime(now);
            media.setUpdateTime(now);
            BeanUtils.copyProperties(detail, media);
            mediaRepository.save(media);
            mediaId = media.getId();
        }

        if (detail.getCollections() != null) {
            List<MediaUri> mediaUris = detail.getCollections().stream().map(o -> {
                MediaUri mediaUri = new MediaUri();
                mediaUri.setMediaId(mediaId);
                mediaUri.setTitle(o.getName());
                mediaUri.setUrl(o.getUrl());
                mediaUri.setCreateTime(now);
                mediaUri.setUpdateTime(now);
                return mediaUri;
            }).collect(Collectors.toList());
            mediaUriRepository.saveAll(mediaUris);
        }
    }
}
