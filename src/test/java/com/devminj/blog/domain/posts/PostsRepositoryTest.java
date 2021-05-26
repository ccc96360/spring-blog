package com.devminj.blog.domain.posts;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @Test
    public void 게시글_저장하고_불러오기(){
        //given
        String title = "테스트 게시글 1";
        String content = "테스트 게시글 내용 입니다.";
        String author = "테스트 게시글 작성자 입니다.";

        Post post = Post.builder()
                .content(content)
                .title(title)
                .author(author)
                .build();

        postsRepository.save(post);
        //when
        List<Post> posts = postsRepository.findAll();
        //then
        Post firstPost = posts.get(0);
        assertThat(firstPost.getTitle()).isEqualTo(title);
        assertThat(firstPost.getContent()).isEqualTo(content);
        assertThat(firstPost.getAuthor()).isEqualTo(author);
    }
}
