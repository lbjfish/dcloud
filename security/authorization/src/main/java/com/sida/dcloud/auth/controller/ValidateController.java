package com.sida.dcloud.auth.controller;

import com.sida.dcloud.auth.util.ImageCodeGenerator;
import com.sida.dcloud.auth.validate.image.ImageCode;
import com.sida.xiruo.common.components.encrypt.Base64;
import com.sida.xiruo.xframework.cache.redis.RedisUtil;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import com.sida.xiruo.xframework.vo.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ValidateController {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/validate/code/image", method = RequestMethod.GET)
    @ApiOperation(value = "获取登录验证码")
    public void  getCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*生成图片，和图片对应的code，key*/
        ImageCode imageCode= ImageCodeGenerator.generate();
        String key = UUIDGenerate.getNextId();
        redisUtil.set(key, imageCode.getCode(), 120L);

//        ServletOutputStream outputStream = response.getOutputStream();
//        ImageIO.write(imageCode.getImage(),"JPEG", outputStream);
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType(MimeTypeUtils.IMAGE_JPEG_VALUE);
//        outputStream.flush();
//        outputStream.close();

//        /*将图片流转为base64编码进行输出*/
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(imageCode.getImage(),"JPEG", bs);
        String encoding = new String(Base64.encode(bs.toByteArray()));
        /*将key设置到相应头中*/
        response.setHeader("X_KEY", key);
//        response.setHeader("X_CODE", imageCode.getCode());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);

        Map<String, String> m  = new HashMap<>();
        m.put("key", key);
        m.put("image", "data:image/jpg;base64," + encoding);
        JsonResult jr = JsonResult.buildOk(m);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(objectMapper.writeValueAsString(jr));
        printWriter.flush();
    }
}
