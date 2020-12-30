package article.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article2;
import jdbc.ConnectionProvider;

public class MainArticleService {
    private ArticleDao dao = new ArticleDao(); // Dao 필드 선언

    public List<Article2> getList() {
        List<Article2> list = new ArrayList<>(); // Article2 형식의 객체를 list이름으로 생성 
        
        try (Connection con = ConnectionProvider.getConnection();) {
            list = dao.selectMainList(con); //list에 dao에 저장된 table내용을 저장.
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return list;
    }

}
