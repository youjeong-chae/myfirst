package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberDao {
    public void delete(Connection conn, String id) throws SQLException {
        String sqlString = "DELETE member"
                + "WHERE memberid=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            
            pstmt.executeUpdate();
        }
    }
    
    public void updadte(Connection conn, Member member)
            throws SQLException { 
        
        String sql = "UPDATE member"
                + "SET name=?, password=?"
                + "WHERE memberid=?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,  member.getName());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getId());
            
            pstmt.executeUpdate();
        }
    }
    
}
