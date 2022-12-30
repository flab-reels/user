package com.flabreels.user.dto.information;

import com.flabreels.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserInformationRequestDto {
    private String userId;
    private String username;
    private String userIntroduce;
    private String userProfile;

    @Builder
    public UserInformationRequestDto(String userId, String username, String userIntroduce, String userProfile) {
        this.userId = userId;
        this.username = username;
        this.userIntroduce = userIntroduce;
        this.userProfile = userProfile;
    }

    public User toEntity(){
        return User.builder()
                .userId(getUserId())
                .username(getUsername())
                .userIntroduce(getUserIntroduce())
                .userProfile(getUserProfile())
                .timestamp(LocalDateTime.now().toString())
                .build();
    }


}
