package com.sida.xiruo.common.util;

import java.util.HashMap;
import java.util.Map;

public class MedicineNameMap {
	public static Map<String, String> nameMap = null ; 
	public static String getMedicineName(){
		String name = 
			"序号,orderNum;"+
			"目录编号,catalogueNum;"+
			"药品分类,medicinalClass;"+
			"系统匹配名,SysMatchName;"+
			"通用名,commonName;"+
			"剂型分类,preparClass;"+
			"剂型,preparType;"+
			"剂型备注,preparDesc;"+
			"规格,standard;"+
			"规格包装,standardAndPack;"+
			"详细规格包装,standardAndPackDesc;"+
			"最小使用单位,minUseUnit;"+
			"单位,unit;"+
			"数量,count;"+
			"中大包装,maxPack;"+
			"附加装置,addUnit;"+
			"包装材质,packMaterial;"+
			"成分,medicineIngredients;"+
			"拆分类型,splitType;"+
			"质量层次,qualityLevel;"+
			"商品备注,goodsDesc;"+
			"审核状态,auditState;"+
			"产品分数,productScore;"+
			"生产企业,productionCompany;"+
			"Ͷ投标企业,tenderCompany;"+
			"企业备注,companyDesc;"+
			"企业编号,companyNum;"+
			"企业排名,companyRanking;"+
			"企业分数,companyScore;"+
			"行业排名,industryRanking;"+
			"投标价,tenderPrice;"+
			"最小单位投标价,minUnitTenderPrice;"+
			"限价,limitedPrice;"+
			"报价,offerPrice;"+
			"拟中标价,imitAwardPrice;"+
			"中标价,awardPrice;"+
			"最小单位限价,minlimitedPrice;"+
			"最小单位报价,minOfferPrice;"+
			"最小单位拟中标价,minUnitImitAwardPrice;"+
			"最小单位报价,minUnitOfferPrice;"+
			"差比折算包装价,packPrice;"+
			"附加装置报价,addUnitPrice;"+
			"医院零售价,hospitalRetailPrice;"+
			"政府最高零售价,governmentMaxRetailPrice;"+
			"最小单位零售价,minUnitRetailPrice;"+
			"中标区域,arradArea;"+
			"区域数量,areaCount;"+
			"药品有效期,effectiveDate";
		return name;
	}
	
	/**
	 * 初始化map标准名与字段
	 * @return
	 */
	public static String getMedicineNameMap (String cname){
		String name = getMedicineName();
		String[] nameArrs = name.split(";");
		nameMap = new  HashMap<String, String>() ;
		for (int i = 0; i < nameArrs.length; i++) {
			String[] nameArr = nameArrs[i].split(",");
			nameMap.put(nameArr[0], nameArr[1]);
		}
		return nameMap.get(cname);
	}

}
