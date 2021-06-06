package com.devminj.blog.web;

import com.devminj.blog.config.auth.dto.SessionUser;
import com.devminj.blog.service.post.PostService;
import com.devminj.blog.service.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("posts", postService.findAllDesc());
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/index";
    }

    @GetMapping("/about")
    public String about(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/about";
    }
    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable Long id){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        PostResponseDto postResponseDto = postService.findById(id);

        model.addAttribute("post", postResponseDto);
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/post";
    }
    @GetMapping("/contact")
    public String contact(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/contact";
    }
    @GetMapping("/test")
    public String test(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/test";
    }
    @GetMapping("/post/write")
    public String postWrite(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/post_write";
    }
}
