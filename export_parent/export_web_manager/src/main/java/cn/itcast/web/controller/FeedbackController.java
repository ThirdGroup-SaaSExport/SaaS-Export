package cn.itcast.web.controller;

import cn.itcast.common.utils.MailUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController{

    @RequestMapping("/list")
    public String list(){
        return "feedback";
    }

    @RequestMapping("/send")
    public String send(String feedback) throws Exception {
        System.out.println(feedback);
        System.out.println(loginUser.getEmail());
        MailUtil.sendMsg(loginUser.getEmail(),"反馈信息",feedback);
        return "success";
    }

}
