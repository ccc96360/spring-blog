package com.devminj.blog.service.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private List<String> tags;

    @Builder
    public PostUpdateRequestDto(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
}
