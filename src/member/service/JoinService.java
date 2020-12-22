package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import member.model.MemberDao;

public class JoinService {
    private MemberDao memverDao = new MemberDao();
    
    public void join(JoinRequest joinReq) {
        Connection con = null;
        try {
            con = ConnectionProvider.getConnection();
            con.setAutoCommit(false);
            
            Member m = memberDao.selectById(con, joinReq.getId());
            
            if (m != null) {
                JdbcUtil.rollback(con);
                throw new DuplicateIdException();
            }
            
            Member member = new Member();
            member.setId(joinReq.getId());
            member.setName(joinReq.getName());
            member.setPassword(joinReq.getPassword());
            
            memberDao.insert(con, member);
            
            con.commit();
        } catch (SQLException e) {
            jdbcUtil.rollback(con);
            throw new RuntimeException(e);
        } finally {
            jdbcUtil.close(con);
        }
    }
}
