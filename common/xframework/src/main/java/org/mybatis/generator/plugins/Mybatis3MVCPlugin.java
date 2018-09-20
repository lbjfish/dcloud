/**
 * Project Name: sdlc-mybatis-generator
 * File Name: Mybatis3MVCPlugin.java
 * Package Name: org.mybatis.generator.plugins
 * Date: 2015-11-10
 * Copyright (c) 2015, sdlc.com All Rights Reserved. 
 */  
 

package org.mybatis.generator.plugins;


import com.sida.xiruo.xframework.dao.IMybatisDao;
import com.sida.xiruo.xframework.service.IBaseService;
import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.PropertyRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他java类生成插件，暂支持controller和service、serviceimpl
 * 
 * @author chelizi
 * @version 1.0
 * @CreateDate 2015-11-10 下午3:32:19
 */

/** customize configuration */
public class Mybatis3MVCPlugin extends PluginAdapter {
	
	private String controllerTargetProject;
	private String controllerPackage;
	private String providerTargetProject;
	private String providerPackage;
	private String providerImplTargetProject;
	private String providerImplPackage;
	private String serviceTargetProject;
	private String servicePackage;
	private String serviceImplTargetProject;
	private String serviceImplPackage;
	
	private FullyQualifiedJavaType autowired;
	
	private FullyQualifiedJavaType controllerRootClass;
	
	private FullyQualifiedJavaType providerInterface;
	private FullyQualifiedJavaType providerRootInterface;
	private FullyQualifiedJavaType providerImplRootClass;
	
	private FullyQualifiedJavaType serviceInterface;
	private FullyQualifiedJavaType serviceRootInterface;
	private FullyQualifiedJavaType serviceImplRootClass;
	
	private FullyQualifiedJavaType daoInterface;
	private FullyQualifiedJavaType entityClass;
	
	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.Plugin#validate(java.util.List)
	 */
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.mybatis.generator.api.PluginAdapter#contextGenerateAdditionalJavaFiles(org.mybatis.generator.api.IntrospectedTable)
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		
		autowired = new FullyQualifiedJavaType(Autowired.class.getName());
		
		daoInterface = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		entityClass = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		
		serviceTargetProject = properties.getProperty("serviceTargetProject");
		servicePackage = properties.getProperty("servicePackage");
		serviceRootInterface = new FullyQualifiedJavaType(properties.getProperty("serviceRootInterface"));
		
		serviceImplTargetProject = properties.getProperty("serviceImplTargetProject");
		serviceImplPackage = properties.getProperty("serviceImplPackage");
		serviceImplRootClass = new FullyQualifiedJavaType(properties.getProperty("serviceImplRootClass"));
		
//		providerTargetProject = properties.getProperty("providerTargetProject");
//		providerPackage = properties.getProperty("providerPackage");
//		providerRootInterface = new FullyQualifiedJavaType(properties.getProperty("providerRootInterface"));
//
//		providerImplTargetProject = properties.getProperty("providerImplTargetProject");
//		providerImplPackage = properties.getProperty("providerImplPackage");
//		providerImplRootClass = new FullyQualifiedJavaType(properties.getProperty("providerImplRootClass"));
		
		List<GeneratedJavaFile> gjfs = new ArrayList<GeneratedJavaFile>();
		
		gjfs.add(generateService());
		gjfs.add(generateServiceImpl());
//		gjfs.add(generateProvider());
//		gjfs.add(generateProviderImpl());
		
		controllerTargetProject = properties.getProperty("controllerTargetProject");
		controllerPackage = properties.getProperty("controllerPackage");
		String controllerRootClassProperty = properties.getProperty("controllerRootClass");
		if(StringUtils.isNotEmpty(controllerPackage) && StringUtils.isNotEmpty(controllerPackage)){
			controllerRootClass = new FullyQualifiedJavaType(controllerRootClassProperty);
			gjfs.add(generateController());
		}
		return gjfs;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateService() {
		
		serviceInterface = new FullyQualifiedJavaType(servicePackage + "."
				+ entityClass.getShortName() + "Service");
		
		Interface service = new Interface(serviceInterface);
		service.setVisibility(JavaVisibility.PUBLIC);
		service.addSuperInterface(serviceRootInterface);
		service.addImportedType(serviceRootInterface);
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				service,
				serviceTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateServiceImpl() {
		
		FullyQualifiedJavaType serviceImplInterface = new FullyQualifiedJavaType(
				serviceImplPackage + "." + entityClass.getShortName() + "ServiceImpl");
		
		TopLevelClass topLevelClass = new TopLevelClass(serviceImplInterface);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(serviceImplRootClass);
		topLevelClass.addSuperInterface(serviceInterface);
		topLevelClass.addAnnotation("@Service");
		
		generateLoggerField(topLevelClass);
		
		String fieldName = StringUtils.uncapitalize(daoInterface.getShortName());
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(daoInterface);
		field.setName(fieldName);
		topLevelClass.addField(field);
		
		FullyQualifiedJavaType iMybatisDao = new FullyQualifiedJavaType(IMybatisDao.class.getName()+"<"+entityClass.getShortName()+">");
		Method method = new Method();
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("getBaseDao");
		method.setReturnType(iMybatisDao);
		method.addBodyLine("return " + fieldName + ";");
		topLevelClass.addMethod(method);
		
		topLevelClass.addImportedType(serviceImplRootClass);
		topLevelClass.addImportedType(serviceInterface);
		topLevelClass.addImportedType(autowired);
		topLevelClass.addImportedType(daoInterface);
		topLevelClass.addImportedType(iMybatisDao);
		topLevelClass.addImportedType(new FullyQualifiedJavaType(Service.class.getName()));
		topLevelClass.addImportedType("org.slf4j.LoggerFactory");

		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				topLevelClass,
				serviceImplTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}
	
	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateProvider() {
		
		providerInterface = new FullyQualifiedJavaType(providerPackage + "."
				+ entityClass.getShortName() + "Provider");
		
		Interface provider = new Interface(providerInterface);
		provider.setVisibility(JavaVisibility.PUBLIC);
		provider.addSuperInterface(providerRootInterface);
		provider.addImportedType(providerRootInterface);
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				provider,
				providerTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateProviderImpl() {
		
		FullyQualifiedJavaType providerImplInterface = new FullyQualifiedJavaType(
				providerImplPackage + "." + entityClass.getShortName() + "ProviderImpl");
		
		TopLevelClass topLevelClass = new TopLevelClass(providerImplInterface);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(providerImplRootClass);
		topLevelClass.addSuperInterface(providerInterface);
		topLevelClass.addAnnotation("@Service");
		
		generateLoggerField(topLevelClass);
		
		String fieldName = StringUtils.uncapitalize(serviceInterface.getShortName());
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(serviceInterface);
		field.setName(fieldName);
		topLevelClass.addField(field);
		
		FullyQualifiedJavaType iBaseService = new FullyQualifiedJavaType(IBaseService.class.getName()+"<"+entityClass.getShortName()+">");
		Method method = new Method();
		method.addAnnotation("@Override");
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("getBaseService");
		method.setReturnType(iBaseService);
		method.addBodyLine("return " + fieldName + ";");
		topLevelClass.addMethod(method);
		
		topLevelClass.addImportedType(providerImplRootClass);
		topLevelClass.addImportedType(providerInterface);
		topLevelClass.addImportedType(autowired);
		topLevelClass.addImportedType(serviceInterface);
		topLevelClass.addImportedType(iBaseService);
		topLevelClass.addImportedType(new FullyQualifiedJavaType(Service.class.getName()));
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				topLevelClass,
				providerImplTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}

	/**
	 * 
	 * @return
	 */
	private GeneratedJavaFile generateController() {
		
		FullyQualifiedJavaType controller = new FullyQualifiedJavaType(
				controllerPackage + "." + entityClass.getShortName() + "Controller");
		
		TopLevelClass topLevelClass = new TopLevelClass(controller);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		topLevelClass.setSuperClass(controllerRootClass);
		topLevelClass.addImportedType("org.springframework.web.bind.annotation.RestController");
		topLevelClass.addImportedType("org.slf4j.LoggerFactory");
		topLevelClass.addAnnotation("@RestController");
		topLevelClass.addAnnotation(String.format("@RequestMapping(\"%s\")", StringUtils.uncapitalize(entityClass.getShortName())));
		
		generateLoggerField(topLevelClass);
		
		Field field = new Field();
		field.addAnnotation("@Autowired");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(serviceInterface);
		field.setName(StringUtils.uncapitalize(serviceInterface.getShortName()));
		topLevelClass.addField(field);
		
		//topLevelClass.addImportedType(new FullyQualifiedJavaType(Controller.class.getName()));
		topLevelClass.addImportedType(new FullyQualifiedJavaType(RequestMapping.class.getName()));
		topLevelClass.addImportedType(controllerRootClass);
		topLevelClass.addImportedType(autowired);
		topLevelClass.addImportedType(serviceInterface);
		
		GeneratedJavaFile gjf = new GeneratedJavaFile(
				topLevelClass,
				controllerTargetProject,
				context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
				context.getJavaFormatter());
		return gjf;
	}
	
	private void generateLoggerField(TopLevelClass topLevelClass){
		
		FullyQualifiedJavaType log = new FullyQualifiedJavaType(org.slf4j.Logger.class.getName());
		
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setStatic(true);
		field.setFinal(true);
		field.setType(log);
		field.setName("logger");
		field.setInitializationString(String.format(
				"LoggerFactory.getLogger(%s.class)", topLevelClass.getType()
						.getShortName()));
		
		topLevelClass.addField(field);
		topLevelClass.addImportedType(log);
	}

}
