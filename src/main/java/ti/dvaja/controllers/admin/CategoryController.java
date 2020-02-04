package ti.dvaja.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ti.dvaja.bindingModel.CategoryBindingModel;
import ti.dvaja.persistence.Category;
import ti.dvaja.persistence.Post;
import ti.dvaja.repositories.CategoryRepository;
import ti.dvaja.repositories.PostRepository;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by drichtar on 6/12/17.
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private PostRepository postRepository;


    @GetMapping("/")
    public String list(Model model) {
        List<Category> categories = this.categoryRepository.findAll();

        categories = categories.stream().sorted(Comparator.comparingInt(Category::getId)).collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("view", "admin/category/list");

        return "base-layout";
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("view", "admin/category/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel) {
        if(StringUtils.isEmpty(categoryBindingModel.getName())) {
            return "redirect:/admin/category/create";
        }

        Category category = new Category(categoryBindingModel.getName());
        this.categoryRepository.save(category);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.categoryRepository.existsById(id)) {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.getOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(CategoryBindingModel categoryBindingModel, @PathVariable Integer id) {
        if (!this.categoryRepository.existsById(id)) {
            return "redirect:/admin/categories/";
        }

        Category category = this.categoryRepository.getOne(id);

        category.setName(categoryBindingModel.getName());
        this.categoryRepository.save(category);

        return "redirect:/admin/categories/";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.categoryRepository.existsById(id)) {
            return "redirect:/admin/categories/";
        }

        Category category = categoryRepository.getOne(id);

        model.addAttribute("category", category);
        model.addAttribute("view", "admin/category/delete");

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(CategoryBindingModel categoryBindingModel, @PathVariable Integer id) {
        if (!this.categoryRepository.existsById(id)) {
            return "redirect:/admin/categories";
        }

        Category category = categoryRepository.getOne(id);

        for (Post post : category.getPosts()) {
            this.postRepository.delete(post);
        }

        this.categoryRepository.delete(category);

        return "redirect:/admin/categories/";
    }


}
