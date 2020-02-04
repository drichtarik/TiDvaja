package ti.dvaja.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ti.dvaja.persistence.Role;

/**
 * Created by drichtar on 2/17/17.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
