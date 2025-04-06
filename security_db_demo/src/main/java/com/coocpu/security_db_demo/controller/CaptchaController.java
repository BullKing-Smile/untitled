package com.coocpu.security_db_demo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @auth Felix
 * @since 2025/3/30 16:40
 */
@RequestMapping(value = "/common")
@RestController
public class CaptchaController {
    @GetMapping("/captcha")
    private void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ICaptcha captcha = CaptchaUtil.createCircleCaptcha(150, 30, 4, 5, 1);
        ICaptcha captcha = CaptchaUtil.createGifCaptcha(90, 30, 4, 5, 1);
        request.getSession().setAttribute("captcha", captcha.getCode());
        captcha.write(response.getOutputStream());
    }
}
