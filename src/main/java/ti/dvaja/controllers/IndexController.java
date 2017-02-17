package ti.dvaja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by drichtar on 2/16/17.
 */
@Controller
public class IndexController {
    @GetMapping("/")
    String index(){
        return "index";
    }
}
