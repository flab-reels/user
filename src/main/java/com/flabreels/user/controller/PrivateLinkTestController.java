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
        String baseUrl = "http://vpce-0e73b706855906f7f-xhd793sm.vpce-svc-00d5d598b075b90d2.ap-northeast-2.vpce.amazonaws.com/privatelink?privatelink=godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link2")
    public ResponseEntity<String> privateLinkTest2(){
        String baseUrl = "http://vpce-0e73b706855906f7f-xhd793sm-ap-northeast-2b.vpce-svc-00d5d598b075b90d2.ap-northeast-2.vpce.amazonaws.com/privatelink?privatelink=godol";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(baseUrl,String.class);
    }
    @GetMapping("/link3")
    public ResponseEntity<String> privateLinkTest3(){
        String baseUrl = "http://vpce-0e73b706855906f7f-xhd793sm-ap-northeast-2a.vpce-svc-00d5d598b075b90d2.ap-northeast-2.vpce.amazonaws.com/privatelink?privatelink=godol";
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
