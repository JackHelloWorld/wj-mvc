package com.wj.controller.utils;



import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.wj.common.utils.DatePattern;
import com.wj.common.utils.Record;
import com.wj.common.utils.Tools;

/**
 * Excel导出
 * @author LiuJack
 *
 */
public class ExcelTools {


	/**
	 * 将集合转为文件
	 * @param list list集合
	 * @param headerStr 头部显示的字符(以,隔开)
	 * @param fildStr 对应的属性字符(以,隔开)
	 * @param headerName 头部名称
	 * @param response Response对象
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation"})
	public static final void listToExcel(List<? extends Record> list, String headerStr,String fildStr,String headerName,HttpServletResponse response) throws Exception{
		if(list.size()==0) return;
		String fileName = headerName+".xls";
		String[] header = headerStr.split(",");
		String[] filds = fildStr.split(",");
		OutputStream os = response.getOutputStream();// 取得输出流   
        response.reset();// 清空输出流   
        response.setHeader("Content-Disposition", "attachment;filename="  + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        response.setContentType("application/msexcel");// 定义输出类型 
		// 第一步，创建一个webbook，对应一个Excel文件   
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();  
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet   
		HSSFSheet sheet = wb.createSheet(headerName);  
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short   
		HSSFRow row = sheet.createRow((int) 0);  
		// 第四步，创建单元格，并设置值表头 设置表头居中   
		HSSFCellStyle style = wb.createCellStyle();  
		//style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式   
		HSSFCell cell = row.createCell((short) 0);  
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);  

		for (int i = 0; i < header.length; i++) {
			cell.setCellValue(header[i]);  
			cell.setCellStyle(style);  
			cell = row.createCell((short) (i+1));  
		}

		// 第五步，写入实体数据
		for (int i = 0; i < list.size(); i++){  
			row = sheet.createRow((int) i+1);  
			Record model = list.get(i);
			for (int j = 0; j < filds.length; j++) {
				HSSFCell hcl = row.createCell((short) j);
				Object value = model.get(filds[j]);
				setValue(hcl, value);
			}
		}  
		wb.write(os); // 写入文件   
		os.close(); // 关闭流
	}
	
	/**
	 * 设置Cell值
	 * @param hcl
	 * @param value
	 */
	private static final void setValue(HSSFCell hcl, Object value) {
		if(Tools.isNullOrNullStr(value)){
			hcl.setCellValue("");
		}else if (value instanceof Integer) {
			hcl.setCellValue(Integer.parseInt(value.toString()));
		}else if (value instanceof Double) {
			hcl.setCellValue(Double.parseDouble(value.toString()));
		}else if (value instanceof Date) {
			hcl.setCellValue(Tools.getDateToStr((Date)value, DatePattern.YYYYMMDDHHMMSS));
		}else if (value instanceof Boolean) {
			hcl.setCellValue(Boolean.parseBoolean(value.toString()));
		}else {
			hcl.setCellValue(value.toString());
		}
	}
	
}
