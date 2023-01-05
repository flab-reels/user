package com.flabreels.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PrivateLinkTestController {

    @GetMapping("/link")
    public String privateLinkTest(){
        return "http://vpce-0880a5a57c91cd4cb-ttcv9iny.ecs.ap-northeast-2.vpce.amazonaws.com/privatelink/privatelink?thatWorks?";
    }

}
