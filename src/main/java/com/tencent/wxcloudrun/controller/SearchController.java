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
import org.springframework.util.ObjectUtils;
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
        Element ul = elementsByClass.tagName("ul").get(0);
//        Elements row = elementsByClass.select("row");
        Elements a = ul.getElementsByTag("a");
        Elements li = ul.getElementsByTag("li");
        for (int i = 0; i < li.size(); i++) {
            Element element = li.get(i);
            String onclick = element.attr("onclick");
            if (ObjectUtils.isEmpty(onclick)) {
                continue;
            }

            element.attr("onclick", "javascript:void(0);");
            li.set(i, element);
        }
        for (int i = 0; i < a.size(); i++) {
            Element element = a.get(i);
            String dataId = element.attr("data-id");
            String dataTo = element.attr("data-to");
            if (ObjectUtils.isEmpty(dataId) && ObjectUtils.isEmpty(dataTo)) {
                continue;
            }
            String href = element.attr("href").replaceFirst("/", "");
            element.attr("href", "javascript:void(0);");
            element.attr("onclick", "downClick('" + href + "')");
            a.set(i, element);
        }

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
