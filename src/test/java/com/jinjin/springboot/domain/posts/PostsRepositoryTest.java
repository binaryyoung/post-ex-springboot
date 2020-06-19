package com.jinjin.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void clean() {
        postsRepository.deleteAll();
    }

    @Test
    public void posts_save_load() {
        //given
        String title = "title";
        String content = "content";

        //when
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("author")
                .build());

        //then
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_테스트() {
        // given
        LocalDateTime nowTime = LocalDateTime.now();

        // when
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // then
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getCreatedDate()).isAfter(nowTime);
        assertThat(postsList.get(0).getModifiedDate()).isAfter(nowTime);
    }

}
