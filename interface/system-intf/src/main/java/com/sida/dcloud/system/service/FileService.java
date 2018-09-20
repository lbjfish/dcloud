package com.sida.dcloud.system.service;

import com.sida.dcloud.auth.vo.FileKeysDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileService {

    public Map<String, String> getUptoken();

    public Map<String, String> getDownUrl(String picType, String style);

    public Map<String, String> getDownUrlByKey(String picName, String style);

    public Map<String, String> getUpPublicToken();

    /**
     * 上传文件
     * @param files
     * @return
     */
    Map<String,String> upload(MultipartFile files);

    /**
     * 获取文件下载路径
     * @param param
     * @return
     */
    FileKeysDTO getDownUrlByKeys(FileKeysDTO param);

    /**
     * 文件直传，返回下载路径
     * @param filePath
     * @return
     */
    Map<String,String> uploadPublic(String filePath,String fileName);

    /**
     * 文件直传，返回下载路径
     * @param filePath
     * @return
     */
    Map<String,String> uploadPublic(String filePath,String fileName,String key);

//    /**
//     * 获取文件hash值
//     * @param filePaths
//     * @return
//     */
//    Map<String,String> getFilesHash(List<String> filePaths);

    /**
     * 上传文件
     * @param files
     * @return
     */
    Map<String,String> uploadImg(List<MultipartFile> files);

    /**
     * 上传文件
     * @param files
     * @return
     */
    Map<String,String> uploadFiles(List<MultipartFile> files);
}