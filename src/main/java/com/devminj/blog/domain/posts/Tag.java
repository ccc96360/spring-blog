package com.devminj.blog.domain.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="post_id")
    private Post post;

    public Tag(Post post, String name) {
        this.post = post;
        this.name = name;
    }
}
