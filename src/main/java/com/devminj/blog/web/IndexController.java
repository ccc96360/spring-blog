package com.devminj.blog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }
    @GetMapping("/post")
    public String post(Model model){
        return "post";
    }
    @GetMapping("/contact")
    public String contact(Model model){
        return "contact";
    }
}
