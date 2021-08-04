package com.free.fs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.free.fs.common.utils.R;
import com.free.fs.model.Dtree;
import com.free.fs.model.FilePojo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 文件管理接口
 *
 * @author dinghao
 * @date 2021/3/15
 */
public interface FileService extends IService<FilePojo> {

    /**
     * 获取文件列表
     *
     * @param pojo
     * @return
     */
    List<FilePojo> getList(FilePojo pojo);

    /**
     * 获取文件树结构列表
     *
     * @param pojo
     * @return
     */
    List<Dtree> getTreeList(FilePojo pojo);

    /**
     * 获取目录树结构列表
     *
     * @param pojo
     * @return
     */
    List<Dtree> getDirTreeList(FilePojo pojo);

    /**
     * 上传文件
     *
     * @param files
     * @param dirIds
     * @return
     */
    R upload(MultipartFile[] files, String dirIds);

    /**
     * 通过产品类型、口味、批号来上传报告
     *
     * @param files
     * @param type   类型
     * @param flavor 口味
     * @param date   日期
     * @return
     */
    R standardUpload(MultipartFile[] files, String product, String flavor, String date);

    /**
     * 分片上传大文件
     *
     * @param files
     * @return
     */
    R uploadSharding(MultipartFile[] files,String dirIds, HttpSession session);

    /**
     * 删除文件
     *
     * @param url
     * @return
     */
    boolean delete(String url);

    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    void download(String url, HttpServletResponse response);

    /**
     * 新增文件夹
     *
     * @param pojo
     * @return
     */
    boolean addFolder(FilePojo pojo);

    /**
     * 重命名
     *
     * @param pojo
     * @return
     */
    boolean updateByName(FilePojo pojo);

    /**
     * 根据id集合批量删除
     *
     * @param id
     * @return
     */
    boolean deleteByIds(Long id);

    /**
     * 移动文件
     *
     * @param ids
     * @param parentId
     * @return
     */
    boolean move(String ids, Long parentId);

    /**
     * 根据id拼接父目录
     *
     * @param id
     * @return
     */
    Map<String, Object> getDirs(Long id);
}
