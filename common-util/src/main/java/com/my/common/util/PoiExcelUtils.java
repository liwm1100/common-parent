package com.my.common.util;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * POI Excel帮助类。
 * 
 * @author huqilong
 *
 */
public class PoiExcelUtils {

	private static final Logger logger = LoggerFactory.getLogger(PoiExcelUtils.class);

	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;

	public static final int UNIT_OFFSET_LENGTH = 7;

	public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 };

	public static final int EXCEL_MAX_ROWS = 65534;

	public static interface DataForamatter<T> {

		/**
		 * 格式化数据
		 * 
		 * @param value
		 * @return
		 */
		String format(T value);

	}

	public static class ColDef {

		// 字段名
		private String field;

		// 字段名称
		private String title;

		// 字段宽度
		private int width;

		@SuppressWarnings("rawtypes")
		private DataForamatter foramatter;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		@SuppressWarnings("unchecked")
		public <T> DataForamatter<T> getForamatter() {
			return foramatter;
		}

		public <T> void setForamatter(DataForamatter<T> foramatter) {
			this.foramatter = foramatter;
		}

	}

	/**
	 * 创建Excel。
	 * 
	 * @param colList
	 * @param dataList
	 * @return
	 */
	public static <T> Workbook createExcelSheet(List<ColDef> colList, List<T> dataList) {
		Workbook workbook = new HSSFWorkbook();
		/** create workbook header row */
		// head font style
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.LIME.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		Sheet sheet = workbook.createSheet("Sheet1");
		Row row = sheet.createRow(0);
		for (int i = 0; i < colList.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(colList.get(i).getTitle());
			cell.setCellStyle(style);
			sheet.setColumnWidth(i, PoiExcelUtils.pixel2WidthUnits(colList.get(i).getWidth()));
		}
		/** create data rows */
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 1);
			Object bean = dataList.get(i);
			for (int j = 0; j < colList.size(); j++) {
				Cell cell = row.createCell(j);
				String beanProp = colList.get(j).getField();
				try {
					Object cellValue = null;
					cellValue = PropertyUtils.getProperty(bean, beanProp);
					DataForamatter<Object> formatter = colList.get(j).getForamatter();
					if (formatter == null) {
						cell.setCellValue(ConvertUtils.convert(cellValue+""));
					} else {
						cell.setCellValue(formatter.format(cellValue));
					}
				} catch (Exception e) {
					logger.warn("Failed to add bean property to excel, will ignore. Property: " + beanProp + " of " + bean, e);
					cell.setCellValue("");
				}
			}
		}
		return workbook;
	}

	/**
	 * 帮助方法：设置Excel文件下载的response头。
	 * 
	 * @param response
	 * @param excelFileName
	 */
	public static void setExcelResponseHeader(HttpServletResponse response, String excelFileName) {
		response.setHeader("Pragma", "public");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName);
	}

	/**
	 * pixel units to excel width units(units of 1/256th of a character width)
	 * 
	 * @param pxs
	 * @return
	 */
	public static short pixel2WidthUnits(int pxs) {
		short widthUnits = (short) (EXCEL_COLUMN_WIDTH_FACTOR * (pxs / UNIT_OFFSET_LENGTH));

		widthUnits += UNIT_OFFSET_MAP[(pxs % UNIT_OFFSET_LENGTH)];

		return widthUnits;
	}

	/**
	 * excel width units(units of 1/256th of a character width) to pixel units
	 * 
	 * @param widthUnits
	 * @return
	 */
	public static int widthUnits2Pixel(short widthUnits) {
		int pixels = (widthUnits / EXCEL_COLUMN_WIDTH_FACTOR) * UNIT_OFFSET_LENGTH;

		int offsetWidthUnits = widthUnits % EXCEL_COLUMN_WIDTH_FACTOR;
		pixels += Math.round((float) offsetWidthUnits / ((float) EXCEL_COLUMN_WIDTH_FACTOR / UNIT_OFFSET_LENGTH));

		return pixels;
	}
}
