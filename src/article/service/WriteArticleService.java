package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;

public class WriteArticleService {
    private ArticleDao articleDao = new ArticleDao();
    private ArticleContentDao contentDao = new ArticleContentDao();
    
    public Integer write(WriteRequest req) {
        Connection conn = null;
        
        try {
            conn = ConnectionProvider.getConnection();
            conn.setAutoCommit(false);
            
            System.out.println("title1:"+req.getTitle());
            Article article = toArticle(req); 
            // request객체에 저장된 title과 writer를 article에 저장. 
            
            System.out.println("title2:"+article.getTitle());
            Article savedArticle = articleDao.insert(conn, article);
            //articleDao에 입력 ,게시물 번호,콘텐츠,filename1,filename2를 articleDao에 저장
            
            if (savedArticle == null) {
                throw new RuntimeException("fail to insert article");
            }
            
            ArticleContent content = new ArticleContent(
                    savedArticle.getNumber(),
                    req.getContent(),
                    req.getFileName1(),
                    req.getFileName2()
                    );
            
            ArticleContent savedContent = contentDao.insert(conn, content);
            
            if (savedContent == null) {
                throw new RuntimeException("fail to insert article_content");
            }
            
            conn.commit();
            
            return savedArticle.getNumber();
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            JdbcUtil.rollback(conn);
            throw e;
        } finally {
            JdbcUtil.close(conn);
        }
    }
    
    private Article toArticle(WriteRequest req) {
        return new Article(null, req.getWriter(), req.getTitle(), null, null, 0);
    }
}
