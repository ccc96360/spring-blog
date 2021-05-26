package com.devminj.blog.web;

import com.devminj.blog.domain.posts.Post;
import com.devminj.blog.domain.posts.PostsRepository;
import com.devminj.blog.service.post.dto.PostSaveRequestDto;
import com.devminj.blog.service.post.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Post_등록() throws Exception{
        //given
       String title = "테스트 게시글 제목 1";
       String content = "테스트 게시글 내용 1";
       String author = "테스트 게시글 작가 1";

       PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                                        .content(content)
                                        .title(title)
                                        .author(author)
                                        .build();

       String url = "http://localhost:" + port + "/api/v1/posts";
       // when
       ResponseEntity<Long> responseEntity =  restTemplate.postForEntity(url, requestDto, Long.class);
        //then

       assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
       System.out.println(responseEntity.getBody());
       assertThat(responseEntity.getBody()).isGreaterThan(0L);

       Post post = postsRepository.findById(responseEntity.getBody()).orElseThrow();

       assertThat(post.getTitle()).isEqualTo(title);
       assertThat(post.getContent()).isEqualTo(content);
       assertThat(post.getAuthor()).isEqualTo(author);

    }
    @Test
    public void Post_수정() throws Exception{
        //given
        String title = "테스트 게시글 제목 1";
        String content = "테스트 게시글 내용 1";
        String author = "테스트 게시글 작가 1";

        Post savePost = postsRepository.save(
                Post.builder()
                        .title(title)
                        .author(author)
                        .content(content)
                        .build()
        );
        postsRepository.save(savePost);

        title = "테스트 게시글 제목 수정됨!! 1";
        content = "테스트 게시글 내용 수정됨!! 1";

        PostUpdateRequestDto requestDto =
                PostUpdateRequestDto.builder()
                        .contents(content)
                        .title(title)
                        .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + savePost.getId();
        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        //when

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> all = postsRepository.findAll();

        Post post = all.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);


    }
}
