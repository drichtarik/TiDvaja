package ti.dvaja.services;

import ti.dvaja.persistence.Post;

import java.util.List;

/**
 * Created by drichtar on 2/17/17.
 */
public interface PostService {

    List<Post> findAll();
    List<Post> findLatest5();
    Post findById(Long id);
    Post create(Post post);
    Post edit(Post post);
    void deleteById(Long id);

}
