package ti.dvaja.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ti.dvaja.bindingModel.PostBindingModel;
import ti.dvaja.configuration.UserDetail;
import ti.dvaja.persistence.Post;
import ti.dvaja.persistence.User;
import ti.dvaja.repositories.PostRepository;
import ti.dvaja.repositories.UserRepository;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Denko on 25/03/2017.
 */
@Controller
public class PostController {
    @Inject
    private PostRepository postRepository;
    @Inject
    private UserRepository userRepository;

    @GetMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        model.addAttribute("view", "post/create");

        return "base-layout";
    }

    @PostMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(PostBindingModel postBindingModel) {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByUsername(user.getUsername());

        Set<User> authors = new HashSet<>();
        authors.add(userEntity);

        Post postEntity = new Post(
                postBindingModel.getTitle(),
                postBindingModel.getContent(),
                authors
        );

        this.postRepository.save(postEntity);

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.postRepository.exists(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.findOne(id);

        model.addAttribute("post", post);
        model.addAttribute("view", "post/details");

        return "base-layout";
    }

    @GetMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.postRepository.exists(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.findOne(id);

        model.addAttribute("post", post);
        model.addAttribute("view", "post/edit");

        return "base-layout";
    }

    @PostMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(PostBindingModel postBindingModel, @PathVariable Integer id) {
        if (!this.postRepository.exists(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.findOne(id);

        post.setContent(postBindingModel.getContent());
        post.setTitle(postBindingModel.getTitle());

        this.postRepository.save(post);

        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.postRepository.exists(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.findOne(id);

        model.addAttribute("post", post);
        model.addAttribute("view", "post/delete");

        return "base-layout";
    }

    @PostMapping("/post/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.postRepository.exists(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.findOne(id);

        this.postRepository.delete(post);

        return "redirect:/";
    }
}
