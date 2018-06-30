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
import service.MailService;
import service.StudentServiceimpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MailController {
    @Autowired
    StudentServiceimpl userService;
    @Autowired
    MailService mailService;
    private static Logger logger = Logger.getLogger(MailController.class);

    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    @ResponseBody
    public Boolean sendMail(HttpServletRequest httpServletRequest, @RequestParam String mail,@RequestParam int id) {
        return mailService.sendMail(httpServletRequest, mail, id);
    }

    // 效验
    @RequestMapping(value = "/sendMail/{verifyCode}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean verifyCode(@PathVariable String verifyCode,@PathVariable Long id) {
        return mailService.verifyCode(verifyCode, id);
    }
}
