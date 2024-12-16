package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import com.namusd.messenger.model.domain.FriendStatus;
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

}
