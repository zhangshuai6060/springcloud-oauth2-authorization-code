package com.example.springcloudalibabaoauthauth.controller;

import com.example.springcloudalibabaoauthauth.util.AuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 2 * @Author: ZhangShuai
 * 3 * @Date: 2020/6/20 11:22
 * 4
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    /*登录的方法*/
    @GetMapping("loginSuccess")
    public String login(){
        return "ok";
    }

    /**
     * 前端 可以根据这个地址 拿到token
     * 获取code 的回调地址
     * @param code
     * @return
     */
    @GetMapping("GetToken")
    public AuthToken GetToken(@RequestParam String code){

        //这里可以让前端 给你传一个 值 拿到这个值 可以进行回调到 这个url
        log.info("code is " + code);
        String authUrl = "http://localhost:9090/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic();
        header.add("Authorization", httpBasic);
        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", "http://localhost:9090/GetToken");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        //调用登录认证服务 生成jwt令牌
        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);
        //申请令牌信息
        AuthToken authToken = makeAuthToken(exchange);
        return authToken;
    }

    /**
     * 设置token值
     *
     * @param exchange 远程调用结果
     * @return
     */
    private AuthToken makeAuthToken(ResponseEntity<Map> exchange) {
        Map bodyMap = exchange.getBody();
        AuthToken authToken = new AuthToken();
        if (bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null ||
                bodyMap.get("expires_in") == null
        ) {
            authToken.setError((String) bodyMap.get("error"));
            authToken.setError_description((String) bodyMap.get("error_description"));
            return authToken;
        }
        authToken.setAccess_token((String) bodyMap.get("access_token"));//用户身份令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));//刷新令牌
        authToken.setJwt_token((String) bodyMap.get("jti"));//jwt令牌
        return authToken;
    }

    /*获取httpBasic的串*/
    private String getHttpBasic() {
        String string = "client" + ":" + "admin";
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }

}
