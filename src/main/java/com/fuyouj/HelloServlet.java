package com.fuyouj;
import com.fuyouj.entity.bo.HeadLine;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FuYouJ
 * @date 2020/4/22  21:54
 * http://localhost:8080/simpleframework/hello
 **/
@WebServlet("/hello")
@Slf4j
public class HelloServlet extends HttpServlet {
    /**
     * 只会初始化一次
     */
    @Override
    public void init(){
        System.out.println("初始化Servlet..");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = "我的简易框架";
        request.setAttribute("name",name);
        log.info(name+"------");

    }

    @Override
    public void destroy() {
        System.out.println("销毁");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("是我执行了doGet方法，我才是入口");
        doGet(req,resp);
    }
}
