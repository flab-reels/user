package com.flabreels.user.dto.following;
import com.flabreels.user.domain.entity.User;
import lombok.Getter;

@Getter
public class UserFollowingListResponseDto {
    private String userId;
    private String followingId;
    private String timestamp;
    private String username;
    private String followingUsername;

    public UserFollowingListResponseDto(User entity){
            this.userId = entity.getUserId();
            this.followingId = entity.getFollowingId();
            this.timestamp = entity.getTimestamp();
            this.followingUsername = entity.getFollowingUsername();
            this.username = entity.getUsername();

    }

}
