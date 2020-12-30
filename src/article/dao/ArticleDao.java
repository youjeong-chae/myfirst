package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Article2;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {
    
    //insert내용 유라한테 설명한번 들을것
    
    public int update(Connection conn, int no, String title) throws SQLException {
        String sql = "UPDATE articles "
                + "SET title=?, moddate=SYSDATE "
                + "WHERE article_no=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, no);
            
            int cnt = pstmt.executeUpdate();
            return cnt;
        }
    }
    
    
    public Article selectById(Connection conn, int no) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * "
                + "FROM articles "
                + "WHERE article_no=?";
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();
            
            Article article = null;
            
            if (rs.next()) {
                article = convertArticle(rs);
            }
            return article;
        } finally {
            JdbcUtil.close(rs, pstmt);
        }
        
    }
    
    public void increaseReadCount(Connection conn, int no) throws SQLException {
        String sql = "UPDATE articles "
                + "SET read_cnt=read_cnt+1 "
                + "WHERE article_no=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            pstmt.executeUpdate();
        }
    }
    
    
    public List<Article> select(Connection conn, int pageNum, int size) 
        throws SQLException {
        
        /*
        String sql = "SELECT * "
                + "FROM article "
                + "ORDER BY articl_no DESC "
                + "LIMIT ?, ?"; // 시작 row_num(zerobase), 갯수
        */
        String sql = "SELECT "
                + "rn, "
                + "article_no, "
                + "writer_id, "
                + "writer_name, "
                + "title, "
                + "regdate, "
                + "moddate, "
                + "read_cnt "
                + "FROM ("
                + " SELECT article_no, "
                + "            writer_id, "
                + "        writer_name, "
                + "        title, "
                + "        regdate, "
                + "        moddate, "
                + "        read_cnt, "
                + "        ROW_NUMBER() "
                + "          OVER ( "
                + "            ORDER BY "
                + "            article_no "
                + "            DESC) "
                + "        rn "
                + "  FROM articles "
                + ") WHERE rn "
                + "    BETWEEN ? AND ?";
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (pageNum-1) * size + 1);
            pstmt.setInt(2, pageNum * size);
            
            rs = pstmt.executeQuery();
            List<Article> result = new ArrayList<>();
            while (rs.next()) {
                result.add(convertArticle(rs));
            }
            
            return result;
        } finally {
            JdbcUtil.close(rs, pstmt);
        }
    }
    
    private Article convertArticle(ResultSet rs) throws SQLException {
        return new Article(rs.getInt("article_no"),
                    new Writer(
                            rs.getString("writer_id"),
                            rs.getString("writer_name")
                            ),
                    rs.getString("title"),
                    rs.getTimestamp("regdate"),
                    rs.getTimestamp("moddate"),
                    rs.getInt("read_cnt")
                );
    }
    
    public int selectCount(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM articles";
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            JdbcUtil.close(rs, stmt);
        }
    }
    
    
    public Article insert(Connection conn, Article article) 
            throws SQLException {
        // 12c 이상
        String sql = "INSERT INTO articles "
                + "(writer_id, writer_name, title,"
                + " regdate, moddate, read_cnt) "
                + "VALUES (?, ?, ?, SYSDATE, SYSDATE, 0)";
        
        // 11g
        /*
        String sql = "INSERT INTO article "
                + "(article_no, writer_id, writer_name, title,"
                + " regdate, moddate, read_cnt) "
                + "VALUES (article_seq.nextval, ?, ?, ?, SYSDATE, SYSDATE, 0)";
                */
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conn.prepareStatement(sql,
                            new String[] {"article_no", "regdate", "moddate"});
            
            pstmt.setString(1, article.getWriter().getId());
            pstmt.setString(2, article.getWriter().getName());
            pstmt.setString(3, article.getTitle());
            
            int cnt = pstmt.executeUpdate();
            
            if (cnt == 1) {
                rs = pstmt.getGeneratedKeys();
                int key = 0;
                Date regDate = null;
                Date modDate = null;
                if (rs.next()) {
                    key = rs.getInt(1);
                    regDate = rs.getTimestamp(2);
                    modDate = rs.getTimestamp(3);
                }
                return new Article(key,
                        article.getWriter(),
                        article.getTitle(),
                        regDate,
                        modDate,
                        0);
            } else {
                return null;
            }
        } finally {
            JdbcUtil.close(rs, pstmt);
        }
    }


    public void delete(Connection con, int no) throws SQLException {
        String sql = "DELETE articles WHERE article_no=?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            
            pstmt.executeUpdate();
        }
    }


    public List<Article2> selectMainList(Connection con) throws SQLException {
        //article2형식의 MainselectList
        String sql = "SELECT a.article_no, a.title, c.file1 "
                + "FROM articles a JOIN article_contents c "
                + "     ON a.article_no = c.article_no "
                + "ORDER BY a.article_no DESC";
        //articles a , article contents c => 두 테이블을 조인해서 원하는 컬럼 뽑아내기
        //? 쿼리스트링이 없으므로 statement 활용 
        
        List<Article2> list = new ArrayList<>();
        
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Article2 article2 = new Article2();
                article2.setNumber(rs.getInt(1));
                article2.setTitle(rs.getString(2));
                article2.setFileName1(rs.getString(3));
                
                list.add(article2);
            }
        }
        
        return list;
    }


}
//prepared statement 와 statement의 차이
//1.쿼리 문장 분석
//2.컴파일
//3.실행

//Statement를 사용하면 매번 쿼리를 수행할 때마다 1) ~ 3) 단계를 거치게 되고, 
//PreparedStatement는 처음 한 번만 세 단계를 거친 후 캐시에 담아 재사용을 한다는 것이다. 
//만약 동일한 쿼리를 반복적으로 수행한다면 PreparedStatment가 DB에 훨씬 적은 부하를 주며, 성능도 좋다.
















