package service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface QiniuSercvice {
    String updaImage(MultipartFile image,int id) throws Exception;

}
