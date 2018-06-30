package service;

import api.CCPRestSmsSDK;
import api.SDKTOOL;
import controller.SmsController;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Student;

import javax.servlet.http.HttpServletRequest;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    StudentService studentService;
    private static Logger logger = Logger.getLogger(SmsServiceImpl.class);

    private SDKTOOL sdktool = new SDKTOOL();
    @Override
    public void message(String telephone) {
        logger.info("telephone" + telephone);
        sdktool.messageTool(telephone);
    }

    @Override
    public JSONObject register(String verify, Student student) {

        logger.info("用户输入的值="+verify);

        String c_verify = sdktool.getS_verify();
        logger.info("验证码="+c_verify);
        JSONObject jsonObjArr = new JSONObject();
        if (verify.equals(c_verify)) {
            studentService.addUser(student);
            jsonObjArr.put("data", "报名成功");
            return jsonObjArr;
        } else {
            jsonObjArr.put("data", "验证码错误");
            return jsonObjArr;
        }
    }
}
