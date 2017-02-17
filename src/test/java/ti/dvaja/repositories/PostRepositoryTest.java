package ti.dvaja.repositories;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by drichtar on 2/16/17.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"guru.springframework.domain"})
@EnableJpaRepositories(basePackages = {"ti.dvaja.repositories"})
@EnableTransactionManagement
public class PostRepositoryTest {
}
