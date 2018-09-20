package com.sida.dcloud.system.service;

import com.sida.dcloud.system.po.SysSerialNumber;
import com.sida.xiruo.xframework.service.IBaseService;
import java.util.Map;

public interface SysSerialNumberService extends IBaseService<SysSerialNumber> {

    /**
     * 通过存储过程生成流水号（按日期重置流水号）
     * @param param
     * @return
     */
    Long generateSerialNumberByDate(Map<String, Object> param);

    /**
     *牌证流水号生成
     * @param param
     */
    String certSerialNumberByDate(Map<String, Object> param);

    /**
     * 生成机构编号
     * @return
     */
    String generateOrgCode();

    /**
     * 车务流水号生成
     * @param param
     * @return
     */
    String carSerialNumberByDate(Map<String, Object> param);

    /**
     * 财务导出打发票批次号
     * @param orgId
     * @return
     */
    String generateExportInvoiceBatchNo(String orgId);

    /**
     * 营销生成订单收据编号
     * @param orgId
     * @return
     */
    String generateReceiptNo(String orgCode, String orgId, String orderType, String tradeType);

    /**
     * 生成订单号
     * @param orgId
     * @return
     */
    String generateOrderNo(String orgId, String orderType);

    /**
     * 测试用编号
     * @return
     */
    String generateTestNo();

}