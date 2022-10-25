package com.Myblog.demo.util;




import com.Myblog.demo.exception.GlobalException;
import com.Myblog.demo.result.CodeMsg;
import com.qcloud.cos.*;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class COSProvider {
    // 1 初始化用户身份信息（secretId, secretKey）。
    @Value("${cosSECRETID}")
    private String secretId;
    @Value("${cosSECRETKEY}")
    private String secretKey;
    @Value("${bucketName}")
    private String bucketName; //存储桶名称，格式：BucketName-APPID
    @Value("${cos.url}")
    private String url;
    @Value("${cos.region}")
    private String cosRegion;
    // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    private Region region = new Region(cosRegion);
    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    // 从 5.6.54 版本开始，默认使用了 https
    private ClientConfig clientConfig = new ClientConfig(region);

    public String upload(InputStream fileStream, String fileName) {
        if(fileStream == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ObjectMetadata meta = new ObjectMetadata();
//         必须设置ContentLength
        try {
            meta.setContentLength(fileStream.available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		meta.setContentEncoding("UTF-8");
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        String key = UUIDUtil.uuid() + fileName;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,fileStream, meta);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
        return url+key;
    }

//    public Bucket getBucket(){
//        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
//        // 设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
//        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
//        Bucket bucketResult = null;
//        try{
//            bucketResult = cosClient.createBucket(createBucketRequest);
//        } catch (CosClientException serverException) {
//            serverException.printStackTrace();
//        }
//        return bucketResult;
//    }

}
