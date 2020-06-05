package com.framework.mvc.render.impl;

import com.framework.mvc.process.RequestProcessorChain;
import com.framework.mvc.render.ResultRender;
import com.framework.mvc.type.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Desc 会解析程序里设置好的model 和 view
 * 渲染到对应的页面里
 * @Author FuYouJ
 * @date 2020/6/3 22:59
 */

public class ViewResultRender implements ResultRender {
    private ModelAndView modelAndView;
    public ViewResultRender(Object mv) {
        //如果入参类型是modelAndView ,
        if (mv instanceof ModelAndView){
            this.modelAndView = (ModelAndView) mv;
            //如果是String
        }else if (mv instanceof String){
            this.modelAndView = new ModelAndView().setView((String) mv);
        }else {
            throw new RuntimeException("请求类型错误");
        }


    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletRequest request = requestProcessorChain.getRequest();
        HttpServletResponse response = requestProcessorChain.getResponse();
        String path = modelAndView.getView();
        Map<String, Object> model = modelAndView.getModel();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        //支持jsp试图
        request.getRequestDispatcher("/templates/"+path).forward(request,response);
    }
}