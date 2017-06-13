package ti.dvaja.loaders;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ti.dvaja.persistence.Post;
import ti.dvaja.repositories.PostRepository;

import javax.inject.Inject;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * Created by drichtar on 2/16/17.
 */
@Component
public class PostLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PostRepository postRepository;

    private Logger log = Logger.getLogger(String.valueOf(PostLoader.class));

    @Inject
    public void setProductRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Post firstPost = new Post();
        firstPost.setTitle("My First Post");
        firstPost.setContent("Article Content");
        firstPost.setDate(new GregorianCalendar(2017, 01, 20));

        //postRepository.save(firstPost);

        log.info("Saved Post - id: " + firstPost.getId());
    }
}
