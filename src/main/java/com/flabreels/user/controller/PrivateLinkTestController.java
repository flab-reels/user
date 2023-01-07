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
        String baseUrl = "http://vpce-08d62bfe2209ad38b-wevn5ij8.vpce-svc-00bfa833a343b6727.ap-northeast-2.vpce.amazonaws.com/privatelink/privatelink?godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link2")
    public ResponseEntity<String> privateLinkTest2(){
        String baseUrl = "http://vpce-08d62bfe2209ad38b-wevn5ij8-ap-northeast-2a.vpce-svc-00bfa833a343b6727.ap-northeast-2.vpce.amazonaws.com/privatelink/privatelink?godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link3")
    public ResponseEntity<String> privateLinkTest3(){
        String baseUrl = "http://vpce-08d62bfe2209ad38b-wevn5ij8-ap-northeast-2b.vpce-svc-00bfa833a343b6727.ap-northeast-2.vpce.amazonaws.com/privatelink/privatelink?godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }

}
