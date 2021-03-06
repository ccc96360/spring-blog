package com.devminj.blog.domain.posts;

import com.devminj.blog.service.tag.dto.TagCountByNameResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @BeforeEach
    public void setup(){
        postsRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void 태그가_같은_모든_게시글_찾기(){
        //given
        List<String> tags1 = Arrays.asList("A","B","C");
        List<String> tags2 = Arrays.asList("A","C");
        List<String> tags3 = Arrays.asList("C");

        postsRepository.save(Post.builder()
                .title("1번")
                .author("1")
                .content("1")
                .tags(tags1)
                .build());
        postsRepository.save(Post.builder()
                .title("2번")
                .author("2")
                .content("2")
                .tags(tags2)
                .build());
        postsRepository.save(Post.builder()
                .title("3번")
                .author("3")
                .content("3")
                .tags(tags3)
                .build());
        //when
        Pageable pageable = PageRequest.of(0, 9, Sort.Direction.DESC, "id");
        Page<Post> posts1 = tagRepository.findAllPostsByTagName("A", pageable);
        Page<Post> posts2 = tagRepository.findAllPostsByTagName("B", pageable);
        Page<Post> posts3 = tagRepository.findAllPostsByTagName("C", pageable);
        List<Page<Post>> allPosts = Arrays.asList(posts1, posts2, posts3);
        //then
        for(Page<Post> posts: allPosts) {
            for (Post post : posts) {
                System.out.println(post.getId() + " " + post.getTitle() + " " + post.getAuthor() + " " + post.getContent());
            }
        }
        assertThat(posts1.getTotalElements()).isEqualTo(2L);
        assertThat(posts2.getTotalElements()).isEqualTo(1L);
        assertThat(posts3.getTotalElements()).isEqualTo(3L);
    }

    @Test
    public void 모든_태그별_개수(){
        //given
        String title = "title1";
        String author = "author";
        String content = "content";

        List<String> tags1 = Arrays.asList("A","B","C","D");
        List<String> tags2 = Arrays.asList("A","D");

        HashMap<String, Long> resultSet = new HashMap<>();
        resultSet.put("A", 2L);
        resultSet.put("B", 1L);
        resultSet.put("C", 1L);
        resultSet.put("D", 2L);

        postsRepository.save(Post.builder()
                .tags(tags1)
                .author(author)
                .content(content)
                .title(title)
                .build());
        postsRepository.save(Post.builder()
                .tags(tags2)
                .author(author)
                .content(content)
                .title(title)
                .build());
        //when;
        List<TagCountByNameResponseDto> result = tagRepository.countGroupByName();
        //then
        for(TagCountByNameResponseDto tag : result){
            String name = tag.getName();
            Long cnt = tag.getCount();
            System.out.println("==" + name + " " + cnt + " " + resultSet.get(name));
            assertThat(cnt).isEqualTo(resultSet.get(name));
        }
    }

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
            System.out.println(name + " " + cnt);
            assertThat(cnt).isEqualTo(resultSet.get(name));
        }
    }
}
