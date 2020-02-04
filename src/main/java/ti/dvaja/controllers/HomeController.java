package ti.dvaja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ti.dvaja.persistence.Category;
import ti.dvaja.persistence.Post;
import ti.dvaja.repositories.CategoryRepository;
import ti.dvaja.repositories.PostRepository;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by Denko on 23/03/2017.
 */
@Controller
public class HomeController {
    @Inject
    private PostRepository postRepository;
    @Inject
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("categories", categories);

        return "base-layout";
    }

    @RequestMapping("error/403")
    public String accesDenied(Model model) {
        model.addAttribute("view", "error/403");

        return "base-layout";
    }

    @GetMapping("/category/{id}")
    public String listPosts(Model model, @PathVariable Integer id) {
        if (!this.categoryRepository.existsById(id)) {
            return "redirect:/";
        }

        Category category = this.categoryRepository.getOne(id);
        Set<Post> posts = category.getPosts();

        model.addAttribute("posts", posts);
        model.addAttribute("category", category);
        model.addAttribute("view", "home/list-posts");

        return "base-layout";
    }

}
