package article.model;

public class ArticleContent {
    private Integer number;
    private String content;
    private String fileName2;
    private String fileName1;
    public ArticleContent(Integer number, String content) {
        super();
        this.number = number;
        this.content = content;
    }
    public ArticleContent(Integer number, String content, String fileName1, String fileName2) {
        this.number = number;
        this.content = content;
        this.fileName1 = fileName1;
        this.fileName2 = fileName2;
    }
    public Integer getNumber() {
        return number;
    }
    public String getContent() {
        return content;
    }
    public String getFileName1() {
        return fileName1;
    }
    
    public String getFileName2() {
        return fileName2;
    }
    
}