package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import member.service.RemoveMemberService;
import mvc.command.CommandHandler;

public class RemoveMemberHandler implements CommandHandler {
    private static final String FORM_VIEW = "removeMemberForm";
    private RemoveMemberService removeMemberSvc = new RemoveMemberService();
    
    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // **이전의 handler 메소드 내용 참조
        // get 요청이면 processForm 메소드 실행
        // post 요청이면 processSubmit 메소드 실행
        // 아니면 에러 응답
        if (req.getMethod().equalsIgnoreCase("get")) {
            return processForm(req, res);
        } else if (req.getMethod().equalsIgnoreCase("post")) {
            return processSubmit(req, res);
        } else {
            res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return null;
        }
    }
    
    private String processForm(HttpServletRequest req, HttpServletResponse res) {
        // **이전 handler processForm 메소드 참조
        // view의 이름을 리턴
        return FORM_VIEW;
    }
    
    private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // password 파라미터를 얻기
        String password = req.getParameter("password");
        // - errors 맵을 만들어서
        // - request attribute에 넣고
        Map<String, Boolean> err = new HashMap<>();
        req.setAttribute("errors", err);
        
        // password 가 null 이거나 비어 있으면(empty)
        // - errors에 (코드와 true) 넣기
        // view의 이름을 리턴
        if (password == null || password.isEmpty()) {
            err.put("password", true);
            return FORM_VIEW;
        }
    
        // 세션에서 user 객체 얻기(authUser attribute)
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("authUser");
        // 서비스에게 일 시키기
        try {
            removeMemberSvc.removeMember(user, password);
            
            // 세션을 invalidate()
            session.invalidate();
            return "removeMemberSuccess";
        } catch (MemberNotFoundException e) { // catch문 2개 (각 문제마다)
            // 문제가 생기면 (문제1. 사용자 없는 경우
            //               (문제2. 패스워드가 틀린 경우
            // - errors 맵에 코드 추가
            // 폼으로 forward하도록 view이름 리턴
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        } catch (InvalidPasswordException e) {
            err.put("invalidPassword", true);
            return FORM_VIEW;
        }
    }
}