package com.fuyouj.controller.frontend;

import com.framework.core.annotation.Controller;
import com.framework.inject.annotation.Autowired;
import com.framework.mvc.annotation.RequestMapping;
import com.framework.mvc.type.RequestMethod;
import com.fuyouj.entity.dto.MainPageInfoDTO;
import com.fuyouj.entity.dto.Result;
import com.fuyouj.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author FuYouJ
 * @date 2020/5/5  22:31
 **/
@Getter
@Controller
@RequestMapping(value = "main")
public class MainPageController {

    @Autowired("HeadLineShopCategoryCombineServiceImpl")
    private HeadLineShopCategoryCombineService combineService;
    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        Result<MainPageInfoDTO> info = combineService.getMainPageInfo();
        return info;
    }
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public void throwException(){
        throw new RuntimeException("抛出异常测试");
    }
}
