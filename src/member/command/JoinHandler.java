package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

public class JoinHandler implements CommandHandler {
    private static final String FORM_VIEW = "joinForm";
    private JoinService joinService = new JoinService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (req.getMethod().equalsIgnoreCase("GET")) {
            return processForm(req, res);
        } else if (req.getMethod().equalsIgnoreCase("POST")) {
            return processSubmit(req, res);
        } else {
            res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return null;
        }
    }

    private String processForm(HttpServletRequest req, HttpServletResponse res) {
        return FORM_VIEW;
    }

    private String processSubmit(HttpServletRequest req, HttpServletResponse res) {

        JoinRequest joinReq = new JoinRequest();
        joinReq.setId(req.getParameter("id"));
        // 문자열을 리퀘스트에서 가져와서 joinReq에 id를 저장
        joinReq.setName(req.getParameter("name"));
        joinReq.setPassword(req.getParameter("password"));
        joinReq.setConfirmPassword(req.getParameter("confirmPassword"));     
    
        Map<String, Boolean> errors = new HashMap<>();
        req.setAttribute("errors", errors);
        
        joinReq.validate(errors);

        if (!errors.isEmpty()) {
            return FORM_VIEW;
        }
        try {
            joinService.join(joinReq);
            return "joinSuccess";
        } catch (DuplicateIdException e) {
            errors.put("duplicateId", true);
            return FORM_VIEW;
        }

    }

}
