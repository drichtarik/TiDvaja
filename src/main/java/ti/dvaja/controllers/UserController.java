package ti.dvaja.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ti.dvaja.bindingModel.UserBindingModel;
import ti.dvaja.persistence.Role;
import ti.dvaja.persistence.User;
import ti.dvaja.repositories.RoleRepository;
import ti.dvaja.repositories.UserRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/register")
    public String registerProcess(UserBindingModel userBindingModel) {
        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
            return "redirect:/register";
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword()),
                userBindingModel.getEmail()
        );

        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.addRole(userRole);

        this.userRepository.save(user);

        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("view", "user/login");

        return "base-layout";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse resposne) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, resposne, auth);
        }

        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userRepository.findByEmail(principal.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("view", "user/profile");

        return "base-layout";
    }

}
