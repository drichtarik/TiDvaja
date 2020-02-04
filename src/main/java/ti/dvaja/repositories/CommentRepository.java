package ti.dvaja.repositories;

import org.springframework.data.repository.CrudRepository;
import ti.dvaja.persistence.Comment;

/**
 * Created by Denis Richtarik on 04, 2018
 * denis.richtarik@gmail.com
 **/
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
