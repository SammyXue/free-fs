package com.free.fs.common.template;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.free.fs.common.constant.CommonConstant;
import com.free.fs.common.exception.BusinessException;
import com.free.fs.common.properties.FsServerProperties;
import com.free.fs.common.utils.FileUtil;
import com.free.fs.model.FilePojo;
import com.obs.services.ObsClient;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.ObsObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "fs.files-server", name = "type", havingValue = "obs")
public class ObsTemplate {
    @Resource
    private FsServerProperties fileProperties;

    private ObsClient getNewClient() {
        ObsClient obsClient = new ObsClient(fileProperties.getObs().getAccessKey(), fileProperties.getObs().getSecretKey(),
                fileProperties.getObs().getEndPoint());

        return obsClient;
    }

    @SneakyThrows
    public FilePojo uploadFile(MultipartFile file) {
        ObsClient obsClient = getNewClient();
        FilePojo pojo = FileUtil.buildFilePojo(file);

        obsClient.putObject(fileProperties.getObs().getBucket(), pojo.getFileName(), file.getInputStream());

        String url = fileProperties.getObs().getUrl() + CommonConstant.DIR_SPLIT + pojo.getFileName();
        pojo.setUrl(url);
        obsClient.close();
        file.getInputStream().close();
        return pojo;
    }

    public FilePojo uploadFileSharding(MultipartFile file, HttpSession session) {
        //todo:分片上传还未实现
        throw new BusinessException("分片上传还未实现！");

    }

    @SneakyThrows
    public void deleteFile(String url) {
        ObsClient obsClient = getNewClient();
        String key = url.replaceAll(fileProperties.getObs().getUrl() + CommonConstant.DIR_SPLIT, "");
        obsClient.deleteObject(fileProperties.getObs().getBucket(), key);
        obsClient.close();

    }

    @SneakyThrows
    public void download(String url, HttpServletResponse response) {
        ObsClient obsClient = getNewClient();
        String key = url.replaceAll(fileProperties.getObs().getUrl() + CommonConstant.DIR_SPLIT, "");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(key, "UTF-8"));

        ObsObject obsObject = obsClient.getObject(fileProperties.getObs().getBucket(), key);

        InputStream input = obsObject.getObjectContent();
        byte[] b = new byte[1024];
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        int len;
        while ((len = input.read(b)) != -1) {
            bos.write(b, 0, len);
        }

        bos.close();
        input.close();
        obsClient.close();
    }


}
