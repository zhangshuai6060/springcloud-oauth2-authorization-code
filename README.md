# 1.springcloud-oauth2-authorization-code
SpringCloud security +oauht2.0 授权码（authorization_code）模式+jwt 实现微服务的认证和授权(修改jdbc连接)
# 2.测试  
1.首先通过浏览器访问下面的地址获取授权码（code）  
http://localhost:9090/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://localhost:9090/GetToken  
2.跳转到登录页面  admin admin 登录
![image](https://github.com/zhangshuai6060/springcloud-oauth2-authorization-code/blob/master/images/20200622111352112.png)  
3.登录成功跳转  
![image](https://github.com/zhangshuai6060/springcloud-oauth2-authorization-code/blob/master/images/20200622111507305.png)
