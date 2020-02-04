package ti.dvaja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ti.dvaja.persistence.User;

/**
 * Created by drichtar on 2/17/17.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
