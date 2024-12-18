package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import com.namusd.messenger.model.domain.FriendStatus;
import com.namusd.messenger.model.dto.FriendshipDto;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Friendship extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    // e.g., "PENDING", "ACCEPTED", "REJECTED"
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    public void accept() {
        this.status = FriendStatus.ACCEPTED;
    }
    public void pending() {
        this.status = FriendStatus.PENDING;
    }
    public void reject() {
        this.status = FriendStatus.REJECTED;
    }

    public FriendshipDto.Response toDto() {
        return FriendshipDto.Response.builder()
                .id(id)
                .user(user.toDto())
                .createdDate(getCreatedDate())
                .lastModifiedDate(getLastModifiedDate())
                .friend(friend.toDto())
                .status(status)
                .build();
    }
}
