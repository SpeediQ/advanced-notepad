package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Note {
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime editTime;
    private int countChanges = 0;
//    private LocalDateTime create;

    public Note(String title, String text) {
        this.title = title;
        this.content = text;
        this.createTime = LocalDateTime.now();
        this.editTime = this.createTime;

    }
    public Note(String title, String text, LocalDateTime editTime) {
        this.title = title;
        this.content = text;
        this.createTime = LocalDateTime.now();
        this.editTime = editTime;
    }

    @Override
    public String toString() {
        String data = editTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

        return title +" (" +data+")";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }

    public int getCountChanges() {
        return countChanges;
    }

    public void setCountChanges(int countChanges) {
        this.countChanges = countChanges;
    }
}