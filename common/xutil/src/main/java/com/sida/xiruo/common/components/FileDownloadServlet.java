package com.sida.xiruo.common.components;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FileDownloadServlet extends HttpServlet {


	/**
	* 
	*/
	private static final long serialVersionUID = 1L;


	/**
	* Constructor of the object.
	*/
	public FileDownloadServlet() {
		super();
	}


	/**
	* Destruction of the servlet. <br>
	*/
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	/**
	* Require: parameter: fileName & relativePath
	* etc. <a href="FileDownloadServlet?fileName=template.txt&relativePath=/template/template.txt">TXT模板下载</a>
	* The doGet method of the servlet. <br>
	*
	* This method is called when a form has its tag value method equals to get.
	* 
	* @param request the request send by the client to the server
	* @param response the response send by the server to the client
	* @throws ServletException if an error occurred
	* @throws IOException if an error occurred
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		 String fileName = RequestUtils.getString(request, "fileName");
		 String relativePath = RequestUtils.getString(request, "relativePath");
		 String filePath = this.getServletContext().getRealPath(relativePath);
		 //   设置响应头和下载保存的文件名     
		 response.setContentType("APPLICATION/OCTET-STREAM");     
		 response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\""); 
		 BufferedInputStream bufferedStream = null;
		 PrintWriter out = null;
		 try{ 
			 //   打开指定文件的流信息     
			 bufferedStream = new BufferedInputStream(new FileInputStream(filePath));  
			 out = response.getWriter();      
			 //   写出流信息     
			 int   i;     
			 while   ((i= bufferedStream.read() )   !=   -1)   {     
				 out.write(i);  
			 }  
		  }catch(IOException e){
			  throw e;
		  }finally{
			  IOUtils.closeQuietly(bufferedStream);
			  IOUtils.closeQuietly(out);
		  }   
	}


	/**
	* The doPost method of the servlet. <br>
	*
	* This method is called when a form has its tag value method equals to post.
	* 
	* @param request the request send by the client to the server
	* @param response the response send by the server to the client
	* @throws ServletException if an error occurred
	* @throws IOException if an error occurred
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}


	/**
	* Initialization of the servlet. <br>
	*
	* @throws ServletException if an error occurs
	*/
	public void init() throws ServletException {
		// Put your code here
	}


}