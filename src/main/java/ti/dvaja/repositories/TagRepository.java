package ti.dvaja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ti.dvaja.persistence.Tag;

/**
 * Created by drichtar on 6/12/17.
 */
public interface TagRepository extends JpaRepository<Tag, Integer>{
    Tag findByName(String name);
}
