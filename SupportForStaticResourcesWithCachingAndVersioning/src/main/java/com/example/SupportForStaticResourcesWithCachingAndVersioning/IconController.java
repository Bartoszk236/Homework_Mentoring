package com.example.SupportForStaticResourcesWithCachingAndVersioning;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IconController {

    @RequestMapping("favicon.ico")
    String favicon() {
        return "forward:/images/favicon.png";
    }
}
