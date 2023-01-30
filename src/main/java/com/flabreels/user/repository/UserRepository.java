package com.flabreels.user.repository;


import com.flabreels.user.domain.entity.User;
import com.flabreels.user.dto.following.UserAddFollowingRequestDto;
import com.flabreels.user.dto.following.UserFollowedListResponseDto;
import com.flabreels.user.dto.following.UserFollowingListResponseDto;
import com.flabreels.user.dto.information.UserInformationRequestDto;
import com.flabreels.user.dto.information.UserInformationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserRepository {

    /**
     * DynamoDB 데이터를 처리하는 중에 데이터를 어떻게 처리할 것인가에 대한 고민을 함
     * For - Loop 문을 사용할까 고민을 했었는데 Stream이 다 연산 기준으로는 연산속도가 큰차이가 없으므로 Stream을 사용하기로 함
     * 참고영상 - http://www.angelikalanger.com/Conferences/Videos/Conference-Video-GeeCon-2015-Performance-Model-of-Streams-in-Java-8-Angelika-Langer.html
     */
    public static final String TABLE_NAME = "User";
    private final DynamoDbTable<User> dynamoDbTable;
    private final DynamoDbIndex<User> followingIdIndex;

    public UserRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient){
        this.dynamoDbTable = dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(User.class));
        this.followingIdIndex = dynamoDbTable.index("followingId");
    }

    public void addUserInformation(UserInformationRequestDto userInformationRequestDto) throws DynamoDbException{
        dynamoDbTable.putItem(userInformationRequestDto.toEntity());
    }
    public void updateUserInformation(UserInformationRequestDto userInformationRequestDto) throws DynamoDbException{
        dynamoDbTable.updateItem(userInformationRequestDto.toEntity());
    }


    public UserInformationResponseDto findUserInformationByUserId(UserQuery query) throws DynamoDbException{
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(query.getLastReadUserId())
                        .build()
                ))
                .filterExpression(query.getUserInformationFilter())
                .limit(query.getLimit())
                .scanIndexForward(true)
                .build();

        return new UserInformationResponseDto(dynamoDbTable.query(queryEnhancedRequest).stream().flatMap(d -> d.items().stream()).collect(Collectors.toList()).get(0));
    }


    /**
     * @param userAddFollowingRequestDto
     * @return if FollowingId is already existed return FALSE or not return TRUE
     * @throws DynamoDbException
     */
    public boolean addFollowing(UserAddFollowingRequestDto userAddFollowingRequestDto) throws DynamoDbException{
        if (findFollowingByUserId(UserQuery.builder()
                .lastReadUserId(userAddFollowingRequestDto.getUserId())
                .followingId(userAddFollowingRequestDto.getFollowingId())
                .build())
                .stream().noneMatch(d -> d.getFollowingId().equals(userAddFollowingRequestDto.getFollowingId()))){
            dynamoDbTable.putItem(userAddFollowingRequestDto.toEntity());
            return true;
        }
        return false;

    }

    public void removeFollowing(UserAddFollowingRequestDto userAddFollowingRequestDto)throws DynamoDbException {
        dynamoDbTable.deleteItem(userAddFollowingRequestDto.toEntity());
    }

    public List<UserFollowedListResponseDto> findFollowedByFollowingId(UserQuery query) throws DynamoDbException{
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(query.getFollowingId())
                        .build()
                ))
                .scanIndexForward(false)
                .build();

        return followingIdIndex.query(queryEnhancedRequest).stream().flatMap(d -> d.items().stream())
                .map(UserFollowedListResponseDto::new)
                .filter(d -> d.getFollowingId() != null)
                .collect(Collectors.toList());
    }

    public List<UserFollowingListResponseDto> findFollowingByUserId(UserQuery query) throws DynamoDbException{
        QueryEnhancedRequest queryEnhancedRequest = QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(query.getLastReadUserId())
                        .build()
                ))
                .scanIndexForward(false)
                .build();
        return dynamoDbTable.query(queryEnhancedRequest).stream().flatMap(d -> d.items().stream())
                .map(UserFollowingListResponseDto::new)
                .filter(d -> d.getFollowingId() != null)
                .collect(Collectors.toList());
    }




}
