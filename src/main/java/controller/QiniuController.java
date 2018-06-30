package controller;


import api.QiniuSDK;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pojo.Student;
import service.QiniuServiceImpl;
import service.StudentService;

@Controller
public class QiniuController {
    private static Logger logger = Logger.getLogger(QiniuController.class);
@Autowired
    QiniuServiceImpl qiniuService;
    @RequestMapping( value = "/updaImage" ,method = RequestMethod.POST)
    @ResponseBody
    public String updaImage(@RequestParam(value = "file", required = false)MultipartFile image,@RequestParam int id)throws Exception{
        return qiniuService.updaImage(image, id);
    }
}