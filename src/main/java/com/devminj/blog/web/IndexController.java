package com.devminj.blog.web;

import com.devminj.blog.config.auth.LoginUser;
import com.devminj.blog.config.auth.dto.SessionUser;
import com.devminj.blog.service.post.PostService;
import com.devminj.blog.service.post.dto.PostResponseDto;
import com.devminj.blog.service.tag.TagService;
import com.devminj.blog.service.tag.dto.TagCountByNameResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
    private final TagService tagService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postService.findAllDesc());
        for(TagCountByNameResponseDto tag : tagService.findNameAndCount()){
            System.out.println(tag.getName() + " " + tag.getCount());
        }
        model.addAttribute("tags", tagService.findNameAndCount());
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/index";
    }

    @GetMapping("/post/{id}")
    public String post(Model model, @LoginUser SessionUser user, @PathVariable Long id){
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
    public String contact(Model model, @LoginUser SessionUser user){
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/contact";
    }

    @GetMapping("/post/write")
    public String postWrite(Model model, @LoginUser SessionUser user){
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/post_write";
    }
    @GetMapping("/post/update/{id}")
    public String postUpdate(Model model, @LoginUser SessionUser user, @PathVariable Long id){
        PostResponseDto postResponseDto = postService.findById(id);

        model.addAttribute("post", postResponseDto);
        model.addAttribute("role", "ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
        return "contents/post_update";
    }
}
