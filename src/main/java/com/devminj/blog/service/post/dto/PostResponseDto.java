package com.devminj.blog.service.post.dto;

import com.devminj.blog.domain.posts.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostResponseDto{
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createTime;
    private String modifiedTime;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createTime = entity.getCreateDate();
        this.modifiedTime = entity.getModifiedDate();
    }

}
