package cn.itcast.domain;

import java.util.Date;

public class Msg {
    private String from;
    private String theme;
    private Date sentDate;
    private String content;

    @Override
    public String toString() {
        return "Msg{" +
                ", from='" + from + '\'' +
                ", theme='" + theme + '\'' +
                ", sentDate=" + sentDate +
                ", content='" + content + '\'' +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
