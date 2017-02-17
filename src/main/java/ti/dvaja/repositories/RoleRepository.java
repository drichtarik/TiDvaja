package ti.dvaja.repositories;

import org.springframework.data.repository.CrudRepository;
import ti.dvaja.persistence.Role;

/**
 * Created by drichtar on 2/17/17.
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
