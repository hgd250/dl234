package com.my120.market.utils;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * excel 模板类
 * 
 * @author wanglei
 * 
 */
public class ViewExcelPOI extends AbstractExcelView {

	String[] columnKey = null;
	String[] columnValue = null;
	List<Map<String, Object>> dataList = null;
	String filename = null;
	int cardSum = 100000;
	int pageSum = 50000;
	int columnWidth = 20;

	public ViewExcelPOI(String[] columnKey, String[] columnValue, List<Map<String, Object>> dataList, String filename) {
		this.columnKey = columnKey;
		this.columnValue = columnValue;
		this.dataList = dataList;
		this.filename = filename;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> obj, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 数据行数
		int dataSize = dataList.size();
		// 每五万为一个sheet页,总sheet页数
		int sheetsize = (dataSize / pageSum) == 0 ? 1 : (dataSize % pageSum > 0 ? (dataSize / pageSum + 1) : dataSize
				/ pageSum);
		// 表头样式
		CellStyle titleStyle = ExcelPOIUtil.getCellStyle(workbook);
		// 生成名为“第一页”的工作表，参数0表示这是第一页
		HSSFSheet sheet = null;
		// 循环sheet页
		for (int sheetIndex = 1; sheetIndex <= sheetsize; sheetIndex++) {
			sheet = workbook.createSheet(" 第" + sheetIndex + "页  ");
			// 生成标题
			ExcelPOIUtil.createTitle(sheet, columnValue, columnWidth, titleStyle);
			// 行数
			int count = ExcelPOIUtil.getColumnCount(pageSum, dataSize, sheetsize, sheetIndex);
			// 生成行数据
			ExcelPOIUtil.creatClumnData(columnKey, dataList, pageSum, sheet, sheetIndex, count);
		}
		// 设置下载时客户端Excel的名称
		String excelName = filename + ".xls";
		// 处理中文文件名
//		excelName = ExcelPOIUtil.encodeFilename(excelName, request);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + excelName);
		OutputStream ouputStream = response.getOutputStream();
		workbook.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

}