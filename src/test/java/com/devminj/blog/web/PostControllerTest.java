package com.devminj.blog.web;

import com.devminj.blog.domain.BaseTime;
import com.devminj.blog.domain.posts.Post;
import com.devminj.blog.domain.posts.PostsRepository;
import com.devminj.blog.domain.posts.Tag;
import com.devminj.blog.domain.posts.TagRepository;
import com.devminj.blog.service.post.dto.PostResponseDto;
import com.devminj.blog.service.post.dto.PostSaveRequestDto;
import com.devminj.blog.service.post.dto.PostUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private WebApplicationContext context;

    private  MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .build();
    }
    @AfterEach
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    //@WithAnonymousUser
    public void Post_등록() throws Exception{
        //given
       String title = "테스트 게시글 제목 1";
       String content = "테스트 게시글 내용 1";
       String author = "테스트 게시글 작가 1";
       List<String> tags = Arrays.asList("A", "B", "C", "D", "E");
       PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                                        .content(content)
                                        .title(title)
                                        .author(author)
                                        .tags(tags)
                                        .build();

       String url = "http://localhost:" + port + "/api/v1/admin/posts";
       // when
       mvc.perform(post(url)
               .contentType(MediaType.APPLICATION_JSON)
               .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk());
        //then

       List<Post> all = postsRepository.findAll();
       Post post = all.get(0);

       assertThat(post.getTitle()).isEqualTo(title);
       assertThat(post.getContent()).isEqualTo(content);
       assertThat(post.getAuthor()).isEqualTo(author);
       for(Tag tag : post.getTags()){
           assertThat(tags.contains(tag.getName())).isEqualTo(true);
       }

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void Post_수정() throws Exception{
        //given
        String title = "테스트 게시글 제목 1";
        String content = "테스트 게시글 내용 1";
        String author = "테스트 게시글 작가 1";
        List<String> tags = Arrays.asList("A","B","C");

        Post savePost = postsRepository.save(
                Post.builder()
                        .title(title)
                        .author(author)
                        .content(content)
                        .tags(tags)
                        .build()
        );
        title = "테스트 게시글 제목 수정됨!! 1";
        content = "테스트 게시글 내용 수정됨!! 1";
        tags  = Arrays.asList("A","B", "C","D","E");

        PostUpdateRequestDto requestDto =
                PostUpdateRequestDto.builder()
                        .content(content)
                        .title(title)
                        .tags(tags)
                        .build();

        String url = "http://localhost:" + port + "/api/v1/admin/posts/" + savePost.getId();
        //when

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
            .andExpect(status().isOk());

        //then


        List<Post> all = postsRepository.findAll();

        Post post = all.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
        for(Tag tag : post.getTags()){
            assertThat(tags.contains(tag.getName())).isEqualTo(true);
        }
        System.out.println(post.getCreateDate() + " " + post.getModifiedDate());

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void Post_조회() throws Exception{
        //given
        List<String> tags = Arrays.asList("A", "B", "C", "D", "E");
        Post savePost1 = postsRepository.save(
                Post.builder()
                        .title("게시글 1")
                        .content("게시글 1")
                        .author("작성자 1")
                        .tags(tags)
                        .build()
        );

        String url = "http://localhost:" + port + "/api/v1/posts/" + savePost1.getId();


        PostResponseDto expected = new PostResponseDto(savePost1);

        //when

//        ResponseEntity<PostResponseDto> responseEntity = restTemplate.getForEntity(url, PostResponseDto.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)))
            .andDo(print());
        //then

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void Post_삭제() throws  Exception{
        //given
        List<String> tags = Arrays.asList("A","B","C","D");
        Post savePost1 = postsRepository.save(
                Post.builder()
                        .title("게시글 1")
                        .content("게시글 1")
                        .author("작성자 1")
                        .tags(tags)
                        .build()
        );

        String url = "http://localhost:" + port + "/api/v1/admin/posts/" + savePost1.getId();
        //when
//        restTemplate.delete(url);
        mvc.perform(delete(url))
                .andExpect(status().isOk());
        //then
        List<Post> allPosts = postsRepository.findAll();
        List<Tag> allTags = tagRepository.findAll();
        assertThat(allPosts.isEmpty()).isEqualTo(true);
        assertThat(allTags.isEmpty()).isEqualTo(true);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        List<String> tags = Arrays.asList("A","B");
        String now = LocalDateTime.of(2021,06,01,0,0).format(DateTimeFormatter.ofPattern(BaseTime.DATE_FORMAT));
        postsRepository.save(
                Post.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .tags(tags)
                        .build());
        //when
        List<Post> posts = postsRepository.findAll();
        //then
        Post post = posts.get(0);
        System.out.println(">>>>>>>>> createDate = " + post.getCreateDate() + ", modifiedDate = " + post.getModifiedDate());
        assertThat(post.getCreateDate()).isGreaterThan(now);
        assertThat(post.getModifiedDate()).isGreaterThan(now);
    }
}
