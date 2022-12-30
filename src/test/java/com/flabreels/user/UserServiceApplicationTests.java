package com.flabreels.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_TIME;

@SpringBootTest
class UserServiceApplicationTests {

    private DynamoDbClient dynamoDbClient;
    Map<String, AttributeValue> item = new HashMap<>();

    @BeforeEach
    public void setUp(){
        AwsCredentials awsCredentials = new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return "key1";
            }
            @Override
            public String secretAccessKey() {
                return "key2";
            }
        };
        AwsCredentialsProvider awsCredentialsProvider = new AwsCredentialsProvider() {
            @Override
            public AwsCredentials resolveCredentials() {
                return awsCredentials;
            }
        };


        dynamoDbClient = DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();

        item.put("feed_id", AttributeValue.builder().s("1").build());
        item.put("content", AttributeValue.builder().s("핵노잼 ㅠㅠ ").build());
        item.put("feed_video_url", AttributeValue.builder().s("http://").build());
        item.put("feed_thumbnail_url", AttributeValue.builder().s("http://").build());
        item.put("created_time", AttributeValue.builder().s(LOCAL_TIME.toString()).build());
    }

    @DisplayName("테이블 생성")
    @Test
    public void createTable(){
        // Given
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("feed_id")
                                .attributeType(ScalarAttributeType.S)
                                .build()

                ).keySchema(
                        KeySchemaElement.builder()
                                .keyType(KeyType.HASH)
                                .attributeName("feed_id")
                                .build()
                )
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .writeCapacityUnits(1L)
                        .readCapacityUnits(1L)
                        .build())
                .tableName("feed")
                .build();
        // When
        CreateTableResponse createTableResponse = dynamoDbClient.createTable(request);

        // Then
        then(createTableResponse.tableDescription().tableName()).isEqualTo("feed");
    }

    @DisplayName("항목 추가")
    @Test
    public void putItem(){

        // Given
        PutItemRequest request = PutItemRequest.builder()
                .tableName("feed")
                .item(item)
                .build();
        // When
        PutItemResponse putItemResponse = dynamoDbClient.putItem(request);
        // Then
        then(putItemResponse.sdkHttpResponse().statusCode()).isEqualTo(200);
    }

    @DisplayName("항목 조회")
    @Test
    public void getItem(){
        // Given
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("feed_id", AttributeValue.builder().s("1").build());
        // When
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("feed")
                .key(key)
                .build();
        GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
        // Then
        then(getItemResponse.item()).containsAllEntriesOf(item);
    }

    @DisplayName("항목 삭제")
    @Test
    public void deleteItem(){
        // Given
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("feed_id", AttributeValue.builder().s("1").build());
        // When
        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName("feed").key(key).build();
        DeleteItemResponse deleteItemResponse = dynamoDbClient.deleteItem(deleteItemRequest);
        // Then
        then(deleteItemResponse.sdkHttpResponse().statusCode()).isEqualTo(200);
    }


}
