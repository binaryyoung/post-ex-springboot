package com.jinjin.springboot.config.auth.dto;

import com.jinjin.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
/*
User클래스를_직렬화하지_않는이유
-User클래스는_엔티티로_@OneToMany,@ManyToMany등_자식_엔티티를_갖고_있다면_직렬화
대상에_자식들까지_포함되어_성능이슈나_부수효과가_발생할_수_있다.
-그래서_따로_직렬화기능이_있는_세션_Dto를_하나_추가하는_것이_좋다.
 */
