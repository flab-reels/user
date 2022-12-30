package com.flabreels.user.dto.following;

import com.flabreels.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserAddFollowingRequestDto {
    private String userId;
    private String followingId;
    private String timestamp;

    @Builder
    public UserAddFollowingRequestDto(String userId, String followingId, String timestamp) {
        this.userId = userId;
        this.followingId = followingId;
        this.timestamp = timestamp;

    }

    public User toEntity(){
        return User.builder()
                .userId(getUserId())
                .followingId(getFollowingId())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }



}
