package com.my120.market.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;;

public class ExcelPOIUtil {

	/**
	 * 生成行数据
	 * 
	 * @param columnKey
	 *            列的key
	 * @param dataList
	 *            数据集合
	 * @param pageSum
	 *            分页数
	 * @param sheet
	 *            sheet对象
	 * @param sheetIndex
	 *            sheet页序号
	 * @param count
	 *            行数
	 * @throws Exception
	 */
	public static void creatClumnData(String[] columnKey, List<Map<String, Object>> dataList, int pageSum,
			HSSFSheet sheet, int sheetIndex, int count) throws Exception {
		// 生数据行
		for (int i = 0; i < count; i++) {
			// 获取行对象
			HSSFRow row = sheet.createRow((i + 1));
			// 生成数据列
			for (int j = 0, size = columnKey.length; j < size; j++) {
				// 获取单元格对象
				HSSFCell cell = row.createCell(j);
				// 数据序号
				int dataIndex = pageSum * (sheetIndex - 1) + i;
				cell.setCellValue(dataList.get(dataIndex).get(columnKey[j]) == null ? "" : dataList.get(dataIndex).get(
						columnKey[j]).toString());
			}
		}
	}

	/**
	 * 获取行数
	 * 
	 * @param pageSum
	 *            总页数
	 * @param dataSize
	 *            数据总数
	 * @param sheetsize
	 *            sheet总数
	 * @param sheetIndex
	 *            sheet页序号
	 * @return
	 */
	public static int getColumnCount(int pageSum, int dataSize, int sheetsize, int sheetIndex) {
		int count = 0;
		// 行数量
		if (sheetsize == 1) {
			count = dataSize;
		} else if (sheetsize == sheetIndex && sheetsize != 1) {
			count = dataSize - pageSum * (sheetsize - 1);
		} else {
			count = pageSum;
		}
		return count;
	}

	/**
	 * 生成sheet页的标题
	 * 
	 * @param sheet
	 *            sheet页对象
	 * @param columnKey
	 *            列的key
	 * @param columnValue
	 *            列名
	 * @param columnWidth
	 *            列宽
	 * @throws Exception
	 */
	public static void createTitle(HSSFSheet sheet, String[] columnValue, int columnWidth, CellStyle titleStyle)
			throws Exception {
		// 获取行对象
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 350);
		for (int i = 0, count = columnValue.length; i < count; i++) {
			sheet.setColumnWidth(i, columnWidth * 256);
			// 获取单元格对象
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(columnValue[i]);
			cell.setCellStyle(titleStyle);
		}
	}

	/**
	 * 设置单元格样式<title使用>
	 * 
	 * @param workbook
	 *            excel对象
	 * @return
	 */
	public static CellStyle getCellStyle(HSSFWorkbook workbook) {
		Font font = workbook.createFont();
		// 字号
		font.setFontHeightInPoints((short) 14);
		// 粗体
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		// 居中
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}

//	/**
//	 * 设置下载文件中文件的名称
//	 * 
//	 * @param filename
//	 * @param request
//	 * @return
//	 */
//	public static String encodeFilename(String filename, HttpServletRequest request) {
//		String agent = request.getHeader("USER-AGENT");
//		try {
//			if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
//				String newFileName = URLEncoder.encode(filename, "UTF-8");
//				newFileName = StringUtils.replace(newFileName, "+", "%20");
//				if (newFileName.length() > 150) {
//					newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
//					newFileName = StringUtils.replace(newFileName, " ", "%20");
//				}
//				return newFileName;
//			}
//			if ((agent != null) && (-1 != agent.indexOf("Mozilla"))) {
//				return MimeUtility.encodeText(filename, "UTF-8", "B");
//			}
//			return filename;
//		} catch (Exception ex) {
//			return filename;
//		}
//	}

	/**
	 * 根据excel读取数据
	 * 
	 * @param filePath
	 *            文件路径
	 * @param columnKey
	 *            列集合
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readExcel(String filePath, String[] columnKey, boolean isExistTitle)
			throws Exception {
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		// 循环sheet页
		for (int i = 0, count = book.getNumberOfSheets(); i < count; i++) {
			HSSFSheet sheet = book.getSheetAt(i);
			// 行数
			int rows = sheet.getLastRowNum();
			// 开始读行的索引
			int indexStart = 0;
			// 过滤title
			if (isExistTitle) {
				indexStart = 1;
			}
			// 循环行
			for (int rowIndex = indexStart; rowIndex <= rows; rowIndex++) {
				dataMap = new HashMap<String, Object>();
				HSSFRow row = sheet.getRow(rowIndex);
				// 列数
				int columns = row.getLastCellNum();
				// 循环列
				for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
					dataMap.put(columnKey[columnIndex], row.getCell(columnIndex));
				}
				dataList.add(dataMap);
			}
		}
		return dataList;
	}
}
