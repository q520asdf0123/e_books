package com.tencent.wxcloudrun.controller;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search")
    public String search(String name, Model model) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.pdfdrive.com/search?q=" + name, String.class);
        Document parse = Jsoup.parse(forEntity.getBody());
        Elements elementsByClass = parse.getElementsByClass("files-new");
        Elements ul = elementsByClass.tagName("ul");
//        Elements row = elementsByClass.select("row");
        Elements li = ul.select("li");
        int index = 1;
        for (Element element : li) {
//            Element test = element.getElementsByAttribute("test1111");
//            Element element1 = element.get
//            Elements onclick = element.getElementsByAttribute("onclick");
//            System.out.println();

        }
//        model.addAttribute("data", elementsByClass.toString());
        model.addAttribute("data", ul.toString());
        return "search";
    }

    @PostMapping("/selectInfo")
    @ResponseBody
    public Url selectInfo(@RequestBody Url url, Model model) {
        url.setUrl("https://www.pdfdrive.com" + url.getUrl());
        return url;
    }

    @Data
    public static class Url {
        private String url;
    }
}
