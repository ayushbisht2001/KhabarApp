package com.example.khabar.topheadlines;

public class TopHeadlinesModel {

    String title, desp, source;

    public TopHeadlinesModel(String title, String desp, String source) {
        this.title = title;
        this.desp = desp;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public String getDesp() {
        return desp;
    }

    public String getSource() {
        return source;
    }
}
