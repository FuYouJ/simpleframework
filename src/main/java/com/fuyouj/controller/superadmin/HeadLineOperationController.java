package com.fuyouj.controller.superadmin;

import com.framework.core.annotation.Controller;
import com.framework.inject.annotation.Autowired;
import com.framework.mvc.annotation.RequestMapping;
import com.framework.mvc.annotation.RequestParam;
import com.framework.mvc.annotation.ResponseBody;
import com.framework.mvc.type.ModelAndView;
import com.framework.mvc.type.RequestMethod;
import com.fuyouj.entity.bo.HeadLine;
import com.fuyouj.entity.dto.Result;
import com.fuyouj.service.solo.HeadLineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author FuYouJ
 * @date 2020/5/5  22:33
 **/
@Controller
@RequestMapping(value = "headline")
public class HeadLineOperationController {
    @Autowired(value = "HeadLineServiceImpl")
    private HeadLineService headLineService;
    //添加
    @RequestMapping(value = "add",method = RequestMethod.POST)
  public ModelAndView addHeadLine(@RequestParam("lineName") String lineName,
                                  @RequestParam("lineLink") String lineLink,
                                  @RequestParam("lineImg") String lineImg,
                                  @RequestParam("priority") Integer priority){
        HeadLine headLine = new HeadLine();
        headLine.setLineName(lineName);
        headLine.setLineImg(lineImg);
        headLine.setLineLink(lineLink);
        headLine.setPriority(priority);
        //TODO
        Result<Boolean> result = headLineService.addHeadLine(headLine);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("addHeadline.jsp").addViewData("result",result);
        return  modelAndView;
    }
    @RequestMapping(value = "toadd",method = RequestMethod.GET)
    public ModelAndView toAddPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("addHeadline.jsp");
        return modelAndView;
    }
    //删除
    @RequestMapping(value = "remove",method = RequestMethod.GET)
     public void emoveHeadLine(){
        //TODO
        System.out.println("删除headline");
    }
    //修改
   public Result<Boolean>  modifyHeadLine(HttpServletRequest req, HttpServletResponse resp){
        //TODO
        return headLineService.modifyHeadLine(new HeadLine());
    }
    //查询

    public Result<HeadLine> queryHeadlineById(){
        //TODO
        return headLineService.queryHeadlineById(1);
    }
    //获取头条列表
    @ResponseBody
    @RequestMapping(value = "query",method = RequestMethod.GET)
    public Result<List<HeadLine>>  queryHeadline(){
        return headLineService.queryHeadline(null,1,100);
    }
}
