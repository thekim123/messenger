package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@ToString(exclude = {"participant", "roomOwner"})
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String roomHash;
    private String roomName;

    @ManyToOne
    private User participant;

    @OneToOne
    private User roomOwner;

    private boolean isPrivate;
    private boolean isBlocked;

    public void updateRoomName(String newRoomName) {
        this.roomName = newRoomName;
    }

    public void blockUser() {
        this.isBlocked = true;
    }

}
