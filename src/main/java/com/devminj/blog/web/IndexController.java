package com.devminj.blog.web;

import com.devminj.blog.config.auth.LoginUser;
import com.devminj.blog.config.auth.dto.SessionUser;
import com.devminj.blog.domain.posts.Post;
import com.devminj.blog.service.post.PostService;
import com.devminj.blog.service.post.dto.PostListResponseDto;
import com.devminj.blog.service.post.dto.PostResponseDto;
import com.devminj.blog.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final TagService tagService;

    @GetMapping("/")
    public String home(Model model, @LoginUser SessionUser user, @PageableDefault(size=9, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        commonSetUp(model, user);

        Page<PostListResponseDto> allPosts = postService.findAllWithPage(pageable);
        postListSetUp(model, allPosts);

        return "contents/index";
    }

    @GetMapping("/post/{id}")
    public String post(Model model, @LoginUser SessionUser user, @PathVariable Long id, @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        commonSetUp(model, user);

        PostResponseDto postResponseDto = postService.findById(id);
        model.addAttribute("post", postResponseDto);

        Page<PostListResponseDto> allPosts = postService.findAllWithPage(pageable);
        int totalPageNum = allPosts.getTotalPages();
        totalPageNum = (totalPageNum > 0) ? totalPageNum : 1;
        model.addAttribute("other_posts", allPosts);
        model.addAttribute("total_page_num", totalPageNum);;

        return "contents/post";
    }

    @GetMapping("/post/write")
    public String postWrite(Model model, @LoginUser SessionUser user){
        commonSetUp(model, user);
        return "contents/post_write";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(Model model, @LoginUser SessionUser user, @PathVariable Long id){
        commonSetUp(model, user);

        PostResponseDto postResponseDto = postService.findById(id);
        model.addAttribute("post", postResponseDto);

        return "contents/post_update";
    }

    @GetMapping("/tag/{name}")
    public String tagPosts(Model model, @LoginUser SessionUser user, @PathVariable String name, @PageableDefault(size=9, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        commonSetUp(model, user);

        Page<PostListResponseDto> allPosts = postService.findByTagName(name, pageable);
        postListSetUp(model, allPosts);

        return "contents/index";
    }
    @GetMapping("/search/{keyword}")
    public String search(Model model, @LoginUser SessionUser user, @PathVariable String keyword, @PageableDefault(size=9, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        commonSetUp(model, user);

        Page<PostListResponseDto> allPosts = postService.findByKeyWord(keyword, pageable);
        postListSetUp(model, allPosts);

        return "contents/index";
    }

    private void commonSetUp(Model model, SessionUser user){
        model.addAttribute("role","ROLE_GUEST");
        if(user != null){
            if(user.getPlatform().equals("github")) model.addAttribute("userName", user.getSiteId());
            model.addAttribute("role", user.getRole());
        }
    }
    private void postListSetUp(Model model, Page<PostListResponseDto> allPosts){
        int totalPageNum = allPosts.getTotalPages();
        totalPageNum = (totalPageNum > 0) ? totalPageNum : 1;

        model.addAttribute("posts", allPosts);
        model.addAttribute("total_page_num", totalPageNum);
        model.addAttribute("is_last_page", allPosts.isLast());

        model.addAttribute("tags", tagService.findNameAndCount());
    }
}
