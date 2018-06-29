package controller;

import api.MailApiSendCloud;
import cached.Memcached;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pojo.Student;
import service.StudentServiceimpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MailController {
    @Autowired
    StudentServiceimpl userService;
    private static Logger logger = Logger.getLogger(MailController.class);

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    @ResponseBody
    public Boolean sendMail(HttpServletRequest httpServletRequest, @RequestParam String mail,@RequestParam int id) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "application.xml");
        MailApiSendCloud mailApiSendCloud = (MailApiSendCloud) context.getBean("mailApi");
        String httpUrl = httpServletRequest.getRequestURL().toString();
        logger.debug("访问项目网址为: " + httpUrl);
        //设置邮箱
        Student student = userService.queryUser(id);
        student.setMail(mail);
        return mailApiSendCloud.sendMail(httpUrl, student);
    }

    // 效验
    @RequestMapping(value = "/sendMail/{verifyCode}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean verifyCode(@PathVariable String verifyCode,@PathVariable Long id) {
        Student student = (Student) Memcached.get(verifyCode);
        if (student != null) {
            logger.debug("studentCustom 邮箱验证:" + student.toString());
            // 该验证码请求只要被接收到就失效
            Memcached.delete(verifyCode);
            // 改变邮箱状态.
            student.setMailboxeVrification(true);
            try {
                // 存入数据库 判断是否存入成功
                return userService.updataUser(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
