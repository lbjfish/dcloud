package com.sida.dcloud.operation.controller;

import com.sida.dcloud.operation.common.OperationException;
import com.sida.dcloud.operation.po.CompanyInfo;
import com.sida.dcloud.operation.service.CompanyInfoService;
import com.sida.dcloud.service.event.config.EventConstants;
import com.sida.xiruo.common.util.PinYinUtil;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.util.BlankUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("companyInfo")
@Api(description = "企业信息")
public class CompanyInfoController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyInfoController.class);

    @Autowired
    private CompanyInfoService companyInfoService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "条件查企业列表")
    public Object list(@RequestBody @ApiParam("JSON参数") CompanyInfo param) {
        Optional.ofNullable(param.getOrderField()).orElseGet(() -> {
            param.setOrderField("sort");
            param.setOrderString("asc");
            return "";
        });
        Object object = companyInfoService.findPageList(param);
        return toResult(object);
    }

    /**
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/findOne", method = RequestMethod.GET)
    @ApiOperation(value = "根据企业主键id获取信息")
    public Object findOne(@RequestParam("id") @ApiParam("id")String id) {
        CompanyInfo one = companyInfoService.selectByPrimaryKey(id);
        return toResult(one);
    }

    @RequestMapping(value = "/findMany", method = RequestMethod.GET)
    @ApiOperation(value = "根据企业主键ids获取信息")
    public Object findMany(@RequestParam("ids") @ApiParam("ids")String ids) {
        return toResult(companyInfoService.selectNamesByIds(ids));
    }

    /********************************************************************************/

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation(value = "删除企业")
    public Object remove(@RequestBody @ApiParam("企业ids") List<String> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new OperationException("没有指定要删除的企业");
        }
        companyInfoService.deleteByPrimaryKeys(String.join(",", ids));
        return toResult();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增企业")
    public Object insert(@RequestBody @ApiParam("企业信息JSON") CompanyInfo param) {
        checkForm(param, EventConstants.EVENT_INSERT);
        return toResult(companyInfoService.insert(param));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新企业")
    public Object update(@RequestBody @ApiParam("企业信息JSON") CompanyInfo param) {
        checkForm(param, EventConstants.EVENT_UPDATE);
        return toResult(companyInfoService.updateByPrimaryKey(param));
    }

    @RequestMapping(value = "/increaseFieldCount", method = RequestMethod.GET)
    @ApiOperation(value = "字段值增长（如：点赞，评论，收藏等）")
    public Object increaseFieldCount(@RequestParam("fieldName") @ApiParam("字段名") String fieldName,
                                     @RequestParam("id") @ApiParam("企业id") String id,
                                     @RequestParam("count") @ApiParam("数量") int count) {
        companyInfoService.increaseFieldCount(fieldName, id, count);
        return toResult();
    }

    private void checkForm(CompanyInfo param, int event) {
        checkIdEmpty(param, event);

        Optional.ofNullable(param.getName()).orElseThrow(() ->new OperationException("企业名称不能空"));
        Optional.ofNullable(param.getRegionId()).orElseThrow(() ->new OperationException("企业所在地区不能空"));
        Optional.ofNullable(param.getAddress()).orElseThrow(() ->new OperationException("企业地址不能空"));

        fillDefaultFields(param, event);
        if(EventConstants.EVENT_INSERT == event) {
            param.setSort(0);
        }
        //英文名称为空则使用拼音填充
        if(BlankUtil.isNotEmpty(param.getEngName())) {
            param.setEngName(PinYinUtil.getFullSpell(param.getName()));
        }
        //英文简称为空则使用拼音首字母填充
        if(BlankUtil.isNotEmpty(param.getEngShortName())) {
            param.setEngName(PinYinUtil.getFirstSpell(param.getName()).toUpperCase());
        }
    }
}