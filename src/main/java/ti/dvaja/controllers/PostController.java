package ti.dvaja.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ti.dvaja.bindingModel.PostBindingModel;
import ti.dvaja.persistence.Category;
import ti.dvaja.persistence.Post;
import ti.dvaja.persistence.Tag;
import ti.dvaja.persistence.User;
import ti.dvaja.repositories.CategoryRepository;
import ti.dvaja.repositories.PostRepository;
import ti.dvaja.repositories.TagRepository;
import ti.dvaja.repositories.UserRepository;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Denko on 25/03/2017.
 */
@Controller
public class PostController {
    @Inject
    private PostRepository postRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private CategoryRepository categoryRepository;
    @Inject
    private TagRepository tagRepository;

    @GetMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        List<Category> categories = this.categoryRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("view", "post/create");

        return "base-layout";
    }

    @PostMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(PostBindingModel postBindingModel) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Category category = this.categoryRepository.getOne(postBindingModel.getCategoryId());
        HashSet<Tag> tags = this.findTagsFromString(postBindingModel.getTagString());

        Set<User> authors = new HashSet<>();
        authors.add(userEntity);

        Post postEntity = new Post(
                postBindingModel.getTitle(),
                postBindingModel.getContent(),
                authors,
                category,
                tags
        );

        this.postRepository.save(postEntity);

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.postRepository.existsById(id)) {
            return "redirect:/";
        }

        if(!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", user);
        }

        Post post = this.postRepository.getOne(id);
        Iterable<User> authors = this.postRepository.getOne(id).getAuthors();

        model.addAttribute("post", post);
        model.addAttribute("view", "post/details");

        model.addAttribute("authors", authors);

        return "base-layout";
    }

    @GetMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.postRepository.existsById(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.getOne(id);

        if(!isUserAuthorOrAdmin(post)) {
            return "redirect:/article/" + id;
        }

        List<Category> categories = this.categoryRepository.findAll();
        String tagString = post.getTags().stream().map(Tag::getName).collect(Collectors.joining(", "));

        model.addAttribute("post", post);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagString);
        model.addAttribute("view", "post/edit");

        return "base-layout";
    }

    @PostMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(PostBindingModel postBindingModel, @PathVariable Integer id) {
        if (!this.postRepository.existsById(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.getOne(id);

        if(!isUserAuthorOrAdmin(post)) {
            return "redirect:/article/" + id;
        }

        Category category = this.categoryRepository.getOne(postBindingModel.getCategoryId());
        HashSet<Tag> tags = this.findTagsFromString(postBindingModel.getTagString());

        post.setContent(postBindingModel.getContent());
        post.setTitle(postBindingModel.getTitle());
        post.setCategory(category);
        post.setTags(tags);

        this.postRepository.save(post);

        return "redirect:/post/" + post.getId();
    }

    @GetMapping("/post/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.postRepository.existsById(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.getOne(id);

        if(!isUserAuthorOrAdmin(post)) {
            return "redirect:/article/" + id;
        }

        model.addAttribute("post", post);
        model.addAttribute("view", "post/delete");

        return "base-layout";
    }

    @PostMapping("/post/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.postRepository.existsById(id)) {
            return "redirect:/";
        }

        Post post = this.postRepository.getOne(id);

        if(!isUserAuthorOrAdmin(post)) {
            return "redirect:/article/" + id;
        }

        this.postRepository.delete(post);

        return "redirect:/";
    }

    private HashSet<Tag> findTagsFromString(String tagString) {
        HashSet<Tag> tags = new HashSet<>();

        String[] tagNames = tagString.split(",\\s*");

        for (String tagName : tagNames) {
            Tag currentTag = this.tagRepository.findByName(tagName);

            if (currentTag == null) {
                currentTag = new Tag(tagName);
                this.tagRepository.save(currentTag);
            }

            tags.add(currentTag);
        }

        return tags;
    }

    private boolean isUserAuthorOrAdmin(Post post) {
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(userDetail.getUsername());

        return user.isAdmin() || user.isAuthor(post);
    }

}
