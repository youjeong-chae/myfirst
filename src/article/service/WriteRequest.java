package article.service;

import java.util.Map;

import article.model.Writer;

public class WriteRequest {
    private Writer writer;
    private String title;
    private String content;
    //fileName1,2를 저장하기 위한 공간, get set 사용
    private String fileName1;
    private String fileName2;
    
    public WriteRequest(Writer writer, String title, String content) {
        super();
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
    public Writer getWriter() {
        return writer;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    
    public void validate(Map<String, Boolean> errors) {
        if (title == null || title.trim().isEmpty()) {
            errors.put("title", true);
        }
    }
    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }
    public void setFileName2(String fileName2) {
        this.fileName2 = fileName2;
    }
    
    public String getFileName1() {
        return fileName1;
    }
    
    public String getFileName2() {
        return fileName2;
    }
}