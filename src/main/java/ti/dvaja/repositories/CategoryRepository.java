package ti.dvaja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ti.dvaja.persistence.Category;

/**
 * Created by drichtar on 6/12/17.
 */
public interface CategoryRepository  extends JpaRepository<Category, Integer> {


}
