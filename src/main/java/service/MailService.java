package service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public interface MailService {
    Boolean sendMail(HttpServletRequest httpServletRequest,String mail,int id);
    boolean verifyCode(String verifyCode,Long id);
}
