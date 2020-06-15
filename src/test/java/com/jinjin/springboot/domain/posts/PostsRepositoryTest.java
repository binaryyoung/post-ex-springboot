package com.jinjin.springboot.domain.posts;

import com.jinjin.springboot.domain.Posts.Posts;
import com.jinjin.springboot.domain.Posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;

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

}
