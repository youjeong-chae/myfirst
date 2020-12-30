package article.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Article2;
import article.service.MainArticleService;
import mvc.command.CommandHandler;

public class MainHandler implements CommandHandler {

    private MainArticleService mainService = new MainArticleService();
    
    

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // TODO Auto-generated method stub
//        ArticlePage articlePage = listService.getArticlePage(1);
        
        List<Article2> articleList = mainService.getList();
        
        //mainService에 있는 List객체를 articleList에 가져옴.
        
        req.setAttribute("articleList", articleList);
        
        // request객체에 articleList란 이름으로 List값 저장. 
        
        
        return "index";
    }

}
