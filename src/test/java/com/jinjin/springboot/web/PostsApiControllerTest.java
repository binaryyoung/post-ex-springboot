package com.jinjin.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinjin.springboot.domain.Posts.Posts;
import com.jinjin.springboot.domain.Posts.PostsRepository;
import com.jinjin.springboot.web.dto.PostsResponseDto;
import com.jinjin.springboot.web.dto.PostsSaveRequestDto;
import com.jinjin.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// JPA를사용하기위해서MVCtest말고스프링부트테스트로함
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void clean() {
        postsRepository.deleteAll();
    }

    @Test
    public void post_등록하다() {
        // given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto =PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void post_수정() {
        // given
        Posts posts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        Long id = posts.getId();
        String expectTitle = "new Title";
        String expectContent = "new Content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectTitle)
                .content(expectContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        // when
        HttpEntity<PostsUpdateRequestDto> httpEntity = new HttpEntity<>(requestDto);
        ResponseEntity<Long> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        assertThat(responseEntity.getBody()).isEqualTo(id);
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(expectTitle);
        assertThat(postsList.get(0).getContent()).isEqualTo(expectContent);
    }

    @Test
    public void post_조회() throws IOException {
        // given
        Posts posts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long id = posts.getId();

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        // when
        ResponseEntity<String> responseEntity
                = restTemplate.getForEntity(url, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("title");
    }
}
