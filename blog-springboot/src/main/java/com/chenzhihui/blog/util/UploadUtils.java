package com.chenzhihui.blog.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件上传工具
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/18 08:55
 **/
public class UploadUtils {


    /**
     * 设置固定常量
     */
    public static final int APP_ID = 1;
    public static final String SECRET_ID = "AKIDTo266yd4IJAlBSYWCtcAdw20tcBoMtPj";
    public static final String SECRET_KEY = "GJKHJ1GTcccYYUIuMdtsrgMsENZfjfPE";
    public static final String BUCKETNAME = "czh-1314123957";
    public static final String URL = "https://czh-1314123957.cos.ap-guangzhou.myqcloud.com";

    public static void main(String[] args) {
        uploadFile();
    }
    /**
     * 上传文件
     */
    public static void uploadFile() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2 设置bucket的地域简称(参照下图)
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = BUCKETNAME;
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);
        //cos文件路径
        String key = "/usr/local/upload/";
        //本地文件路径
        String fileUrl="***********";
        File localFile = new File(fileUrl);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        try {
            // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
            Upload upload = transferManager.upload(putObjectRequest);
            showTransferProgress(upload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        transferManager.shutdownNow();
        cosclient.shutdown();
    }
    private static void showTransferProgress(Transfer transfer) {
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
        } while (transfer.isDone() == false);
    }
}


