package li.haomin.whale.api;

import li.haomin.whale.model.Media;
import li.haomin.whale.model.MediaUri;
import li.haomin.whale.repository.MediaRepository;
import li.haomin.whale.repository.MediaUriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private MediaUriRepository mediaUriRepository;

    @GetMapping("/total")
    public Long total() {
        return mediaRepository.count();
    }

    @GetMapping("/page")
    public Page<Media> page(@RequestParam(required = false) String tag,
                            @RequestParam(required = false) String area,
                            @RequestParam(required = false) String name,
                            @RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "10") Integer size,
                            @RequestParam(defaultValue = "time") String sort,
                            @RequestParam(defaultValue = "desc") String dir) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(dir), sort));
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name", match -> match.contains());
        Media media = new Media();
        if (tag != null && !tag.isEmpty()) media.setTag(tag);
        if (area != null && !area.isEmpty()) media.setArea(area);
        if (name != null && !name.isEmpty()) media.setName(name);
        Page<Media> pages = mediaRepository.findAll(Example.of(media, exampleMatcher), pageable);
        return pages;
    }

    @GetMapping("/{id}")
    public Media media(@PathVariable Long id) {
        Optional<Media> media = mediaRepository.findById(id);
        if (media.isPresent()) {
            List<MediaUri> mediaUris = mediaUriRepository.findAllByMediaId(id);
            media.get().setMediaUris(mediaUris);
            return media.get();
        }
        return null;
    }

    @GetMapping("/tags")
    public List<String> tags() {

        return mediaRepository.findAllTags();
    }

    @GetMapping("/areas")
    public List<String> areas() {

        return mediaRepository.findAllAreas();
    }
}
