package service;

import api.MailApiSendCloud;
import cached.Memcached;
import controller.MailController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import pojo.Student;

import javax.servlet.http.HttpServletRequest;


@Service
public class MailServiceImpl implements MailService {
    @Autowired
    StudentService userService;
    @Autowired
    MailApiSendCloud mailApiSendCloud;

    private static Logger logger = Logger.getLogger(MailServiceImpl.class);


    @Override
    public Boolean sendMail(HttpServletRequest httpServletRequest, String mail, int id) {

        String httpUrl = httpServletRequest.getRequestURL().toString();
        logger.debug("访问项目网址为: " + httpUrl);

        //设置邮箱
        Student student = userService.queryUser(id);
        student.setMail(mail);
        return mailApiSendCloud.sendMail(httpUrl, student);
    }
    public boolean verifyCode(String verifyCode,Long id) {
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
