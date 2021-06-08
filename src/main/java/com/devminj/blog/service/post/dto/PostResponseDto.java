package com.devminj.blog.service.post.dto;

import com.devminj.blog.domain.posts.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto{
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createTime;
    private String modifiedTime;
    private List<String> tags;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createTime = entity.getCreateDate();
        this.modifiedTime = entity.getModifiedDate();
        this.tags = entity.tagsToStringTags();
    }

}
