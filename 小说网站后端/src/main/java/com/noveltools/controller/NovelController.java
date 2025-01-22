package com.noveltools.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/novel")
public class NovelController {

    @PostMapping("/print-title")
    public String printNovelTitle(@RequestParam String title) {
        System.out.println("收到的书名是: " + title);
        return "书名「" + title + "」已成功打印到控制台";
    }
} 