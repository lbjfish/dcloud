/**
 * 
 */
package com.sida.xiruo.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;

/**
 * @author xjiang
 * 
 */
public final class HtmlUtil {
	public static void main(String args[]) {
		try {
			File xslFile = new File("E:\\SOA\\51dsale_manage\\WebRoot\\xsl\\articleCategory.xsl");
			File xmlFile = new File("E:\\SOA\\51dsale_manage\\WebRoot\\xml\\all.xml");
			File targetHtmlFile = new File("E:\\SOA\\tomcat6.0.18\\webapps\\dsale\\test.htm");
			generateHtmlFile(xslFile, xmlFile, targetHtmlFile);
		} catch(Exception e) {
			e.printStackTrace(System.err);
		}
	}
	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-19
	 * @param xslFile
	 * @param xmlDocument
	 * @param targetHtmlFile
	 * @throws Exception
	 * @see
	 */
	public static void generateHtmlFile(File xslFile, Document xmlDocument, File targetHtmlFile) throws Exception {
		if(!xslFile.exists()) {
			throw new RuntimeException(xslFile.getAbsolutePath() + " not exists.");
		}
		xmlDocument.setXMLEncoding("UTF-8");
		final TransformerFactory factory = TransformerFactory.newInstance();
		final Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
		transformer.setOutputProperty("encoding", "UTF-8");
		final DocumentSource source = new DocumentSource(xmlDocument);
		final Result result = new StreamResult(targetHtmlFile);
		if(!targetHtmlFile.getParentFile().exists()) {
			targetHtmlFile.getParentFile().mkdirs();
		}
		transformer.transform(source, result);
	}
	
	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-19
	 * @param xslFile
	 * @param xmlFile
	 * @param targetHtmlFile
	 * @throws Exception
	 * @see
	 */
	public static void generateHtmlFile(File xslFile, File xmlFile, File targetHtmlFile) throws Exception {
		if(!xslFile.exists()) {
			throw new RuntimeException(xslFile.getAbsolutePath() + " not exists.");
		}
		if(!xmlFile.exists()) {
			throw new RuntimeException(xmlFile.getAbsolutePath() + " not exists.");
		}
		generateHtmlFile(xslFile, new SAXReader().read(xmlFile), targetHtmlFile);
	}

	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-19
	 * @param document
	 * @param targetHtmlFile
	 * @throws Exception
	 * @see
	 */
	public static void saveHtml(Document document, File targetHtmlFile) {
		FileWriter fileWriter = null;
		HTMLWriter writer = null;
		try {
			fileWriter = new FileWriter(targetHtmlFile);
			
			writer = new HTMLWriter(fileWriter, createDsaleFormat());
			//Document doc = DocumentHelper.parseText(document.asXML());
			writer.write(document);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
					fileWriter = null;
				} catch(IOException ioe) {
					ioe.printStackTrace(System.err);
				}
			}
			if(writer != null) {
				try {
					writer.close();
					writer = null;
				} catch(IOException ioe) {
					ioe.printStackTrace(System.err);
				}
			}
		}
	}
	
	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-20
	 * @return
	 * @see
	 */
	private static OutputFormat createDsaleFormat() {
		OutputFormat format = OutputFormat.createPrettyPrint();
        format.setSuppressDeclaration(true);
		format.setXHTML(true);
		format.setExpandEmptyElements(true);
        format.setEncoding("UTF-8");
        format.setIndent("\t");
        format.setLineSeparator("\r\n");
        format.setTrimText(true);
        format.setNewlines(true);
        
        return format;
	}
}
