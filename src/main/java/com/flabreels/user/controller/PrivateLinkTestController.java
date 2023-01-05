package com.flabreels.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PrivateLinkTestController {

    @GetMapping("/link1")
    public ResponseEntity<String> privateLinkTest1(){
        String baseUrl = "http://vpce-0880a5a57c91cd4cb-ttcv9iny.ecs.ap-northeast-2.vpce.amazonaws.com";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link2")
    public ResponseEntity<String> privateLinkTest2(){
        String baseUrl = "vpce-0880a5a57c91cd4cb-ttcv9iny-ap-northeast-2b.ecs.ap-northeast-2.vpce.amazonaws.com";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link3")
    public ResponseEntity<String> privateLinkTest3(){
        String baseUrl = "http://vpce-0880a5a57c91cd4cb-ttcv9iny-ap-northeast-2a.ecs.ap-northeast-2.vpce.amazonaws.com";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }

}
