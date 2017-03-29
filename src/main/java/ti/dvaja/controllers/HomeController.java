package ti.dvaja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ti.dvaja.persistence.Post;
import ti.dvaja.repositories.PostRepository;

import javax.inject.Inject;

/**
 * Created by Denko on 23/03/2017.
 */
@Controller
public class HomeController {
    @Inject
    private PostRepository postRepository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Post> posts = this.postRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("posts", posts);

        return "base-layout";
    }

}
