package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.JdbcUtil;

//selectById와 insert에 들어가는 변수 확인.

public class ArticleContentDao {
    
    public int update(Connection conn, int no, String content) throws SQLException {
        String sql = "UPDATE article_contents "
                + "SET content=? "
                + "WHERE article_no=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2, no);
            
            int cnt = pstmt.executeUpdate();
            
            return cnt;
        }
    }
    
    public ArticleContent selectById(Connection conn, int no) throws SQLException {
        String sql = "SELECT * FROM article_contents  "
                + "WHERE article_no=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();
            ArticleContent content = null;
            
            if (rs.next()) {
                content = new ArticleContent(rs.getInt("article_no"), 
                        rs.getString("content"), rs.getString("file1"), rs.getString("file2"));
            }
            return content;
        } finally {
            JdbcUtil.close(rs, pstmt);
        }
    }
    
    
    public ArticleContent insert(Connection conn, ArticleContent content) 
        throws SQLException {
        
        String sql = "INSERT INTO article_contents "
                + "(article_no, content, file1, file2) "
                + "VALUES (?, ?, ?, ?)";
        //쿼리 스트링을 통해 parameter 값을 받는다. 
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, content.getNumber());
            pstmt.setString(2, content.getContent());
            pstmt.setString(3, content.getFileName1());
            pstmt.setString(4, content.getFileName2());
            
            int cnt = pstmt.executeUpdate();
            
            if (cnt == 1) {
                return content;
            } else {
                return null;
            }
        }
    }

    public void delete(Connection con, int no) throws SQLException {
        String sql = "DELETE article_contents WHERE article_no=?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            
            pstmt.executeUpdate();
        }
    }
}

