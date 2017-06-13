package ti.dvaja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ti.dvaja.persistence.Tag;
import ti.dvaja.repositories.TagRepository;

import javax.inject.Inject;

/**
 * Created by drichtar on 6/12/17.
 */
@Controller
public class TagController {
    @Inject
    private TagRepository tagRepository;

    @GetMapping("/tag/{name}")
    public String postsWithTag(Model model, @PathVariable String name) {
        Tag tag = this.tagRepository.findByName(name);

        if (tag == null) {
            return "redirect:/";
        }

        model.addAttribute("view", "tags/posts");
        model.addAttribute("tag", tag);

        return "base-layout";
    }
}
