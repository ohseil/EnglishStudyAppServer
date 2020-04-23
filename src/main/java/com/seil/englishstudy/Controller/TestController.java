package com.seil.englishstudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping (value="/helloworld")
public class TestController {

    @GetMapping
    public String HelloWorld() {

        return "/helloworld.html";
    }
}
