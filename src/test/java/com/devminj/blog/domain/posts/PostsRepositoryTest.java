package com.devminj.blog.domain.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @Autowired
    TagRepository tagRepository;

    @BeforeEach
    void setup(){
        postsRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void 게시글_저장하고_불러오기(){
        //given
        String title = "테스트 게시글 1";
        String content = "테스트 게시글 내용 입니다.";
        String author = "테스트 게시글 작성자 입니다.";
        List<String> tags = Arrays.asList("A", "B", "C", "D", "E");
        List<String> tags2 = Arrays.asList("C", "D", "E");

        Post post = Post.builder()
                .content(content)
                .title(title)
                .author(author)
                .tags(tags)
                .build();
        Post post2 = Post.builder()
                .content(content)
                .title(title)
                .author(author)
                .tags(tags2)
                .build();


        postsRepository.save(post);
        postsRepository.save(post2);
        //when
        List<Post> posts = postsRepository.findAll();
        //then
        Post resPost = posts.get(0);
        assertThat(resPost.getTitle()).isEqualTo(title);
        assertThat(resPost.getContent()).isEqualTo(content);
        assertThat(resPost.getAuthor()).isEqualTo(author);
        for(Tag tag: resPost.getTags()){
            System.out.println(tag.getName() + " " + tag.getId() + " " + tag.getPost().getId());
        }
        List<Tag> allTags = tagRepository.findAll();
        for(Tag tag: allTags){
            System.out.println(tag.getName() + " " + tag.getId() + " " + tag.getPost().getId());
        }
    }


}
