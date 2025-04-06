# Spring Security

功能丰富且易用的java工具库
- [Hugo](https://hutool.cn/)
- [Hugo Tools](https://github.com/chinabugotech/hutool)
> 涵盖了字符串、数字、集合、编码、日期、文件、IO、加密、数据库JDBC、JSON、HTTP客户端等一系列操作， 可以满足各种不同的开发需求。

## Basic Knowledge

### @Controller & @RestController
- Controller --- String,JsonObject,Html
- RestController --- String, JsonObject
 


FilterChainProxy.java


UserDetails 属性检查
AbstractUserDetailsAuthenticationProvider.DefaultPreAuthenticationChecks
- user.isAccountNonLocked() --- 是否锁定
- user.isEnabled() --- 是否激活
- user.isAccountNonExpired() --- 是否过期



自定义登录页面 要与SecurityConfig 相一致
FormLoginConfigurer.loginProcessingUrl("/user/login")

Spring 框架中 实现Filter 直接继承 OncePerRequestFilter 更方便


### 配置 加载静态资源
```xml
 <build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.*</include>
            </includes>
        </resource>
    </resources>
</build>
```


### 密碼哈希算法


### Bcrypt 加密原理
随机加盐salt(22位字符串)后 再进行加密 得到的
密文密码 (version + salt + hash)
即： brcypt(明文密码 + 22位随机盐salt) = 密文

### 密码匹配原理
1. 从密文密码中 取出盐salt(22位)
2. 用户输入的密码 + 第一步取出的盐salt 进行Bcrypt加密
3. 新得到的密文密码 与 数据库中的 密文密码 进行比较， 一致则通过


### 获取登录用户信息

```java
  /**
     * 用下面三个对象/类/接口 接受都可以获得 登录信息
     * java.security.Principal
     * org.springframework.security.core.Authentication
     * org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     * OR
     * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     */
    @ResponseBody
    @PostMapping("/welcome")
    private Object welcome() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return usernamePasswordAuthenticationToken;
//        return authentication;
        return return  (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

/**
 * @auth Felix
 * @since 2025/3/30 22:24
 */
public class LoginInfoUtils {
    public static Users getLoginUserInfo() {
        return  (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
```



## 权限管理框架

### 基于角色 的权限管理

1. 权限拦截 注解
- PreAuthorize --- 方法调用之前 权限检查
- PostAuthorize --- 方法调用之后 权限检查

2. 开启权限检查注解
- @EnableMethodSecurity(prePostEnabled = true)

3. 定义的接口方法必须是 public
```java
    @PreAuthorize(value = "hasRole('admin')")
    @GetMapping("/create")
    public Object createUser() {
        return "createUser";
    }
```

添加角色
```java
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return AuthorityUtils.NO_AUTHORITIES;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (null != roles) {
            for (Role role : roles) {
                // 添加角色 (ROLE)
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                // 添加权限 (资源)
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        return authorities;
    }
```


### 配置 403 error 页面
/resource/static/error/403.html
无权限的跳转会 自动定位到该文件


### 基于资源 的权限管理
1. RestController 方法上添加注解， 检查权限
```java
@PreAuthorize(value = "hasAuthority('course:create')")
@GetMapping("/create")
public Object createCourse() {
    return "createCourse";
}
```

2. 登录用户 查询资源(Permission)赋值
```java
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return AuthorityUtils.NO_AUTHORITIES;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // 添加角色
        if (null != roles) {
            for (Role role : roles) {
                // 添加角色 固定前缀ROLE_
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
        }
        // 添加资源权限
        if (null != permissions) {
            for (Permission permission : permissions) {
                authorities.add(new SimpleGrantedAuthority( permission.getCode()));
            }
        }
        return authorities;
    }

```







## 前后端分离 Security
跨域问题
- 协议不同 会跨域 http://xxx.xx   https://xxx.xx
- 端口不同
- 域名不同



### JWT JSON Web Token
[JWT](https://jwt.io/)

> 一种开放的行业标准(RFC7519),用于安全的上方之间传递信息， 常用于各方之间传递信息， 特别是身份认证领域。
组成结构：[HEADER].[Payload].[Signature]

JWT 包含三个部分， 用逗号','隔开
- HEADER --- 头部 
- Payload --- 负载 这里可以携带 业务数据，比如一些参数
- Signature --- 签名


1. Header 原文是个JSON对象， 通常如下
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```
JSON对象使用 ***BASE64URL*** 算法转换成字符串 ==> HEADER part

2. Payload 也是一个JSON对象 通常包含7个字段
- iss --- Issuer, 签发人
- exp --- Expiration Time, 过期时间
- sub --- subject, 主题
- aud --- audience, 听众/受众
- nbf --- Not Before, 生效时间
- iat --- Issued At, 签发时间
- jti --- JWT ID, 编号

也可以使用任何字段 组成JSON, 
同样使用BASE64URL 算法进行编码

3. Signature 签名部分是对 前两个部分的签名， 目的是<font color=yellow>***防止篡改***</font>

默认使用Header中的签名算法
产生方式：HMACSHA256(
    base64UrlEncoder(header) + ".",
    base64UrlEncoder(payload),
    secret
)

最终的JWT 内容组成如下

base64UrlEncoder(header) + "."
base64UrlEncoder(payload) + "."
HMACSHA256(
    base64UrlEncoder(header) + ".",
    base64UrlEncoder(payload),
    secret
)



