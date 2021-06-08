package com.devminj.blog.service.post.dto;

import com.devminj.blog.domain.posts.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private List<String> tags;
    private String content;
    private String author;

    @Builder
    public PostSaveRequestDto(String title, String content, String author, List<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
    }
    public Post toEntity(){
        return Post.builder()
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .tags(this.tags)
                .build();
    }
}
