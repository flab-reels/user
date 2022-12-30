package com.flabreels.user.dto.information;

import com.flabreels.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInformationResponseDto {
    private String userId;
    private String username;
    private String userIntroduce;
    private String userProfile;


    public UserInformationResponseDto(User entity) {
        this.userId = entity.getUserId();
        this.username = entity.getUsername();
        this.userIntroduce = entity.getUserIntroduce();
        this.userProfile = entity.getUserProfile();
    }
}
