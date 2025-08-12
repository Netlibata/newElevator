package com.example.V1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端路由控制器
 * 用于处理Vue SPA应用的路由，确保所有前端路由都返回index.html
 */
@Controller
public class FrontendController {

    /**
     * 处理根路径请求
     */
    @RequestMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    /**
     * 处理所有前端路由请求
     * 排除API请求、静态资源等
     */
    @RequestMapping(value = {
            "/dashboard",
            "/admin-login",
            "/admin-page",
            "/door-system",
            "/electrical-system",
            "/guidance-system",
            "/traction-system",
            "/abnormal-data",
            "/maintenance-log",
            "/maintenance-worker-dashboard",
            "/system-relationship-chart",
            "/user-management"
    })
    public String index() {
        return "forward:/index.html";
    }
}