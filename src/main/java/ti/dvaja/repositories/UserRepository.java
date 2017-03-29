package ti.dvaja.repositories;

import org.springframework.data.repository.CrudRepository;
import ti.dvaja.persistence.User;

/**
 * Created by drichtar on 2/17/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
