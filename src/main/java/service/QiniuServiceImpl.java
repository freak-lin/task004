package service;

import api.QiniuSDK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pojo.Student;

@Service
public class QiniuServiceImpl implements QiniuSercvice{
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QiniuServiceImpl.class);

    @Autowired
    StudentService studentService;
    @Autowired
    QiniuSDK qiniuSDK;

    @Override
    public String updaImage(MultipartFile image, int id) throws Exception {
        logger.info("进入方法" + image);
        logger.info("id=" + id);
        String imageUrl = qiniuSDK.updateFile(image);
        logger.info(imageUrl + "===========URL");
        String finalImageUrl = qiniuSDK.download(imageUrl);
        logger.info(finalImageUrl + "=================final Url");
        Student student = studentService.queryUser(id);
        //设定头像
        student.setHeadPortrait(finalImageUrl);
        studentService.updataUser(student);
        return finalImageUrl;

    }
}
