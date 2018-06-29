package tool;

import api.QiniuSDK;
import api.UrlDownload;
import api.aliyun;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

public class QiniuToAliyun {
        private static Logger logger = LoggerFactory.getLogger(QiniuToAliyun.class);
        @Autowired
        QiniuSDK updateImageSDK;
        @Autowired
        aliyun aliyunOSSAPI;

        // 七牛文件转存阿里云

        // prefixHttp 需要知道OSS文件外网地址的前缀

        public boolean qiNiuFileToAliyun (String bucketname, String prefixHttp)throws Exception {
            return qiNiuFileToAliyunReal(bucketname,"",0,"",prefixHttp);
        }

        public boolean qiNiuFileToAliyun(String bucketname, String prefix, int limit, String delimiter, String prefixHttp) throws IOException{
            return qiNiuFileToAliyunReal(bucketname, prefix, limit, delimiter, prefixHttp);
        }

        private boolean qiNiuFileToAliyunReal(String bucketname, String prefix, int limit, String delimiter, String prefixHttp) throws IOException {
            // 获取七牛OSS中文件列表
            BucketManager.FileListIterator fileListIterator = updateImageSDK.getObjectList(bucketname, prefix, limit, delimiter);
            if (fileListIterator != null) {
                // 遍历
                while (fileListIterator.hasNext()) {
                    FileInfo[] items = fileListIterator.next();
                    for (FileInfo item : items) {
                        logger.debug(item.key);
                        logger.debug(item.hash);
                        logger.debug(String.valueOf(item.fsize));
                        logger.debug(item.mimeType);
                        logger.debug(String.valueOf(item.putTime));
                        logger.debug(item.endUser);

                        String httpString = prefixHttp + item.key;
                        logger.debug(httpString);
                        // 读取七牛OSS文件
                        InputStream inputStream = UrlDownload.httpDownload(httpString);
                        aliyunOSSAPI.updateFile(null,inputStream, item.key, item.mimeType);
                    }
                }
                return true;
            }
            logger.debug("fileListIterator 为空");
            return false;
        }
    }

