package com.sida.dcloud.training.controller;

import com.sida.dcloud.training.common.TrainingException;
import com.sida.dcloud.training.event.dispatcher.FavoriteNoteDispatcher;
import com.sida.dcloud.training.po.FavoriteNote;
import com.sida.dcloud.training.service.FavoriteNoteService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("favoriteNote")
@Api(description = "收藏记录记录")
public class FavoriteNoteController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteNoteController.class);

    @Autowired
    private FavoriteNoteDispatcher favoriteNoteDispatcher;
    @Autowired
    private FavoriteNoteService favoriteNoteService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查收藏记录列表")
    public Object list(@RequestBody @ApiParam("JSON参数") FavoriteNote param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("thatTime");
            param.setOrderString("desc");
            return "";
        });
        Object object = favoriteNoteService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/listByUser", method = RequestMethod.POST)
    @ApiOperation(value = "根据用户收藏记录列表")
    public Object listByUser() {
        FavoriteNote param = new FavoriteNote();
        param.setUserId(LoginManager.getCurrentUserId());
        param.setOrderField("thatTime");
        param.setOrderString("desc");
        Object object = favoriteNoteService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.POST)
    @ApiOperation(value = "根据收藏记录主键id获取信息")
    public Object findOne(@RequestBody @ApiParam("id")String id) {
        FavoriteNote one = favoriteNoteService.selectByPrimaryKey(id);
        return toResult(one);
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "用户取消收藏")
    public Object remove(@RequestBody @ApiParam("JSON参数") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new TrainingException("没有指定要删除的项目");
        }
        favoriteNoteDispatcher.remove(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insertByUser", method = RequestMethod.POST)
    @ApiOperation(value = "用户新增收藏记录")
    public Object insertByUser(@RequestBody @ApiParam("习题id") String exerciseId) {
        FavoriteNote param = new FavoriteNote();
        param.setExerciseId(exerciseId);
        param.setUserId(LoginManager.getCurrentUserId());
        param.setThatTime(new Date());
        favoriteNoteDispatcher.insert(param);
        return toResult();
    }
}