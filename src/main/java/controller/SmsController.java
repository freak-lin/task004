package controller;

import api.SDKTOOL;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Student;
import service.SmsServiceImpl;
import service.StudentServiceimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SmsController {
    @Autowired
    StudentServiceimpl userService;
    @Autowired
    SmsServiceImpl smsService;
    private static Logger logger = Logger.getLogger(SmsController.class);
    private SDKTOOL sdktool = new SDKTOOL();
    //短信验证
    @RequestMapping(value = "/message",method = RequestMethod.GET)
    public void message(String telephone){
         smsService.message(telephone);
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(){
        return "/logup";
    }

    //提交注册信息
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestParam String verify,Student student){
        return smsService.register(verify, student);
        }
    }
