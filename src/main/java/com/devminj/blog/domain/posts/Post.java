package com.devminj.blog.domain.posts;

import com.devminj.blog.domain.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Tag> tags;

    @Builder
    public Post(String title, String content, String author, List<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = stringTagsToEntity(tags);
    }

    public void update(String title, String content, List<String> tags){
        this.title = title;
        this.content = content;
        this.tags = this.stringTagsToEntity(tags);
    }
    public List<Tag> stringTagsToEntity(List<String> tags){
        List<Tag> ret = new ArrayList<Tag>();
        for(String s : tags){
            ret.add(new Tag(this, s));
        }
        return ret;
    }
    public List<String> tagsToStringTags(){
        List<String> ret = new ArrayList<String>();
        for(Tag tag : this.tags){
            ret.add(tag.getName());
        }
        return ret;
    }
    public void addTag(Tag tag){
        if(tags == null){
            tags = new ArrayList<Tag>();
        }
        tags.add(tag);
    }
}
