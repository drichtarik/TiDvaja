package ti.dvaja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ti.dvaja.repositories.RoleRepository;
import ti.dvaja.repositories.UserRepository;

import javax.inject.Inject;

/**
 * Created by drichtar on 2/17/17.
 */
@Controller
public class UserController {

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("view", "user/register");

        return "base-layout";
    }
}
