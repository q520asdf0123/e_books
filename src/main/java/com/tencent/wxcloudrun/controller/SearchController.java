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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String selectInfo(@RequestBody Url url, Model model) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://www.pdfdrive.com" + url.getUrl(), String.class);
        Document parse = Jsoup.parse(forEntity.getBody());
        Element previewButtonMain = parse.getElementById("previewButtonMain");
        Elements byAttribute = previewButtonMain.getElementsByAttribute("data-preview");
        String attr = byAttribute.attr("data-preview");

        String[] split = attr.split("\\?");
        String id = split[1].split("&")[0].split("=")[1];
        String session = split[1].split("&")[1].split("=")[1];

        Download download = new Download();

        download.setPdfUrl(id, session);
        download.setMobiUrl(id, session);
        download.setEpubUrl(id, session);
        model.addAttribute("download", download);
        return "download";
    }

    private static String downloadUrl(String id, String session, String type) {
        return "https://www.pdfdrive.com/download.pdf?" +
                "id=" + id +
                "&h=" + session +
                "&u=cache&ext=" +
                type;
    }


    @Data
    public static class Url {
        private String url;
    }

    @Data
    public static class Download {
        private String pdfUrl;
        private String epubUrl;
        private String mobiUrl;

        public void setPdfUrl(String id, String session) {
            pdfUrl = downloadUrl(id, session, "pdf");
        }

        public void setEpubUrl(String id, String session) {
            epubUrl = downloadUrl(id, session, "epub");
        }

        public void setMobiUrl(String id, String session) {
            mobiUrl = downloadUrl(id, session, "mobi");
        }
    }
}
