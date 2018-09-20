package com.sida.xiruo.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelRead {
		 //log4j
	   
	    private HSSFWorkbook workbook;//����
	   
	   
	    public ExcelRead(File file){
	        try {
//	        	InputStream  in = i
	            //��ȡ����workbook
	            workbook = new HSSFWorkbook(new FileInputStream(file));
	            System.out.println(workbook.getNumberOfSheets()+"excel sheet ����");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	   
	   
	    public List getDatasInSheet(int sheetNumber){
	        List<List> result = new ArrayList<List>();
	        //���ָ����sheet
	        HSSFSheet sheet = workbook.getSheetAt(sheetNumber);
	        //���sheet������
	        int rowCount = sheet.getLastRowNum();
	        if(rowCount < 1){
	            return result;
	        }
	        //������row
	        for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {
	            //����ж���
	            HSSFRow row = sheet.getRow(rowIndex);
	            if(null != row){
	                List<Object> rowData = new ArrayList<Object>();
	                //��ñ����е�Ԫ��ĸ���
	                int cellCount = row.getLastCellNum();
	                //������cell
	                for (short cellIndex = 0; cellIndex < cellCount; cellIndex++) {
	                    HSSFCell cell = row.getCell(cellIndex);
	                    //���ָ����Ԫ���е����
	                    Object cellStr = this.getCellString(cell);
	                    rowData.add(cellStr);
	                }
	                result.add(rowData);
	            }
	        }
	       
	        return result;
	    }
	   
	   
	    private Object getCellString(HSSFCell cell) {
	        Object result = null;
	        if(cell != null){
	            //��Ԫ�����ͣ�Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
	            int cellType = cell.getCellType();
	            switch (cellType) {
	            case HSSFCell.CELL_TYPE_STRING:
	                result = cell.getRichStringCellValue().getString();
	                break;
	            case HSSFCell.CELL_TYPE_NUMERIC:
	                result = cell.getNumericCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_FORMULA:
	                result = cell.getNumericCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_BOOLEAN:
	                result = cell.getBooleanCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_BLANK:
	                result = null;
	                break;
	            case HSSFCell.CELL_TYPE_ERROR:
	                result = null;
	                break;
	            default:
	                System.out.println("ö������������");
	                break;
	            }
	        }
	        return result;
	    }
	   
	    
	  

	    //test
	    public static void main(String[] args) {
	        File file = new File("E:/�б�3  2011���Ĵ�ʡ���ҽ��������ҩ�Ｏ���б�ɹ��б�Ŀ¼(������).xls");
	        ExcelRead parser = new ExcelRead(file);
	        List<List> datas = parser.getDatasInSheet(0);
	        for (int i = 0; i < datas.size(); i++) {
	            List row = datas.get(i);
	            for (short j = 0; j < row.size(); j++) {
	                Object value = row.get(j);
	                String data = String.valueOf(value);
	                System.out.print(data+"         ");
	            }
	            System.out.println();
	        }
	    }

}
