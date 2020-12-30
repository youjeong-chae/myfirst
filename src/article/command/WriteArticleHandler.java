package article.command;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;

public class WriteArticleHandler implements CommandHandler {
    private static final String FORM_VIEW = "newArticleForm";
    private WriteArticleService writeService = new WriteArticleService();
    
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
        Map<String, Boolean> errors = new HashMap<>();
        req.setAttribute("errors", errors);
        
        User user = (User) req.getSession().getAttribute("authUser");
        WriteRequest writeReq = createWriteRequest(user, req);
        writeReq.validate(errors);
        
        if (!errors.isEmpty()) {
            return FORM_VIEW;
        }
        
        Part part1 = req.getPart("uploadFile1"); //파트 나눠서 request객체에  part1이란 이름으로uploadfile을  저장.
        Part part2 = req.getPart("uploadFile2");
        
        writeReq.setFileName1(part1.getSubmittedFileName());//part1을 filename1이란 이름으로 writerequest에 저장
        writeReq.setFileName2(part2.getSubmittedFileName());
        
        
        int newArticleNo = writeService.write(writeReq); //새로운 게시글번호 저장 , insert해서 contentdao에 넣는 과정포함됨
        
        
        saveFile(newArticleNo, part1);// savefile에 게시글 번호와 part 저장
        saveFile(newArticleNo, part2);
        
        req.setAttribute("newArticleNo", newArticleNo); //request 객체에 게시글 번호저장
        
        return "newArticleSuccess";
    }
    


    private void saveFile(int num, Part part) {
        String fileName = part.getSubmittedFileName();//part에서 file네임 가져옴
        
        String folder = "C:\\upload-files\\" + num; //게시글번호(num)로 폴더명을 만들어서 folder라는 변수에 담음 
        
        File nf = new File(folder); // 새로운 파일 객체 생성
        
        //파일 저장시 상위 폴더가 없을경우 상위폴더 까지 생성
        if (!nf.exists()) {
            nf.mkdirs();  // 없으면 새 폴더가 생성
        }
        
        try {
            part.write("C:\\upload-files\\" + num + "\\" + fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
        return new WriteRequest(new Writer(user.getId(),
                user.getName()),
                req.getParameter("title"),
                req.getParameter("content"));
    }
}