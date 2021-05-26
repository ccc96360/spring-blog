package com.devminj.blog.service.post.dto;

import com.devminj.blog.domain.posts.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    public Post toEntity(){
        return Post.builder()
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
