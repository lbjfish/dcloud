package com.sida.dcloud.system.controller;

import com.sida.dcloud.auth.vo.FileKeysDTO;
import com.sida.dcloud.system.service.FileService;
import com.sida.dcloud.system.service.SysAttachmentService;
import com.sida.xiruo.xframework.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sysAttachment")
@Api("附件上传相关接口")
public class SysAttachmentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysAttachmentController.class);

    @Autowired
    private SysAttachmentService sysAttachmentService;
    @Autowired
    private FileService fileService;

    /**
     * 获取上传照片的上传令牌
     * @return
     */
    @RequestMapping(value = "/upToken", method = RequestMethod.GET)
    public Object getUptoken() {
        Map<String,String> map = fileService.getUptoken();
        return toResult(map);
    }

    @RequestMapping(value = "/xxx", method = RequestMethod.GET)
    public Object testFileCreate(HttpServletRequest request) {
        Map<String,String> map = new HashMap<>();
        map.put("user.dir========",System.getProperty("user.dir"));
        System.out.println("user.dir========"+System.getProperty("user.dir"));

        File directory = new File("abc");
        try {
            map.put("new file canonicalPath=======",directory.getCanonicalPath());
            System.out.println("new file canonicalPath======="+directory.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("new file absolutePath=======",directory.getAbsolutePath());
        map.put("new file path=======",directory.getPath());
        map.put("classLoader=======",Class.class.getClass().getResource("/").getPath());

        System.out.println("new file："+directory.getAbsolutePath());//得到的是C:/test/abc 
        System.out.println("new file："+directory.getPath());//得到的是abc 
        System.out.println("classLoader："+Class.class.getClass().getResource("/").getPath());

        return toResult(map);
    }

    /**
     * 获取公共上传照片的上传令牌
     * @return
     */
    @RequestMapping(value = "/upPublicToken", method = RequestMethod.GET)
    public Object getUpPublicToken() {
        Map<String,String> map = fileService.getUpPublicToken();
        return toResult(map);
    }

    /**
     * 根据业务数据 获取对应文件
     * @param picType
     * @param style 图片样式，如：imageView2/1/w/200/h/200
     * @return
     */
    @RequestMapping(value = "/downUrl", method = RequestMethod.GET)
    public Object getDowntoken(@RequestParam String picType, @RequestParam(required=false) String style) {
        Map<String,String> map = fileService.getDownUrl(picType, style);
        return toResult(map);
    }

    /**
     * 根据文件名获取图片下载地址
     * @param picName
     * @param style	图片样式，如：imageView2/1/w/200/h/200
     * @return
     */
    @RequestMapping(value = "/downUrlByKey", method = RequestMethod.GET)
    public Object downUrlByKey(@RequestParam String picName, @RequestParam(required=false) String style) {
        Map<String,String> map = fileService.getDownUrlByKey(picName, style);
        return toResult(map);
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件")
    public Object upload(@RequestParam("file") MultipartFile file) {
        Map<String,String> map = fileService.upload(file);
        return toResult(map);
    }

    @RequestMapping(value = "/uploadPublic", method = RequestMethod.POST)
    @ApiOperation(value = "上传共用文件")
    public Object uploadPublic(@RequestParam("文件本地路径，如 D:\\\\qiniu\\\\img.jpg") String filePath,
                               @RequestParam("文件名，带文件名后缀如：img.jpg") String fileName) {
        Map<String,String> map = fileService.uploadPublic(filePath,fileName);
        return toResult(map);
    }

    @RequestMapping(value = "/getDownUrlByKeys", method = RequestMethod.POST)
    @ApiOperation(value = "获取文件下载路径")
    public Object getDownUrlByKeys(@RequestBody FileKeysDTO param) {
        Object object = fileService.getDownUrlByKeys(param);
        return toResult(object);
    }

//    @RequestMapping(value = "/getFilesHash", method = RequestMethod.POST)
//    @ApiOperation(value = "获取文件hash值")
//    public Object getFilesHash(@RequestBody List<String> filePaths) {
//        Object object = fileService.getFilesHash(filePaths);
//        return toResult(object);
//    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ApiOperation(value = "上传图片到七牛")
    public Object uploadImg(@RequestParam("file") List<MultipartFile> files) {
        Map<String,String> map = fileService.uploadImg(files);
        return toResult(map);
    }


    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ApiOperation(value = "本地文件夹文件批量上传")
    public Object uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        Map<String,String> map = fileService.uploadFiles(files);
        return toResult(map);
    }

}