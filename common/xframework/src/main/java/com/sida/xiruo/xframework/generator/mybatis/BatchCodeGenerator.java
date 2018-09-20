package com.sida.xiruo.xframework.generator.mybatis;

import com.sida.xiruo.common.components.FileUtils;
import com.sida.xiruo.common.components.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 
* @ClassName: BatchCodeGenerator
* @Description：批量生成，目前有问题
* @date：2016年8月3日 下午11:33:56
* 修改备注：
* 序号 		修改人：		修改时间：			                备注
* 001		yjr	2016年8月30日 下午11:33:56
* @version
 */
public class BatchCodeGenerator {
    
	public static void main(String[] args) throws Exception {
		String tableStrs="bid_contract,bid_budget_price";
		String tableNames[]=tableStrs.split(",");
		String entityNames[]=new String[tableNames.length];
		String tempNntityName="";
		for (int i=0;i<tableNames.length;i++) {
			String string=tableNames[i];
			String[] str_array = string.split("_");
			for (String string2 : str_array) {
				tempNntityName+= StringUtils.firstLetterUpper(string2);
			}
			entityNames[i]=tempNntityName;
		}
		System.out.println("file generate start");
		String proertyPath="src/main/resources/bidgenerator.properties";
		StringBuilder sb = FileUtils.readFileToString(proertyPath);
		String targetStr1=sb.toString();
		for (int i=0;i< tableNames.length;i++) {
			StringBuffer sb2=new StringBuffer(targetStr1);
			String string = tableNames[i];
			if(sb2.indexOf("table.name") > 0 ){
				sb2.delete(sb2.indexOf("table.name"),sb2.length());
			}
			if(sb2.indexOf("entity.name") > 0 ){
				sb2.delete(sb2.indexOf("entity.name"),sb2.length());
			}
			sb2.append("table.name="+string+"\r\n");
			sb2.append("entity.name="+entityNames[i]+"\r\n");
			//先删除
			FileUtils.deleteFileOrFolder(proertyPath);
			FileUtils.saveStringToFile(proertyPath, sb2.toString());
			mybatisGenerator();
			System.out.println(string+"....已完成");
			Thread.sleep(1000);
		}
		System.out.println("Generator all finshed");
	}

    /** 功能:通过在generator.properties中指定的tableName生成对应domain、xml及DAO */
    private static void mybatisGenerator() throws Exception {
        List<String> configs = new ArrayList<String>();
		boolean overwrite = false;
		//File configFile = new File("generatorConfig.xml");
		InputStream  configInputStream = BatchCodeGenerator.class.getResourceAsStream("/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(configs);
		Configuration config = cp.parseConfiguration(configInputStream);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, configs);
		myBatisGenerator.generate(null);
		configInputStream.close();
    }
}
