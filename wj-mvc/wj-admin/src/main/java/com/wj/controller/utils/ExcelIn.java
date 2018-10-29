package com.wj.controller.utils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wj.common.utils.Record;
import com.wj.common.utils.Tools;


/**
 * Excel导入
 * @author liuJian
 *
 */
public class ExcelIn {

	
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行,读取的行1必须为当前实体对应的表列
	 * @param file 读取数据的源Excel
	 * @param ignoreRows 读取数据忽略的行数，比如行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	private static String[][] getData(InputStream inputStream, int ignoreRows,ExcelType type)
			throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(inputStream);
		// 打开Workbook
		Workbook wb;
		if(type.equals(ExcelType.XLS)){
			POIFSFileSystem fs = new POIFSFileSystem(in);
			wb = new HSSFWorkbook(fs);
		}else{
			wb = new XSSFWorkbook(in);
		}
		Cell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			Sheet st = wb.getSheetAt(sheetIndex);

			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 设置编码
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd")
									.format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case Cell.CELL_TYPE_BLANK:
							break;
						case Cell.CELL_TYPE_ERROR:
							value = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y"
									: "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}

				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 去掉字符串右边的空格
	 * @param str 要处理的字符串
	 * @return 处理后的字符串
	 */
	private static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	/**
	 * 将Excel转为实体集合
	 * @param clazz 对应的实体类
	 * @param uploadFile 上传文件
	 * @param ignoreRows 忽略行数
	 * @return  对应实体集合
	 */
	public static List<Record> excelToList(InputStream inputStream,int ignoreRows,String attrs,ExcelType type){
		List<Record> list = new ArrayList<Record>();
		String[][] result;
		String[] fildes = attrs.split(",");
		try {
			result = ExcelIn.getData(inputStream, ignoreRows,type);
			int rowLength = result.length;
			for(int i=0;i<rowLength;i++) {
				Record record = new Record();
				for(int j=0;j<fildes.length;j++) {
					if(Tools.notNull(result[i][j])){
						record.put(fildes[j], result[i][j]);
					}
				}
				list.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
}
