package com.steven.AIAnswering.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.steven.AIAnswering.config.CosClientConfig;
import java.io.File;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Cos Object storage operation
 *
 */
@Component
public class CosManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    /**
     * Upload object
     *
     * @param key
     * @param localFilePath
     * @return
     */
    public PutObjectResult putObject(String key, String localFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                new File(localFilePath));
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * Upload object
     *
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }
}
