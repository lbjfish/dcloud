package com.sida.dcloud.system.service.impl;


import com.sida.dcloud.auth.vo.FileKeysDTO;
import com.sida.dcloud.system.po.SysAttachment;
import com.sida.dcloud.system.service.FileService;
import com.sida.dcloud.system.service.SysAttachmentService;
import com.sida.dcloud.system.util.QiniuHashUtils;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.util.BlankUtil;
import com.sida.xiruo.xframework.util.PoDefaultValueGenerator;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caojz
 * 文件服务类
 *
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${qiniu.bucket}")
    private String BUCKET;
    @Value("${qiniu.ak}")
    private String AK;
    @Value("${qiniu.sk}")
    private String SK;
    @Value("${qiniu.domain}")
    private String DOMAIN;
    @Value("${qiniu.bucketPublic}")
    private String BUCKET_PUBLIC;
    @Value("${qiniu.domainOfBucket}")
    private String domainOfBucket;
    @Value("${qiniu.defaultImg}")
    private String defaultImg;

    @Autowired
    private SysAttachmentService sysAttachmentService;

    @Override
    public Map<String, String> getUptoken() {
        Map<String,String> data = new HashMap<String, String>();
        Auth auth = Auth.create(AK, SK);
        data.put("upToken", auth.uploadToken(BUCKET));
        return data;
    }

    @Override
    public Map<String, String> getDownUrl(String picType, String style){

        //根据业务传参获取资源文件文件名
        String resoureName = defaultImg;

        //添加样式
        if(null!= style && !"".equals(style)){
            resoureName = resoureName + "?"+ style;
        }
        //构造下载地址
        String baseUrl = DOMAIN + resoureName;
        long expires = 21600; //6小时

        Auth auth = Auth.create(AK, SK);
        String downUrl = auth.privateDownloadUrl(baseUrl, expires);

        Map<String,String>data = new HashMap<String, String>();
        data.put("downUrl", downUrl);
        return data;
    }

    @Override
    public Map<String, String> getDownUrlByKey(String picName, String style){
        Map<String,String> data = new HashMap<String, String>();

        if(picName.contains("http://") || picName.contains("https://")){
            data.put("downUrl", picName);
            return data;
        }

        String resoureName = defaultImg;//默认图片

        //判断传递的picName是否为空，不为空则覆盖resoureName的值
        if(BlankUtil.isNotEmpty(picName)){
            resoureName = picName;
        }

        if(null!= style && !"".equals(style)){
            resoureName = resoureName + "?"+ style;
        }

        //构造下载地址
        String baseUrl = DOMAIN + resoureName;
        long expires = 21600; //6小时
        Auth auth = Auth.create(AK, SK);
        String downUrl = auth.privateDownloadUrl(baseUrl, expires);
        data.put("downUrl", downUrl);
        return data;
    }

    @Override
    public Map<String, String> getUpPublicToken(){
        Map<String,String>data = new HashMap<String, String>();
        Auth auth = Auth.create(AK, SK);
        data.put("upToken", auth.uploadToken(BUCKET_PUBLIC));
        return data;
    }

    @Override
    public Map<String, String> upload(MultipartFile file) {
        Map<String,String> map = new HashMap<>();

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(AK, SK);
        String upToken = auth.uploadToken(BUCKET);

        try {
            try {
                if (file != null) {
                    //取得当前上传文件的文件名称
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    final String realFolderPath = "E:\\upload";
                    final File folderFile = new File(realFolderPath);
                    if(!folderFile.exists()) {
                        folderFile.mkdirs();
                    }

                    String originalFileName = file.getOriginalFilename();
                    if (BlankUtil.isNotEmpty(originalFileName)) {
                        File localFile = new File(realFolderPath +"\\"+ originalFileName);
                        file.transferTo(localFile);
                        Response response = uploadManager.put(localFile,null,upToken);
                        //解析上传成功的结果
                        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                        map.put("key",putRet.key);
                        map.put("hash",putRet.hash);
                    }
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public FileKeysDTO getDownUrlByKeys(FileKeysDTO param) {
        FileKeysDTO dto = new FileKeysDTO();
        //1.解析入参，组装文件名集合
        List<String> fileList = Lists.newArrayList();
        if (BlankUtil.isNotEmpty(param.getFileKeyList())){
            fileList.addAll(param.getFileKeyList());
        }
        if (BlankUtil.isNotEmpty(param.getFileKeyStr())){
            if (BlankUtil.isNotEmpty(param.getSeparator())){
                Collections.addAll(fileList, param.getFileKeyStr().split(param.getSeparator()));
            }else {
                Collections.addAll(fileList, param.getFileKeyStr().split(","));
            }
        }

        //2.遍历获取下载路径
        List<String> downUrlList = Lists.newArrayList();
        Map<String,String> downUrlMap = new HashMap<>();
        String downUrlStr = "";

        if (BlankUtil.isNotEmpty(fileList)){
            for (String file : fileList){
                //添加样式
                String fileWithStyle = file;
                if(BlankUtil.isNotEmpty(param.getStyle())){
                    fileWithStyle = file + "?"+ param.getStyle();
                }
                String baseUrl = DOMAIN + fileWithStyle;
                long expires = 21600; //6小时
                Auth auth = Auth.create(AK, SK);
                String downUrl = auth.privateDownloadUrl(baseUrl, expires);
                downUrlList.add(downUrl);
                downUrlMap.put(file,downUrl);
            }
            downUrlStr = StringUtils.join(downUrlList,",");
        }
        dto.setDownUrlList(downUrlList);
        dto.setDownUrlMap(downUrlMap);
        dto.setDownUrlStr(downUrlStr);
        return dto;
    }

    @Override
    public Map<String, String> uploadPublic(String filePath,String fileName) {
        return uploadPublic(filePath,fileName,null);
    }

    @Override
    public Map<String, String> uploadPublic(String filePath, String fileName, String key) {
        Map<String, String> downloadUrlMap = new HashMap<>();

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = AK;
        String secretKey = SK;
        String bucket = BUCKET_PUBLIC;
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = filePath;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //为了保存完整的文件名信息，需要在文件上传前获取到hash
        String hash = "";
        try{
            hash = QiniuHashUtils.calcETag(filePath);
        }catch (Exception e){
            e.printStackTrace();
        }

        //如果拿hash异常，则使用uuid作为key前缀
        if (BlankUtil.isEmpty(hash)){
            hash = UUIDGenerate.getNextId();
        }

        if (BlankUtil.isNotEmpty(key)){
            //若key传入，则使用固定key
        }else {
            key = hash + "/" + fileName;
        }

        Auth auth = Auth.create(accessKey, secretKey);
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        String upToken = auth.uploadToken(bucket,key);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);

            try{
                String encodedFileName = URLEncoder.encode(putRet.key, "utf-8");
                String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
                downloadUrlMap.put("downloadUrl",finalUrl);
                return downloadUrlMap;
            }catch (Exception e){
                throw new ServiceException("获取下载路径异常，请重试！");
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    @Override
    public Map<String, String> uploadImg(List<MultipartFile> files) {
        Map<String,String> map = new HashMap<>();

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(AK, SK);
        String upToken = auth.uploadToken(BUCKET);

        try {
            try {
                for(MultipartFile file:files) {
                    if (file != null) {
                        Response response = uploadManager.put(file.getInputStream(), null, upToken, null, "");
                        //解析上传成功的结果
                        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                        map.put(putRet.key,putRet.hash);
                    }
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, String> uploadFiles(List<MultipartFile> files) {
        Map<String,String> map = new HashMap<>();
        //遍历文件、循环单个上传
        if (BlankUtil.isNotEmpty(files)){
            Auth auth = Auth.create(AK, SK);
            String upToken = auth.uploadToken(BUCKET);
            //创建上传文件夹
            final String realFolderPath = "E:\\upload";
            final File folderFile = new File(realFolderPath);
            if(!folderFile.exists()) {
                folderFile.mkdirs();
            }
            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(Zone.zone0());
            UploadManager uploadManager = new UploadManager(cfg);

            List<SysAttachment> list = Lists.newArrayList();
            List<String> fileNameList = Lists.newArrayList();
            List<String> originNameList = Lists.newArrayList();

            for (MultipartFile file : files){
                try {
                    if (file != null) {
                        String originalFileName = file.getOriginalFilename();
                        if (BlankUtil.isNotEmpty(originalFileName)) {
                            File localFile = new File(realFolderPath +"\\"+ originalFileName);
                            file.transferTo(localFile);

                            String key = "";
                            try{
                                key = QiniuHashUtils.calcETag(localFile.getAbsolutePath());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            key = key + "/" + originalFileName;

                            Response response = uploadManager.put(localFile,key,upToken);
                            //解析上传成功的结果
                            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                            SysAttachment attachment = new SysAttachment();
                            PoDefaultValueGenerator.putDefaultValue(attachment);
                            attachment.setFilePath(putRet.key);
                            attachment.setFileName(putRet.key);
                            attachment.setOriginName(originalFileName);
                            attachment.setExtension(originalFileName.substring(originalFileName.lastIndexOf(".")));
                            attachment.setSize(new BigDecimal(file.getSize()));
                            attachment.setBusinessType("PicForDataMigration");
                            attachment.setBusinessId(putRet.key);
                            fileNameList.add(attachment.getFileName());
                            originNameList.add(attachment.getOriginName());
                            list.add(attachment);
                        }
                    }
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            //考虑重复上传
            //1.同hash值同文件名不录入数据库
            //2.不同hash值但是同文件名的，保留本次内容为唯一记录
            Map<String,SysAttachment> fileNameExistMap = sysAttachmentService.findMapByFileNameList(fileNameList);
            Map<String,SysAttachment> originNameExistMap = sysAttachmentService.findMapByOriginNameList(originNameList);

            if (BlankUtil.isNotEmpty(list)){
                List<SysAttachment> insertList = Lists.newArrayList();
                for (SysAttachment po : list){
                    if (fileNameExistMap.containsKey(po.getFileName())){
                        //忽略同hash同文件名
                        continue;
                    }
                    if (originNameExistMap.containsKey(po.getOriginName())){
                        //如果同文件名，更新原纪录
                        SysAttachment update = originNameExistMap.get(po.getOriginName());
                        PoDefaultValueGenerator.putDefaultValue(update);
                        update.setFilePath(po.getFilePath());
                        update.setFileName(po.getFileName());
                        update.setOriginName(po.getOriginName());
                        update.setExtension(po.getExtension());
                        update.setSize(po.getSize());
                        update.setBusinessType(po.getBusinessType());
                        update.setBusinessId(po.getBusinessId());
                        sysAttachmentService.updateByPrimaryKeySelective(update);
                        continue;
                    }
                    insertList.add(po);
                }
                //批量插入
                sysAttachmentService.batchInsert(insertList);
            }
        }
        return map;
    }

//    public static void main(String[] args) {
//        String name = "食堂满意度调查结果汇总-wzwtest.xls";
//        System.out.println(name.substring(name.lastIndexOf(".")));
//        System.out.println(name.split("\\.")[1]);
//    }
}
