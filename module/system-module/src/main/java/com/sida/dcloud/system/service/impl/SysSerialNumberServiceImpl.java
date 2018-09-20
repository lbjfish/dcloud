package com.sida.dcloud.system.service.impl;


import com.sida.dcloud.system.dao.SysSerialNumberMapper;
import com.sida.dcloud.system.po.SysSerialNumber;
import com.sida.dcloud.system.service.SysSerialNumberService;
import com.sida.xiruo.common.util.Xiruo;
import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.exception.ServiceException;
import com.sida.xiruo.xframework.service.BaseServiceImpl;
import com.sida.xiruo.xframework.util.BlankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.ldap.PagedResultsControl;
import java.util.HashMap;
import java.util.Map;

@Service
public class SysSerialNumberServiceImpl extends BaseServiceImpl<SysSerialNumber> implements SysSerialNumberService {

    private static final String YYYYMMDD = "yyyyMMdd";
    private static final String YYMMDD = "yyMMdd";  //短年
    private static final String YYYYMM = "yyyyMM";

    private static final String SQL_PERIOD_INFINITE = "infinite"; //永不重置
    private static final String SQL_PERIOD_DAY = "%Y%m%d";      //按日重置流水号
    private static final String SQL_PERIOD_MONTH = "%Y%m";      //按月重置流水号
    private static final String SQL_PERIOD_YEAR = "%Y";         //按年重置流水号

    @Autowired
    private SysSerialNumberMapper sysSerialNumberMapper;

    @Override
    public IMybatisDao<SysSerialNumber> getBaseDao() {
        return sysSerialNumberMapper;
    }

    @Override
    public Long generateSerialNumberByDate(Map<String, Object> param) {
        if(BlankUtil.isNotEmpty(param)) {
            if(BlankUtil.isEmpty(param.get("param_org_id"))) {
                throw new ServiceException("0000", "分公司ID不能为空" + param);
            }
            if(BlankUtil.isEmpty(param.get("param_period_date_format"))) {
                throw new ServiceException("0000", "流水号生成周期日期格式不能为空" + param);
            }
            sysSerialNumberMapper.generateSerialNumberByDate(param);
            Object resultNumber = param.get("result");
            if (BlankUtil.isNotEmpty(resultNumber)) {
                Long serialNumber = Long.parseLong(resultNumber.toString());
                return serialNumber;
            } else {
                throw new ServiceException("0000", "没有查到流水号：" + param);
            }
        } else {
            throw new ServiceException("0000", "生成流水号参数异常：" + param);
        }
    }

    /**
     * 牌证流水号生成
     *
     * @param param
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String certSerialNumberByDate(Map<String, Object> param) {
        if(BlankUtil.isNotEmpty(param)) {
            if(BlankUtil.isEmpty(param.get("param_key"))) {
                throw new ServiceException("0000", "流水号key不能为空" + param);
            }
            if(BlankUtil.isEmpty(param.get("param_org_id"))) {
                throw new ServiceException("0000", "分公司ID不能为空" + param);
            }
            if(BlankUtil.isEmpty(param.get("param_period_date_format"))) {
                throw new ServiceException("0000", "流水号生成周期日期格式不能为空" + param);
            }
            sysSerialNumberMapper.generateSerialNumberByDate(param);
            Object resultNumber = param.get("result");
            if (BlankUtil.isNotEmpty(resultNumber)) {
                String serialNumber="";
                if(BlankUtil.isNotEmpty(param.get("length"))){
                    serialNumber = Xiruo.getFormatDateString("yyyyMMddHHmmss") + String.format("%0"+param.get("length")+"d", resultNumber);
                }else {
                    serialNumber =resultNumber+"";
                }
                return serialNumber;
            } else {
                throw new ServiceException("0000", "没有查到流水号：" + param);
            }
        } else {
            throw new ServiceException("0000", "生成流水号参数异常：" + param);
        }
    }


    /**
     * 生成机构编号
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String generateOrgCode() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("param_key", "SysOrg");
        param.put("param_org_id", 0);//机构ID默认为0,
        param.put("param_period_date_format", SQL_PERIOD_INFINITE);
        long number = generateSerialNumberByDate(param);
        long orgCode = 10000+number;
        if(orgCode > 99999) {
            throw new ServiceException("0000", "流水编号超过上限99999");
        }
        //年度8位+流水号5位
        return String.valueOf(orgCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String carSerialNumberByDate(Map<String, Object> param) {
        if(BlankUtil.isNotEmpty(param)) {
            if(BlankUtil.isEmpty(param.get("param_key"))) {
                throw new ServiceException("0000", "流水号key不能为空" + param);
            }
            if(BlankUtil.isEmpty(param.get("param_org_id"))) {
                throw new ServiceException("0000", "分公司ID不能为空" + param);
            }
            if(BlankUtil.isEmpty(param.get("param_period_date_format"))) {
                throw new ServiceException("0000", "流水号生成周期日期格式不能为空" + param);
            }
            sysSerialNumberMapper.generateSerialNumberByDate(param);
            Object resultNumber = param.get("result");
            if (BlankUtil.isNotEmpty(resultNumber)) {
                String serialNumber="";
                if(BlankUtil.isNotEmpty(param.get("length"))){
                    serialNumber = Xiruo.getFormatDateString("yyyyMMdd") + String.format("%0"+param.get("length")+"d", resultNumber);
                }else {
                    serialNumber =resultNumber+"";
                }
                return serialNumber;
            } else {
                throw new ServiceException("0000", "没有查到流水号：" + param);
            }
        } else {
            throw new ServiceException("0000", "生成流水号参数异常：" + param);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String generateExportInvoiceBatchNo(String orgId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("param_key", "MarketOrderTradeRecord_BatchNo");
        param.put("param_org_id", BlankUtil.isEmpty(orgId) ? "0":orgId);//机构ID默认为0,
        param.put("param_period_date_format", SQL_PERIOD_DAY);
        long number = generateSerialNumberByDate(param);
        String batchNo = Xiruo.getFormatDateString("yyyyMMdd") + String.format("%03d", number);
        //年度8位+流水号5位
        return String.valueOf(batchNo);
    }


    /**
     * 收据编号“，规则：驾校代码（4位-驾校信息管理内设置）+
     * 订单类型代码（其他订单=00, 学车订单=01,尾款订单=02）+YYMMDD + 当月序号（6位数）
     * @param orgId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String generateReceiptNo(String orgCode, String orgId, String orderType, String tradeType) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("param_key", "MarketOrderTradeRecord_ReceiptNo");
        param.put("param_org_id", BlankUtil.isEmpty(orgId) ? "0":orgId);//机构ID默认为0,
        param.put("param_period_date_format", SQL_PERIOD_MONTH);
        long number = generateSerialNumberByDate(param);
        String orderCode = "00";
        if("1".equals(tradeType)) {
            orderCode = "02";
        } else if("1".equals(orderType)) {
            orderCode = "01";
        } else {
            orderCode = "00";
        }
        String batchNo = orgCode + orderCode
                + Xiruo.getFormatDateString("yyMMdd")
                + String.format("%06d", number);
        //年度8位+流水号5位
        return String.valueOf(batchNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo(String orgId, String orderType) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("param_key", "MarketOrder");
        param.put("param_org_id", BlankUtil.isEmpty(orgId) ? "0":orgId);//机构ID默认为0,
        param.put("param_period_date_format", SQL_PERIOD_MONTH);
        long number = generateSerialNumberByDate(param);
        String batchNo = Xiruo.getFormatDateString("yyyyMMdd")
                + String.format("%06d", number);
        if("1".equals(orderType)) {
            batchNo = "XC"+batchNo;
        } else {
            batchNo = "QT"+batchNo;
        }
        //年度8位+流水号5位
        return batchNo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String generateTestNo() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("param_key", "Test");
        param.put("param_org_id", "0");
        param.put("param_period_date_format", SQL_PERIOD_DAY);
        long number = generateSerialNumberByDate(param);
        String testNo = Xiruo.getFormatDateString("yyyyMMdd")
                + String.format("%06d", number);
        //年度8位+流水号5位
        return testNo;
    }


}