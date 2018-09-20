package com.sida.dcloud.system.controller;

import com.sida.dcloud.system.service.SysSerialNumberService;
import com.sida.xiruo.xframework.controller.BaseController;
import com.sida.xiruo.xframework.controller.LoginManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sysSerialNumber")
@Api(description = "流水号生成Api")
public class SysSerialNumberController extends BaseController {

    @Autowired
    private SysSerialNumberService sysSerialNumberService;

    @RequestMapping(value = "/generateOrgCode", method = RequestMethod.GET)
    @ApiOperation(value = "生成机构编号")
    public String generateOrgCode( ){
        return sysSerialNumberService.generateOrgCode();
    }

    @RequestMapping(value = "/generateCertApplyCode", method = RequestMethod.POST)
    @ApiOperation(value = "牌证流程申请单号")
    public Object generateCertApplyCode(@RequestBody Map<String, Object> param){
        String serialNumber=sysSerialNumberService.certSerialNumberByDate(param);
        Map<String,Object> map=new HashMap<>();
        map.put("serialNumber",serialNumber);
        return toResult(map);
    }

    @RequestMapping(value = "/generateCarApplyCode", method = RequestMethod.POST)
    @ApiOperation(value = "车务相关业务单号")
    public Object generateCarApplyCode(@RequestBody Map<String, Object> param){
        String serialNumber=sysSerialNumberService.carSerialNumberByDate(param);
        Map<String,Object> map=new HashMap<>();
        map.put("serialNumber",serialNumber);
        return toResult(map);
    }

    @RequestMapping(value = "/generateExportInvoiceBatchNo", method = RequestMethod.GET)
    @ApiOperation(value = "导出打发票批次号")
    public Object generateExportInvoiceBatchNo(){
        String orgId = LoginManager.getCurrentOrgId();
        String batchNo = sysSerialNumberService.generateExportInvoiceBatchNo(orgId);
        return toResult(batchNo);
    }


    @RequestMapping(value = "/generateReceiptNo", method = RequestMethod.GET)
    @ApiOperation(value = "收据编号")
    public Object generateReceiptNo(@ApiParam("订单类型") String orderType, @ApiParam("收款类型:0收款，1尾款") String tradeType) {
        String orgId = LoginManager.getCurrentOrgId();
        String receiptNo = sysSerialNumberService.generateReceiptNo("SZSG", orgId, orderType, tradeType);
        return toResult(receiptNo);
    }


    @RequestMapping(value = "/generateOrderNo", method = RequestMethod.GET)
    @ApiOperation(value = "订单编号")
    public Object generateOrderNo(@RequestParam String orderType) {
        String orgId = LoginManager.getCurrentOrgId();
        String orderNo = sysSerialNumberService.generateOrderNo(orgId, orderType);
        return toResult(orderNo);
    }

    @RequestMapping(value = "/generateTestNo", method = RequestMethod.GET)
    @ApiOperation(value = "测试编号")
    public Object generateTestNo() {
        String testNo = sysSerialNumberService.generateTestNo();
        return toResult(testNo);
    }

}
