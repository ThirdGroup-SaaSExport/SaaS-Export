package cn.itcast.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class UploadUtil {

    private static final String accessKey = "in1PI6uSce9R9-ZoiJZ_AjH3DRUdPwgpDh7YlDfC";
    private static final String secretKey = "fVy6qph1sENVMyW8z6A1dumUgd3hfitok-JyT3Ef";
    private static final String bucket = "heima100";
    private static final String url = "http://q08h637ux.bkt.clouddn.com/";

    public static String upload(byte[] uploadBytes){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        //            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            key = putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

        return url + key;
    }


    public static void main(String[] args) throws Exception {
        File file = new File("D:\\黑马视频\\Java课外书\\java成长IT书单.jpg");
        InputStream input = new FileInputStream(file);
        byte[] byt = new byte[input.available()];
        input.read(byt);
        System.out.println(new UploadUtil().upload(byt));
    }


}
