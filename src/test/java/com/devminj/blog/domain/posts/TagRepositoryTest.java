package com.devminj.blog.domain.posts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TagRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @Autowired
    TagRepository tagRepository;

    @Test
    public void 태그별_개수_세기(){
        //given
        String title = "title";
        String author = "author";
        String content = "content";

        List<String> tags1 = Arrays.asList("A", "B", "C", "D", "E");
        List<String> tags2 = Arrays.asList("A", "B", "E", "F", "G");
        HashMap<String, Long> resultSet = new HashMap<String, Long>();
        resultSet.put("A", 2L);
        resultSet.put("B", 2L);
        resultSet.put("C", 1L);
        resultSet.put("D", 1L);
        resultSet.put("E", 2L);
        resultSet.put("F", 1L);
        resultSet.put("G", 1L);

        Post post1 = Post.builder()
                .title(title)
                .author(author)
                .content(content)
                .tags(tags1)
                .build();
        Post post2 = Post.builder()
                .title(title)
                .author(author)
                .content(content)
                .tags(tags2)
                .build();
        postsRepository.save(post1);
        postsRepository.save(post2);
        // when
        List<String> allNames = tagRepository.findAllDistinctName();
        // then
        for(String name: allNames){
            Long cnt = tagRepository.NumberOfName(name);
            assertThat(cnt).isEqualTo(resultSet.get(name));
        }
    }
}
