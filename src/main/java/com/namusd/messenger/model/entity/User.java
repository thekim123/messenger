package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import com.namusd.messenger.model.domain.ParticipantRole;
import com.namusd.messenger.model.domain.UserRole;
import com.namusd.messenger.model.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private UserRole roles;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_friends", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"), // 현재 User ID
            inverseJoinColumns = @JoinColumn(name = "friend_id") // 친구 User ID
    )
    private Set<User> friends = new HashSet<>();


    public UserDto.Response toDto() {
        return UserDto.Response.builder()
                .id(this.id)
                .username(this.username)
                .roles(this.roles)
                .build();
    }

}
