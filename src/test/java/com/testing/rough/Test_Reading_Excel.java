package com.testing.rough;

import com.testing.base.TestBase;

public class Test_Reading_Excel extends TestBase{
	
	public static void main(String[] args) {
		
		
		String sheetName = "AddCustomer_Test";
		
		int rows = excel.getRowCount(sheetName);
		System.out.println(rows);
		int cols = excel.getColumnCount(sheetName);
		System.out.println(cols);
		Object[][] data = new Object[rows-1][cols];
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for(int colNum = 0; colNum < cols; colNum++) {
				data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		
		System.out.println(data);
		
		
	}

}
