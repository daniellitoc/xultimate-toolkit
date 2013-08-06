package org.danielli.future.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {
	public static void exportXLS(String name, String[][] strings, OutputStream out) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(name);
			
		for (int hang = 0; hang < strings.length; hang++) {
			// ������
			HSSFRow row = sheet.createRow(hang);
			for (int lie = 0; lie < strings[hang].length; lie++) {
				// ������ createCell((short) lie)
				HSSFCell cell = row.createCell(lie);
				cell.setCellValue(strings[hang][lie]);
				
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				
				HSSFFont f = wb.createFont();
				f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				
				cellStyle.setFont(f);

				// adapt this font to the cell
				cell.setCellStyle(cellStyle);
			}
		}
			
		wb.write(out);
		out.flush();
		out.close();
	}

	public static String[][] readXLS(String name, InputStream in) throws IOException {
		String[][] strings;
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(in));
		HSSFSheet sheet = wb.getSheet(name);
		strings = new String[sheet.getPhysicalNumberOfRows()][];
		for (int r = 0; r < strings.length; r++) {
			HSSFRow row = sheet.getRow(r);
			strings[r] = new String[row.getPhysicalNumberOfCells()];
			for (int l = 0; l < strings[r].length; l++) { 
				HSSFCell cell = row.getCell(l);
				switch (cell.getCellType()) { 
					case HSSFCell.CELL_TYPE_BLANK:   // ��
						strings[r][l] = "";  
						break;
					case HSSFCell.CELL_TYPE_NUMERIC: // ��ֵ
						strings[r][l] = String.valueOf(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:  // �ַ�
						strings[r][l] = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // ����
						strings[r][l] = String.valueOf(cell.getBooleanCellValue());
						break;
					default:  
						strings[r][l] = "δ֪";  
						break;  
				}
			}
		}
		in.close();
		return strings;
	}
}
