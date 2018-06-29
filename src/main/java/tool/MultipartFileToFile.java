package tool;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MultipartFileToFile{

    public static File  multipartFile(MultipartFile multipartFile)throws Exception{
        File f = null;
        if(multipartFile == null){
            return null;
        }else{
            InputStream ins = multipartFile.getInputStream();
            f =new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, f);
            return f;
        }
    }
    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
