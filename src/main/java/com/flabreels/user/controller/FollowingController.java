package com.flabreels.user.controller;

import com.flabreels.user.dto.following.UserAddFollowingRequestDto;
import com.flabreels.user.dto.following.UserFollowedListResponseDto;
import com.flabreels.user.dto.following.UserFollowingListResponseDto;
import com.flabreels.user.repository.UserQuery;
import com.flabreels.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FollowingController {

    private final UserRepository userRepository;
    /**
     * HEADER => id
     * PARAM => followingId
     */
    @PostMapping("/following")
    public ResponseEntity<UserAddFollowingRequestDto> addFollowing(HttpServletRequest request){
        String userId = request.getHeader("id");
        String followingId = request.getParameter("followingId");
        try{
            userRepository.addFollowing(UserAddFollowingRequestDto.builder().userId(userId).followingId(followingId).build());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DynamoDbException e){
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping ("/following")
    public ResponseEntity<UserAddFollowingRequestDto> removeFollowing(HttpServletRequest request){
        String userId = request.getHeader("id");
        String followingId = request.getParameter("followingId");
        try{
            userRepository.removeFollowing(UserAddFollowingRequestDto.builder().userId(userId).followingId(followingId).build());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (DynamoDbException e){
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Header => id
     */
    @GetMapping("/followed")
    public ResponseEntity<List<UserFollowedListResponseDto>> followedUsers(HttpServletRequest request){
        try {
            return ResponseEntity.ok(userRepository.findFollowedByFollowingId(UserQuery.builder().followingId(request.getHeader("id")).build()));
        }catch (DynamoDbException e){
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Header => id
     */
    @GetMapping("/following")
    public ResponseEntity<List<UserFollowingListResponseDto>> followingUsers(HttpServletRequest request){
        try{
            return ResponseEntity.ok(userRepository.findFollowingByUserId(UserQuery.builder().lastReadUserId(request.getHeader("id")).build()));
        }catch (DynamoDbException e){
            log.info(e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
