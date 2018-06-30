package service;

import net.sf.json.JSONObject;
import pojo.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.StreamSupport;

public interface SmsService {
    void message(String telephone);

    JSONObject register(String verify, Student student);
}
