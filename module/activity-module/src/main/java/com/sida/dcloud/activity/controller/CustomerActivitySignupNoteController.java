package com.sida.dcloud.activity.controller;

import com.sida.dcloud.activity.common.ActivityException;
import com.sida.dcloud.activity.dto.ActivitySignupNoteDto;
import com.sida.dcloud.activity.po.ActivityOrder;
import com.sida.dcloud.activity.po.CustomerActivitySignupNote;
import com.sida.dcloud.activity.service.CustomerActivitySignupNoteService;
import com.sida.dcloud.activity.vo.CustomerActivitySignupNoteVo;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.xframework.common.Contants;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import com.sida.xiruo.xframework.util.UUIDGenerate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scala.annotation.meta.param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("customerActivitySignupNote")
@Api(description = "活动报名表")
public class CustomerActivitySignupNoteController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerActivitySignupNoteController.class);

    @Autowired
    private CustomerActivitySignupNoteService customerActivitySignupNoteService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查报名列表")
    public Object list(@RequestBody @ApiParam("JSON参数") CustomerActivitySignupNoteVo param) {
        param.setUserId(LoginManager.getCurrentUserId());
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("start_time");
            param.setOrderString("desc");
            return "";
        });
        Object object = customerActivitySignupNoteService.findPageList(param);
        return toResult(object);
    }

    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据报名主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        CustomerActivitySignupNote one = customerActivitySignupNoteService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findOneToClient", method = RequestMethod.GET)
    @ApiOperation(value = "根据报名主键id获取信息 - C端使用")
    public Object findOneToClient(@RequestParam("id") @ApiParam("id")String id) {
        return toResult(customerActivitySignupNoteService.findOneToClient(id));
    }

    @RequestMapping(value = "/findSimpleOneToClient", method = RequestMethod.GET)
    @ApiOperation(value = "根据报名主键id获取简易信息 - C端使用")
    public Object findSimpleOneToClient(@RequestParam("id") @ApiParam("id")String id) {
        return toResult(customerActivitySignupNoteService.findSimpleOneToClient(id));
    }
    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除报名")
    public Object remove(@RequestBody @ApiParam("报名ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new ActivityException("没有指定要删除的报名");
        }
        customerActivitySignupNoteService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增报名")
    public Object insert(@RequestBody @ApiParam("报名信息JSON") CustomerActivitySignupNote param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        customerActivitySignupNoteService.insert(param);
        return toResult();
    }

    @RequestMapping(value = "/insertSignupNoteAndOrder", method = RequestMethod.POST)
    @ApiOperation(value = "新增报名表和订单")
    public Object insertSignupNoteAndOrder(@RequestBody @ApiParam("报名表和订单") ActivitySignupNoteDto param) {
        checkMixForm(param, EventConstants.EVENT_INSERT);
        return toResult(customerActivitySignupNoteService.insertSignupNoteAndOrder(param));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新报名")
    public Object update(@RequestBody @ApiParam("报名信息JSON") CustomerActivitySignupNote param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        customerActivitySignupNoteService.updateByPrimaryKey(param);
        return toResult();
    }

    private void checkMixForm(ActivitySignupNoteDto param, int event) {
        if(Contants.EVENT_UPDATE == event) {
            Optional.ofNullable(param.getCustomerActivitySignupNote()).map(CustomerActivitySignupNote::getId).orElseThrow(() -> new RuntimeException("更新操作时主键不能空"));
            Optional.ofNullable(param.getActivityOrder()).map(ActivityOrder::getId).orElseThrow(() -> new RuntimeException("更新操作时主键不能空"));
            if(!LoginManager.getCurrentUserId().equals(param.getUserId())) {
                throw new RuntimeException(String.format("不能修改其他人的报名表，登录用户[%s]，修改用户[%s]", LoginManager.getCurrentUserId(), param.getUserId()));
            }
        }
        Optional.ofNullable(param.getUserId()).orElseThrow(() ->new ActivityException("用户id不能空"));
        Optional.ofNullable(param.getActivityId()).orElseThrow(() ->new ActivityException("活动id不能空"));
        Optional.ofNullable(param.getSettingVersion()).orElseThrow(() ->new ActivityException("报名表设置版本不能空"));
        param.getCustomerActivitySignupNote().setId(UUIDGenerate.getNextId());
        param.getCustomerActivitySignupNote().setActivityId(param.getActivityId());
        param.getCustomerActivitySignupNote().setUserId(param.getUserId());
        param.getCustomerActivitySignupNote().setVersion(param.getSettingVersion());
        param.getCustomerActivitySignupNote().setDeleteFlag(false);
        param.getCustomerActivitySignupNote().setDisable(false);
        param.getCustomerActivitySignupNote().setSentStatus(false);
        param.getCustomerActivitySignupNote().setSignupTime(new Date());
        param.getActivityOrder().setId(UUIDGenerate.getNextId());
        param.getActivityOrder().setActivityId(param.getActivityId());
        param.getActivityOrder().setOrderUser(param.getUserId());
        param.getActivityOrder().setNoteId(param.getCustomerActivitySignupNote().getId());
        param.getActivityOrder().setDeleteFlag(false);
        param.getActivityOrder().setDisable(false);
        param.getActivityOrder().setCreateTime(param.getCustomerActivitySignupNote().getSignupTime());
    }

    private void checkForm(CustomerActivitySignupNote param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getUserId()).orElseThrow(() ->new ActivityException("用户不能空"));
        Optional.ofNullable(param.getVersion()).orElseThrow(() ->new ActivityException("版本号不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
            param.setSentStatus(false);
            param.setSignupTime(param.getCreatedAt());
        }
    }
}