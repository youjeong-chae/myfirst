package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.ChangePasswordService;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import mvc.command.CommandHandler;

public class ChangePasswordHandler implements CommandHandler {
    private static final String FORM_VIEW = "changePwdForm";
    private ChangePasswordService changePwdSvc = new ChangePasswordService();
    
    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        return FORM_VIEW;
    }
    
    private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
        User user = (User) req.getSession().getAttribute("authUser");
        
        Map<String, Boolean> errors = new HashMap<>();
        req.setAttribute("errors", errors);

        String curPwd = req.getParameter("curPwd");
        String newPwd = req.getParameter("newPwd");
        String confirmNewPwd = req.getParameter("confirmNewPwd");
        
        if (curPwd == null || curPwd.isEmpty()) {
            errors.put("curPwd", true);
        }
        
        if (newPwd == null || newPwd.isEmpty()) {
            errors.put("newPwd", true);
        } 
        if (confirmNewPwd == null || confirmNewPwd.isEmpty()) {
            errors.put("confirmNewPwd", true);  
        }
        
        if (!errors.isEmpty()) {
            return FORM_VIEW;
        
        } else {
            if (!newPwd.equals(confirmNewPwd)) {
                errors.put("notMatch", true);
                return FORM_VIEW;
            }
        }

        try {
            changePwdSvc.changePassword(user.getId(), curPwd, newPwd);
            return "changePwdSuccess";
        } catch (InvalidPasswordException e) {
            errors.put("badCurPwd", true);
            return FORM_VIEW;
        } catch (MemberNotFoundException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
