package controller;

import api.SDKTOOL;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Student;
import service.StudentServiceimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SmsController {
    @Autowired
    StudentServiceimpl userService;
    private static Logger logger = Logger.getLogger(SmsController.class);
    SDKTOOL sdktool = new SDKTOOL();
    //短信验证
    @RequestMapping(value = "/message",method = RequestMethod.GET)
    public void message(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String telephone){
//       String telephone = httpServletRequest.getParameter("telephone");
        logger.info("telephone"+telephone);
        sdktool.messageTool(telephone);
    }


    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return "/logup";
    }

    //提交注册信息
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(HttpServletRequest httpServletRequest, Student student){
        HttpSession session = httpServletRequest.getSession();
        String telephone = httpServletRequest.getParameter("verify");
        String c_verify = sdktool.getS_verify();

        JSONObject jsonObjArr = new JSONObject();
        if (telephone.equals(c_verify)) {
            userService.addUser(student);
            jsonObjArr.put("data", "报名成功");
            return jsonObjArr;
        }else {
            jsonObjArr.put("data", "验证码错误");
        return jsonObjArr;
        }
    }
}
