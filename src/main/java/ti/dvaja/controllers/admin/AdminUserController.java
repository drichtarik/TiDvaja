package ti.dvaja.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ti.dvaja.bindingModel.UserEditBindingModel;
import ti.dvaja.persistence.Post;
import ti.dvaja.persistence.Role;
import ti.dvaja.persistence.User;
import ti.dvaja.repositories.PostRepository;
import ti.dvaja.repositories.RoleRepository;
import ti.dvaja.repositories.UserRepository;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Denko on 09/04/2017.
 */
@Controller
@RequestMapping("admin/user")
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
        model.addAttribute("view", "admin/user/list");

        return "base-layout";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.userRepository.existsById(id)) {
            return "redirect:/admin/user/";
        }

        User user = this.userRepository.getOne(id);
        Iterable<Role> roles = roleRepository.findAll();


        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, UserEditBindingModel userEditBindingModel) {
        if(!this.userRepository.existsById(id)) {
            return "redirect:/admin/user/";
        }

        User user = this.userRepository.getOne(id);

        if(!StringUtils.isEmpty(userEditBindingModel.getPassword()) && !StringUtils.isEmpty(userEditBindingModel.getConfirmPassword())) {
            if(userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
                //BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(userEditBindingModel.getPassword());//(bCryptPasswordEncoder.encode(userEditBindingModel.getPassword())));
            }
        }

        user.setName(userEditBindingModel.getName());
        user.setEmail(userEditBindingModel.getEmail());
    
        Set<Role> roles = new HashSet<>();
        
        for (Integer roleId : userEditBindingModel.getRoles()) {
            roles.add(this.roleRepository.getOne(roleId));
        }

        this.userRepository.save(user);

        return "redirect:/admin/user/";
        
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        if(!this.userRepository.existsById(id)) {
            return "redirect:/admin/user/";
        }

        User user = this.userRepository.getOne(id);

        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.userRepository.existsById(id)) {
            return "redirect:/admin/user/";
        }

        User user = this.userRepository.getOne(id);

        for (Post post : user.getPosts()) {
            this.postRepository.delete(post);
        }

        this.userRepository.delete(user);

        return "redirect:/admin/user/";
    }
}
