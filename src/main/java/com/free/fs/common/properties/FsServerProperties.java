package com.free.fs.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * fs上传配置资源类
 *
 * @author dinghao
 * @date 2021/3/10
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "fs.files-server")
public class FsServerProperties {

    /**
     * 自动化配置
     * type：oss or qiniu
     */
    private String type;

    /**
     * 七牛配置
     */
    QiniuProperties qiniu = new QiniuProperties();

    /**
     * oss配置
     */
    OssProperties oss = new OssProperties();
}