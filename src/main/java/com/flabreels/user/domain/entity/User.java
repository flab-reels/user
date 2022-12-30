package com.flabreels.user.domain.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/**
 * DynamoDB 사용법
 *
 * - 1. Cdk나 Console을 통해 Table 이름, PartitionKey를 설정한다.
 * - 2. Entity를 설정할때 Table 이름, PartitionKey는 대소문자를 반드시 일치 시킨다. (다른 Column은 NoSQL이기 때문에 구성을 해도 상관없다.)
 * - 3. Repository의 Crud는 DynamoDbEnhancedClient interface를 이용해서 사용하면 JPA처럼 사용 할 수 있다.
 */

@DynamoDbBean
@NoArgsConstructor
public class User {

    private String userId;
    private String timestamp;
    private String followingId;
    private String userProfile;
    private String userIntroduce;
    private String username;
    private String followingUsername;

    @Builder
    public User(String userId, String timestamp, String followingId, String userProfile, String userIntroduce, String username, String followingUsername) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.followingId = followingId;
        this.userProfile = userProfile;
        this.userIntroduce = userIntroduce;
        this.username = username;
        this.followingUsername = followingUsername;
    }









    // PartitionKey의 값은 대소문자 다 일치 시켜야함
    @DynamoDbPartitionKey()
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbSortKey
    @DynamoDbSecondarySortKey(indexNames = "followingId")
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("followingId")
    @DynamoDbSecondaryPartitionKey(indexNames = "followingId")
    public String getFollowingId() {
        return followingId;
    }
    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    @DynamoDbAttribute("followingUsername")
    public String getFollowingUsername() {
        return followingUsername;
    }
    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }

    @DynamoDbAttribute("userProfile")
    public String getUserProfile() {
        return userProfile;
    }
    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    @DynamoDbAttribute("userIntroduce")
    public String getUserIntroduce() {
        return userIntroduce;
    }
    public void setUserIntroduce(String userIntroduce) {
        this.userIntroduce = userIntroduce;
    }

    @DynamoDbAttribute("username")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
