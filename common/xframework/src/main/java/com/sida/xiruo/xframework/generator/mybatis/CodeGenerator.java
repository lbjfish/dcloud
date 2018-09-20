package com.sida.xiruo.xframework.generator.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 * @author yjr
 * @version 2015-08-30
 */
public class CodeGenerator {
    
	public static void main(String[] args) throws Exception {
		mybatisGenerator();
		System.out.println("Gen erator finshed");
	}

    /** 功能:通过在generator.properties中指定的tableName生成对应domain、xml及DAO */
    private static void mybatisGenerator() throws Exception {
        List<String> configs = new ArrayList<String>();
		boolean overwrite = true;
//		File configFile = new File("generatorConfig.xml");
		InputStream  configInputStream = CodeGenerator.class.getResourceAsStream("/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(configs);
		Configuration config = cp.parseConfiguration(configInputStream);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, configs);
		myBatisGenerator.generate(null);
    }
}
