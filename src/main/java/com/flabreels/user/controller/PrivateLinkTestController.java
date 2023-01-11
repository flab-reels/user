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
        String baseUrl = "http://vpce-03dec6fdfffe46008-78exva8s.vpce-svc-02274ea451b03aa5b.ap-northeast-2.vpce.amazonaws.com/privatelink?privatelink=godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link2")
    public ResponseEntity<String> privateLinkTest2(){
        String baseUrl = "vpce-01cb36638e6ec5736-uwdwv5q0.vpce-svc-01343629200457007.ap-northeast-2.vpce.amazonaws.com/privatelink/privatelink?godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link4")
    public ResponseEntity<String> privateLinkTest4(){
        String baseUrl = "http://naver.com";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
}
