package ti.dvaja.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ti.dvaja.persistence.User;
import ti.dvaja.repositories.PostRepository;
import ti.dvaja.repositories.RoleRepository;
import ti.dvaja.repositories.UserRepository;

import javax.inject.Inject;

/**
 * Created by Denko on 09/04/2017.
 */
@Controller
@RequestMapping("admin/users")
public class AdminUserController {
    @Inject
    private UserRepository userRepository;

    @Inject
    private PostRepository postRepository;

    @Inject
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String listUsers(Model model) {
        Iterable<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/users/list");

        return "base-layout";
    }
}
