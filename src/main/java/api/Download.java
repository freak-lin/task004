package api;

import java.net.URLEncoder;


public class Download {

    public static void main(String args[])throws Exception {
        new Download().download();
    }

    public void download() throws Exception{
        String fileName = "FlQpWrjhvWmVaGM0vkDw9YIuXeHA";
        String domainOfBucket = "paln96qj1.bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(fileName,"utf-8");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        System.out.println(finalUrl);
    }
}