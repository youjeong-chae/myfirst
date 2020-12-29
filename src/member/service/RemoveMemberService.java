package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import auth.service.User;
import jdbc.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

public class RemoveMemberService {
    private MemberDao memberDao = new MemberDao();
    
    public void removeMember(User user, String password) {
        // 0. 커넥션 얻기
        Connection con = ConnectionProvider.getConnection();
        
        try {
            // 1. dao의 selectById로 member객체 얻기
            //    1.1 member없으면 MemberNotFoundException 발생
            Member member = memberDao.selectById(con, user.getId());
            if (member == null) {
                throw new MemberNotFoundException();
            }
            // 2. password와 member.password가 같은 지 확인
            //    2.1 다르면 InvalidPasswordException 발생
            if (!member.matchPassword(password)) {
                throw new InvalidPasswordException();
            }
            
            // 3. delete() 실행
            memberDao.delete(con, user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(con);
        }
        
    }
}