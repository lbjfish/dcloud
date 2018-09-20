package com.sida.xiruo.xframework.util;

import com.sida.xiruo.common.components.StringUtils;
import com.sida.xiruo.xframework.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表单检验工具
 * Created by huangbaidong
 * 2017/5/16.
 */
public class FormCheckUtil {

    /**
     * 检查必填项属性值
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkRequiredField(Object fieldValue, String fieldName) {
        if(BlankUtil.isEmpty(fieldValue)) {
            throw new ServiceException(fieldName+"不能为空");
        }
    }

    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, Long fieldValue, String fieldName, Long min, Long max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ServiceException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ServiceException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, Integer fieldValue, String fieldName, Integer min, Integer max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ServiceException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ServiceException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, Double fieldValue, String fieldName, Double min, Double max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ServiceException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ServiceException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, BigDecimal fieldValue, String fieldName, BigDecimal min, BigDecimal max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ServiceException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ServiceException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void checkLength(boolean checkRequiredField, String fieldValue, String fieldName, Integer min, Integer max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if (min != null) {
                if (fieldValue.length() < min) {
                    throw new ServiceException(fieldName + "长度不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.length() > max) {
                    throw new ServiceException(fieldName + "长度不能大于" + max);
                }
            }
        }
    }

    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void checkLengthForChinese(boolean checkRequiredField, String fieldValue, String fieldName, Integer min, Integer max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if (min != null) {
                if (StringUtils.chineseLength(fieldValue) < min) {
                    throw new ServiceException(fieldName + "长度不能小于" + min);
                }
            }
            if (max != null) {
                if (StringUtils.chineseLength(fieldValue) > max) {
                    throw new ServiceException(fieldName + "长度不能大于" + max);
                }
            }
        }
    }


    /**
     * 日期时间格式检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkDatetime(boolean checkRequiredField, String fieldValue, String fieldName) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(!Pattern.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", fieldValue)) {
                throw new ServiceException(fieldName + "非法的日期时间格式："+fieldValue+"，正确格式：2017-10-10 09:00:00");
            }
        }
    }

    /**
     * 日期时间格式检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkDatetimeByFormat(boolean checkRequiredField, String fieldValue, String fieldName,String format) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            Pattern p;
            if (BlankUtil.isEmpty(format)){
                p = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
            }else {
                p=Pattern.compile(format);
            }
            Matcher m = p.matcher(fieldValue);
            if(!m.matches()) {
                if(BlankUtil.isEmpty(format)){
                    throw new ServiceException(fieldName + "非法的日期时间格式："+fieldValue+"，正确格式："+"2017-10-25 09:59:47");
                }else {
                    throw new ServiceException(fieldName + "非法的日期时间格式："+fieldValue+"，正确格式："+format);
                }
            }
        }
    }

    /**
     * 日期格式检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkDate(boolean checkRequiredField, String fieldValue, String fieldName) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", fieldValue)) {
                throw new ServiceException(fieldName + "非法的日期格式："+fieldValue+"，正确格式：2017-09-11");
            }
        }
    }

    /**
     * 时间格式检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkTime(boolean checkRequiredField, String fieldValue, String fieldName) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(!Pattern.matches("\\d{2}:\\d{2}:\\d{2}", fieldValue)) {
                throw new ServiceException(fieldName + "非法的时间格式："+fieldValue+"，正确格式：09:00:00");
            }
        }
    }

    /**
     * 数字检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkNumber(boolean checkRequiredField, String fieldValue, String fieldName) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(!Pattern.matches("^(-?\\d+)(\\.\\d+)?$", fieldValue)) {
                throw new ServiceException(fieldName + "不是有效的数字类型");
            }
        }
    }


    /**
     * 检查数字的合法性（带精度校验）
     * @param fieldValue
     * @param fieldName
     * @param after
     * @param before
     */
    public static void checkNumberWithinLimit(boolean checkRequiredField, BigDecimal fieldValue, String fieldName, int before, int after) {
        checkNumberWithinLimit(checkRequiredField, String.valueOf(fieldValue), fieldName, before, after);
    }

    /**
     * 检查数字的合法性（带精度校验）
     * @param fieldValue
     * @param fieldName
     * @param after
     * @param before
     */
    public static void checkNumberWithinLimit(boolean checkRequiredField, String fieldValue, String fieldName, int before, int after) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if (!Pattern.matches("^\\d{1," + before + "}(\\.\\d{1,"+after+"})?$", fieldValue)) {
                throw new ServiceException(fieldName + "不是有效的数字类型：整数位不能超过"+before+"位，小数位不能超过"+after+"位！");
            }
        }
    }

    /**
     * 检查整数的合法性
     * @param checkRequiredField
     * @param fieldValue
     * @param fieldName
     * @param min
     * @param max
     */
    public static void checkIntWithinLimit(boolean checkRequiredField, String fieldValue, String fieldName, int min, int max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            try {
                int intValue = Integer.parseInt(fieldValue);
                if (intValue < min) {
                    throw new ServiceException(fieldName + "不能小于" + min + "！");
                }
                if (intValue > max) {
                    throw new ServiceException(fieldName + "不能大于" + max + "！");
                }
            } catch(Exception e) {
                throw new ServiceException(fieldName + "不是合法的整数！");
            }
        }
    }

    /**
     * 检查整数的合法性
     * @param checkRequiredField
     * @param fieldValue
     * @param fieldName
     * @param min
     * @param max
     */
    public static void checkIntWithinLimit(boolean checkRequiredField, Integer fieldValue, String fieldName, int min, int max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(fieldValue < min) {
                throw new ServiceException(fieldName + "不能小于"+min+"！");
            }
            if(fieldValue > max) {
                throw new ServiceException(fieldName + "不能大于"+max+"！");
            }
        }
    }

    /**
     * 数字检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkMobile(boolean checkRequiredField, String fieldValue, String fieldName) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(!Pattern.matches("1\\d{10}", fieldValue)) {
                throw new ServiceException(fieldName + "不是有效的手机号码");
            }
        }
    }

    /**
     * 身份证号检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkIdNum(boolean checkRequiredField, String fieldValue, String fieldName) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(!Pattern.matches("/(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)/", fieldValue)) {
                throw new ServiceException(fieldName + "不是有效的身份证号码");
            }
        }
    }

    /**
     * 检查是否英文|中文|数字
     * @param checkRequiredField
     * @param fieldValue
     * @param fieldName
     * @param type 1英文，2中文，3数字，4，英文+中文， 5，英文+数字，6，英文+中文+数字
     */
    public static void checkEnglishChineseNumber(boolean checkRequiredField, String fieldValue, String fieldName, int type) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(type == 6) {
                if (!Pattern.matches("^([0-9]|[A-Za-z]|[\u4E00-\u9FA5])+$", fieldValue)) {
                    throw new ServiceException(fieldName + "只能包含中文、英文、数字");
                }
            } else if(type == 5) {
                if (!Pattern.matches("^([0-9]|[A-Za-z])+$", fieldValue)) {
                    throw new ServiceException(fieldName + "只能包含英文、数字");
                }
            } else if(type == 4) {
                if (!Pattern.matches("^([A-Za-z]|[\u4E00-\u9FA5])+$", fieldValue)) {
                    throw new ServiceException(fieldName + "只能包含中文、英文");
                }
            } else if(type == 3) {
                if (!Pattern.matches("^[0-9]+$", fieldValue)) {
                    throw new ServiceException(fieldName + "不是有效的数字");
                }
            } else if(type == 2) {
                if (!Pattern.matches("^([\u4E00-\u9FA5])+$", fieldValue)) {
                    throw new ServiceException(fieldName + "不是有效的中文");
                }
            } else if(type == 1) {
                if (!Pattern.matches("^([A-Za-z])+$", fieldValue)) {
                    throw new ServiceException(fieldName + "只能包含字母A-Za-z");
                }
            }
        }
    }

    /**
     * 检查值是在有效选项范围内
     * @param checkRequiredField
     * @param fieldValue
     * @param fieldName
     * @param options
     */
    public static void checkInOptions(boolean checkRequiredField, String fieldValue, String fieldName, Collection<String> options) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(BlankUtil.isNotEmpty(options) && !options.contains(fieldValue)) {
                throw new ServiceException(fieldName + "不存在："+fieldValue);
            }
        }
    }

    /**
     * 检查值是在有效选项范围内
     * @param checkRequiredField
     * @param fieldValue
     * @param fieldName
     * @param options
     */
    public static void checkInOptions(boolean checkRequiredField, Integer fieldValue, String fieldName, Collection<Integer> options) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if(BlankUtil.isNotEmpty(options) && !options.contains(fieldValue)) {
                throw new ServiceException(fieldName + "不存在："+fieldValue);
            }
        }
    }


    public static void main(String[] args) {
        /*checkDate(true, "2017-10-10", "日期");
        checkTime(true, "09:00:01", "时间");
        checkDatetime(true, "2017-10-10 09:00:01", "日期时间");
        checkNumber(true, "-2.34", "金额");*/
        /*List<String> options = new ArrayList<>();
        options.add("A");
        options.add("B");
        options.add("C");
        checkInOptions(false, "F", "选择题", options);*/

        checkMobile(false, "13800001111", "手机号码");
    }

}
