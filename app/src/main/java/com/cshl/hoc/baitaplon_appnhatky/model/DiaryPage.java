package com.cshl.hoc.baitaplon_appnhatky.model;

import java.io.Serializable;

public class DiaryPage implements Serializable {
    public String title;
    public String content;
    public long createdDateTime;

    public DiaryPage() {
    }

    public DiaryPage(String title, String content, long createdDateTime) {
        this.title = title;
        this.content = content;
        this.createdDateTime = createdDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return this.content;
    }
    public long getCreatedDateTime() {
        return createdDateTime;
    }

}
