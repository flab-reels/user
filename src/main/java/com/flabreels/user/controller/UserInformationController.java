package com.flabreels.user.controller;


import com.flabreels.user.dto.information.UserInformationRequestDto;
import com.flabreels.user.dto.information.UserInformationResponseDto;
import com.flabreels.user.repository.UserQuery;
import com.flabreels.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserInformationController {

    private final UserRepository userRepository;

    /**
     * HEADER => id, picture
     * PARAM => introduce,name
     */
    @PostMapping("/information")
    public ResponseEntity<UserInformationRequestDto> saveUserInformation(HttpServletRequest request){
        String userId = request.getHeader("id");
        String userProfile = request.getHeader("picture");
        String userIntroduce = request.getParameter("introduce");
        String username = request.getParameter("name");
        UserInformationRequestDto userInformationRequestDto = UserInformationRequestDto.builder()
                .userId(userId)
                .userProfile(userProfile)
                .username(username)
                .userIntroduce(userIntroduce)
                .build();
        try {
            userRepository.addUserInformation(userInformationRequestDto);
            return ResponseEntity.ok(userInformationRequestDto);
        }catch (DynamoDbException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * HEADER => id, picture
     * PARAM => introduce,name
     */
    @PutMapping("/information")
    public ResponseEntity<UserInformationRequestDto> updateUserInformation(HttpServletRequest request){
        String userId = request.getHeader("id");
        String userProfile = request.getHeader("picture");
        String userIntroduce = request.getParameter("introduce");
        String username = request.getParameter("name");
        UserInformationRequestDto userInformationRequestDto = UserInformationRequestDto.builder()
                .userId(userId)
                .userProfile(userProfile)
                .username(username)
                .userIntroduce(userIntroduce)
                .build();
        try {
            userRepository.updateUserInformation(userInformationRequestDto);
            return ResponseEntity.ok(userInformationRequestDto);
        }catch (DynamoDbException e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    /**
     * PARAM => id
     */
    @GetMapping("/information")
    public ResponseEntity<UserInformationResponseDto> getUserInformationByUsername(HttpServletRequest request){
        try {
            UserInformationResponseDto userInfo = userRepository.findUserInformationByUserId(UserQuery.builder()
                    .lastReadUserId(request.getParameter("id")).limit(1000).build());
            return ResponseEntity.ok(userInfo);
        } catch (DynamoDbException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }




}
