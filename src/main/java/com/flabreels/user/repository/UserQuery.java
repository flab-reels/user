package com.flabreels.user.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Slf4j
public class UserQuery {

    private final int limit;
    private final String lastReadUserId;
    private final String timestamp;
    private final String followingId;
    private final String username;

    /**
     *
     * RDS와 다르게 DynamoDB는 페이징이 불가능하기 때문에 마지막으로 읽었던 키를 전달하여 다음데이터를 얻어오는식으로 사용되야한다.
     * 그 값을 사용하기 위해서는 존재하는 PartitionKey를 사용하면 된다. 물론 SortKey로 사용하면 되지만 만약 SortKey가 변동이 심하다면
     * 페이징 기능을 사용할 수 없기때문에 PartitionKey를 사용하면 된다.
     */
    @Builder
    public UserQuery(int limit, String lastReadUserId, String timestamp, String followingId, String username) {
        this.limit = limit;
        this.lastReadUserId = lastReadUserId;
        this.timestamp = timestamp;
        this.followingId = followingId;
        this.username = username;
    }




    public Map<String, AttributeValue> exclusiveStartKey() {
        log.info(lastReadUserId);
        if (Objects.nonNull(lastReadUserId)) {
            Map<String, AttributeValue> exclusiveStartKeyMap = new HashMap<>();
            exclusiveStartKeyMap.put("userId", AttributeValue.builder().s(lastReadUserId).build());
            exclusiveStartKeyMap.put("followingId", AttributeValue.builder().s(followingId).build());
            exclusiveStartKeyMap.put("timestamp", AttributeValue.builder().s(timestamp).build());
            return exclusiveStartKeyMap;
        }
        return null;
    }

    public Expression getUserInformationFilter(){
        Map<String, AttributeValue> expressionValue = new HashMap<>();

        if (Objects.nonNull(username)) {
            expressionValue.put(":username", AttributeValue.builder()
                    .n(String.valueOf(username))
                    .build());
            return Expression.builder()
                    .expression(":username = username")
                    .expressionValues(expressionValue)
                    .build();
        }
        return null;
    }

    public Expression getFilterExpression() {
        Map<String, AttributeValue> expressionValue = new HashMap<>();

//        if (Objects.nonNull(followingId)) {
//            expressionValue.put(":followingId", AttributeValue.builder()
//                    .n(String.valueOf(followingId))
//                    .build());
            return Expression.builder()
                    .expression("contains(#followingId, :followingId)")
                    .expressionValues(expressionValue)
                    .build();
//        }
//        return null;
    }
}
