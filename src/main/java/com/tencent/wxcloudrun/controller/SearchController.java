package com.tencent.wxcloudrun.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search")
    public String search(String name) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.pdfdrive.com/search?q=" + name, String.class);
        Document parse = Jsoup.parse(forEntity.getBody());
        Elements elementsByClass = parse.getElementsByClass("files-new");
        return elementsByClass.toString();
    }
}
