package ti.dvaja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ti.dvaja.persistence.Post;

/**
 * Created by drichtar on 2/16/17.
 */
public interface PostRepository extends JpaRepository<Post, Integer> {
}