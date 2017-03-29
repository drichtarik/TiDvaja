package ti.dvaja.repositories;

import org.springframework.data.repository.CrudRepository;
import ti.dvaja.persistence.Post;

/**
 * Created by drichtar on 2/16/17.
 */
public interface PostRepository extends CrudRepository<Post, Integer> {
}