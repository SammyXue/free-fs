package com.free.fs.service.impl;

import com.free.fs.common.template.ObsTemplate;
import com.free.fs.model.FilePojo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@ConditionalOnProperty(prefix = "fs.files-server", name = "type", havingValue = "obs")
public class ObsService extends AbstractIFileService {
    @Resource
    private ObsTemplate obsTemplate;

    @Override
    protected FilePojo uploadFile(MultipartFile file) {
        return obsTemplate.uploadFile(file);
    }

    @Override
    protected FilePojo uploadFileSharding(MultipartFile file, HttpSession session) {
        return obsTemplate.uploadFileSharding(file, session);
    }

    @Override
    protected void deleteFile(String url) {
        obsTemplate.deleteFile(url);
    }

    @Override
    public void download(String url, HttpServletResponse response) {
        obsTemplate.download(url, response);
    }
}
