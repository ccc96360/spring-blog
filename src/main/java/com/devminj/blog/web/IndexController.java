package com.devminj.blog.web;

import com.devminj.blog.config.auth.dto.SessionUser;
import com.devminj.blog.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
        }
        return "contents/index";
    }

    @GetMapping("/about")
    public String about(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
        }
        return "contents/about";
    }
    @GetMapping("/post")
    public String post(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
        }
        return "contents/post";
    }
    @GetMapping("/contact")
    public String contact(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
        }
        return "contents/contact";
    }
    @GetMapping("/test")
    public String test(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
        }
        return "contents/test";
    }
    @GetMapping("/post_write")
    public String postWrite(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
        }
        return "contents/post_write";
    }
}
