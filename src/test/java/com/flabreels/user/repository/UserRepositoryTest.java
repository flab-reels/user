package com.flabreels.user.repository;

import com.flabreels.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    private DynamoDbClient dynamoDbClient;
    private DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<User> user;


    //설정
    @BeforeEach
    public void setUp(){
        AwsCredentials awsCredentials = new AwsCredentials() {
            @Override
            public String accessKeyId() {return "foo";}
            @Override
            public String secretAccessKey() {return "bar";}
        };
        AwsCredentialsProvider awsCredentialsProvider = new AwsCredentialsProvider() {
            @Override
            public AwsCredentials resolveCredentials() {
                return awsCredentials;
            }
        };
        /**
         * dynamoDbClient 를 먼저 빌드후에
         * enhancedClient 를 설정해줘야한다. (구버전 DBMapper과 같은 기능)
         * 이후 DynamoDbTable 객체를 가져와 user 클래스를 매핑한 @Bean 을 ArrayList 화 시킨다.
         */
        dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();

        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();

        user = enhancedClient.table("user", TableSchema.fromBean(User.class));
    }

    @DisplayName("테이블 생성")
    @Test
    @Disabled
    public void createTable(){
        user.createTable(builder -> builder
                .provisionedThroughput(b -> b
                        .readCapacityUnits(1L)
                        .writeCapacityUnits(1L)
                )
                .build()
        );
        then(user.tableName()).isEqualTo("user");
    }

    @DisplayName("항목 생성")
    @Test
    public void saveItem(){
        // Given

        User item = User.builder()
                .userId("1")
                .timestamp(LOCAL_DATE_TIME.toString())
                .followingId("2")
                .build();
        // When
        user.putItem(item);
        // Then
        then(user.getItem(item).getUserId()).isEqualTo("1");

    }

    @DisplayName("항목 가져오기")
    @Test
    public void getItem(){
        // Given
        // When
        Key key = Key.builder().partitionValue("1").sortValue("1")
                .build();
        // Then
        User resultUser = user.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
        then(resultUser.getUserId()).isEqualTo("1");

    }


    @DisplayName("항목 수정하기")
    @Test
    public void modifyItem(){
        // Given
        Key key = Key.builder().partitionValue("1").sortValue("1")
                .build();

        User modifiedUser = user.getItem(r->r.key(key));
        modifiedUser.setUserId("4");
        user.updateItem(modifiedUser);
        // Then
        User resultUser = user.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
        then(resultUser.getUserId()).isEqualTo("4");
    }

    @DisplayName("항목 삭제")
    @Test
    public void deleteItem(){

        Key key = Key.builder()
                .partitionValue("1")
                .sortValue("1")
                .build();

        user.deleteItem(key);
        //then
        User resultUser = user.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
        then(resultUser).isNull();

    }

}