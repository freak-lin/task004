package api;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;


import org.springframework.web.multipart.MultipartFile;
import tool.MultipartFileToFile;


public class QiniuSDK {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private String access_key;
    private String secret_key;
    private String bucketname;    //空间名
    private String key = null;    //默认不指定key的情况下，以文件内容的hash值作为文件名
    private String domainOfBucket;//下载域名
    private String callbackUrl;   //回调函数服务器
    private Auth auth;
    Configuration cfg = new Configuration(Zone.zone0());
    //密钥配置

    public QiniuSDK(String access_key, String secret_key, String bucketname,String callbackUrl,String domainOfBucket) {
        this.access_key = access_key;
        this.secret_key = secret_key;
        this.bucketname = bucketname;
        this.callbackUrl = callbackUrl;
        this.domainOfBucket = domainOfBucket;
        this.auth = Auth.create(access_key, secret_key);
    }
    ///////////////////////指定上传的Zone的信息//////////////////
    //第一种方式: 指定具体的要上传的zone
    //注：该具体指定的方式和以下自动识别的方式选择其一即可
    //要上传的空间(bucket)的存储区域为华东时
    // Zone z = Zone.zone0();
    //要上传的空间(bucket)的存储区域为华北时
    // Zone z = Zone.zone1();
    //要上传的空间(bucket)的存储区域为华南时
    // Zone z = Zone.zone2();

    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    Zone z = Zone.autoZone();
    Configuration c = new Configuration(z);

    //创建上传对象
    UploadManager uploadManager = new UploadManager(c);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    //  这里policy添加了回调服务器，设置成本机
    public String getUpToken() {
//        StringMap policy = new StringMap();
//        policy.put("callbackUrl", callbackUrl);
//        policy.put("callbackBody", "key=$(key)&hash=$(hash)&fname=$(fname)&ext=$(ext)");
        return auth.uploadToken(bucketname,key);
    }
//上传本地图片
    public void upload(String filePath) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }

//    获取文件列表
    public BucketManager.FileListIterator getObjectList(String bucketname, String prefix, int limit, String delimiter) {
        return getObjectListReal(bucketname, prefix, limit, delimiter);
    }

    public BucketManager.FileListIterator getObjectList(String prefix, int limit, String delimiter) {
        return getObjectListReal(bucketname, prefix, limit, delimiter);
    }

    private BucketManager.FileListIterator getObjectListReal(String bucketname, String prefix, int limit, String delimiter) {
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth,cfg);
        if (bucketManager != null) {
            return bucketManager.createFileListIterator(bucketname, prefix, limit, delimiter);
        }
        return null;
    }
    //上传图片
    public String updateFile(MultipartFile multipartFile)throws Exception{
        //把multipartFile转成file(这种转换方式会在项目名下面生成一个文件)
        File fi = MultipartFileToFile.multipartFile(multipartFile);
        System.out.println(fi);
        Response res;
        try {
            res = uploadManager.put(fi,key,getUpToken());
            DefaultPutRet ret = res.jsonToObject(DefaultPutRet.class);
            return ret.key;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            return r.toString();
       }finally{


                //最后要删除文件
            try {
                File del = new File(fi.toURI());
                del.delete();
            } catch (Exception e) {
                System.out.println(e);
            }
            }
        }
//拼接图片URL
    public String download(String fileName) throws Exception {
        String encodedFileName = URLEncoder.encode(fileName, "utf-8");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        return finalUrl;
    }

}