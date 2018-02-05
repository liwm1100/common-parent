package com.my.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * excel处理工具
 * @Project common-util
 * @version 1.0
 * @Author  cai
 * @Date    2016年10月24日
 * @Describe Excel
 */
public class ExcelUtil {
	
	// 导入excel
	public static List<String[]> getExcelCellsValue(MultipartFile file) throws Exception {
		return getExcelCellsValue(file, false);
	}

	// 导入excel
	public static List<String[]> getExcelCellsValue(MultipartFile file, boolean includeFirstNull) throws Exception {
		if (file == null)
			return null;
		String name = file.getOriginalFilename();// 获取上传文件名,包括路径
		long size = file.getSize();
		if ((name == null || name.equals("")) && size == 0)
			return null;
		InputStream is = null;
		try {
			is = file.getInputStream();
			Workbook wb = WorkbookFactory.create(is);
			if (wb instanceof HSSFWorkbook) {
				return getHssfValues(wb, includeFirstNull);
			} else if (wb instanceof XSSFWorkbook) {
				return getXssfValues(wb, includeFirstNull);
			} else {
				return null;
			}

		}finally {

			try {
				if(null != is )
					is.close();
			} catch (Exception e) {}

		}
	}

	/**
	 * 解析97-2003Excel
	 * 
	 * @param hssfWorkbook
	 * @return
	 * @throws Exception 
	 */
	private static List<String[]> getHssfValues(Workbook hssfWorkbook, boolean includeFirstNull) throws Exception {
		List<String[]> resList = new ArrayList<String[]>();
		if (hssfWorkbook == null) {
			return null;
		}
		HSSFSheet hssfSheet = (HSSFSheet) hssfWorkbook.getSheetAt(0); // hssfWorkbook.getNumberOfSheets()
		if (hssfSheet == null) {
			return null;
		}
		if (hssfSheet.getLastRowNum() > 65535) {
			throw new Exception("导入EXCEL超过最大条数限制，单次导入最大行数为" + 65535);
		}
		DecimalFormat df = new DecimalFormat("#.##");
		// 循环行Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}

			if (hssfRow.getLastCellNum() > 0) {
				String[] values = new String[hssfRow.getLastCellNum()];

				for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
					HSSFCell hSSFCellValue = hssfRow.getCell(i);
					if (hSSFCellValue == null) {
						values[i] = "";
						continue;
					}
					if (hSSFCellValue.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						values[i] = df.format(hSSFCellValue.getNumericCellValue());
					} else {
						values[i] = hSSFCellValue.getStringCellValue();
					}
				}
				// 第一列为空则过滤掉
				if (values.length == 0 || !includeFirstNull && StringUtils.isEmpty(values[0].trim())) {
					continue;
				}
				resList.add(values);
			}
		}
		return resList;
	}

	/**
	 * 解析2007Excel
	 * 
	 * @param hssfWorkbook
	 * @return
	 * @throws Exception 
	 */
	private static List<String[]> getXssfValues(Workbook xssfWorkbook, boolean includeFirstNull) throws Exception {
		List<String[]> resList = new ArrayList<String[]>();
		if (xssfWorkbook == null) {
			return null;
		}
		XSSFSheet xssfSheet = (XSSFSheet) xssfWorkbook.getSheetAt(0);
		if (xssfSheet == null) {
			return null;
		}
		if (xssfSheet.getLastRowNum() > 65535) {
			throw new Exception("导入EXCEL超过最大条数限制，单次导入最大行数为1000");
		}
		DecimalFormat df = new DecimalFormat("#.##");
		// 循环行Row
		for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow == null) {
				continue;
			}
			String[] values = new String[xssfRow.getLastCellNum()];
			for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
				XSSFCell xssfCellValue = xssfRow.getCell(i);
				if (xssfCellValue == null) {
					values[i] = "";
					continue;
				}
				if (xssfCellValue.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					values[i] = df.format(xssfCellValue.getNumericCellValue());
				} else {
					values[i] = xssfCellValue.getStringCellValue();
				}
			}
			// 第一列为空则过滤掉
			if (values.length == 0 || !includeFirstNull && StringUtils.isEmpty(values[0].trim())) {
				continue;
			}
			resList.add(values);
		}
		return resList;
	}

	/**
	 * 下载远程文件并保存到本地
	 * 
	 * @param remoteFilePath
	 *            远程文件路径
	 * @param localFilePath
	 *            本地文件路径
	 */
	public static void downloadFile(String remoteFilePath, String localFilePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace(System.out);
			}
		}
	}

	/**
	 * 创建表头
	 * 
	 * <pre>
	 * @param sheet 页签
	 * @param headers 表头内容数组
	 * ExcelUtils.createHeader()<BR>
	 * <P>Author : zhumingzhou </P>  
	 * <P>Date : 2013-8-15 </P>
	 * </pre>
	 */
	public static void createHeader(HSSFSheet sheet, String[] headers) {
		if (sheet == null || headers == null || headers.length <= 0)
			return;
		HSSFRow headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}
	}

	/**
	 * 创建行
	 * 
	 * <pre>
	 * @param sheet 页签
	 * @param objects 行内容数组
	 * @param rowIndex 行序号
	 * ExcelUtils.createRow()<BR>
	 * <P>Author : zhumingzhou </P>  
	 * <P>Date : 2013-8-15 </P>
	 * </pre>
	 */
	public static void createRow(HSSFSheet sheet, Object[] objects, int rowIndex) {
		if (sheet == null || objects == null || objects.length <= 0)
			return;
		HSSFRow row = sheet.createRow(rowIndex);
		for (int i = 0; i < objects.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(String.valueOf(objects[i] == null ? " " : objects[i]));
		}
	}

}
