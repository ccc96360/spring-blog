package com.devminj.blog.web;

import com.devminj.blog.service.post.PostService;
import com.devminj.blog.service.post.dto.PostResponseDto;
import com.devminj.blog.service.post.dto.PostSaveRequestDto;
import com.devminj.blog.service.post.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("api/v1/posts/{id}")
    public PostResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping("api/v1/admin/posts")
    public Long save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("api/v1/admin/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto){
        System.out.println(requestDto.getContent() + " " + requestDto.getTitle());
        return postService.update(id, requestDto);

    }

    @DeleteMapping("api/v1/admin/posts/{id}")
    public Long delete(@PathVariable Long id){
        postService.delete(id);
        return id;
    }


}
