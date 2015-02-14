import org.apache.poi.POIDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
public class PreprocessXlsx {

	public ArrayList<Pair<String, String>> Data;
	public ArrayList<String> StopFile;
	int count;
	public PreprocessXlsx(){
		Data = new ArrayList<Pair<String, String>>();
		StopFile = new ArrayList<String>();
		int count =0;
	}
	
	public void readxls(String path){
		//count = 0;
		//Hashtable<String, String> Data = new Hashtable<String, String>();
	
		try{
			FileInputStream file = new FileInputStream(new File(path));
			//XSSFWorkbook workbook = new XSSFWorkbook();
			//Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                count++;
            	Row row = rowIterator.next();
            	//code comments is wiki to read very column data in each row
                //For each row, iterate through all the columns
                /*Iterator<Cell> cellIterator = row.cellIterator();
                 
                while (cellIterator.hasNext()) 
                {
                    
                	Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) 
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        
                    }
                }*/
            	
            	Cell textcell = row.getCell(2);
            	Cell labelcell = row.getCell(4);
            	String text1 = labelcell.toString();
            	String text2 = textcell.toString();
            	System.out.println(textcell.toString().toLowerCase()+ "\t");
            	System.out.println(labelcell.toString()+ "\t");
            	Pair<String, String> temp = new Pair<String, String>(text1, text2);
            	Data.add(temp);
            	//System.out.println(text1+" "+text2);
                //System.out.println("");
            }
            //System.out.println("row count is: "+count);

            file.close();
            
            //return Data;
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		//return Data;
	}
	public void removeStop() throws FileNotFoundException
	{
		File filestop = new File("src/Data/stop.txt");
		Scanner scanner2 = new Scanner(filestop);
		ArrayList<Pair<String, String>> tempFile = new ArrayList<Pair<String, String>>(Data);
		//tempFile = ProcessedFile;
		Data.clear();
		while(scanner2.hasNext())
		{
			String temp = scanner2.nextLine();
			StopFile.add(temp.toLowerCase());
		}
		
		for(Pair<String, String> p: tempFile)
		{
			ArrayList<String> tempList = new ArrayList<String>();
			String[] temptext = p.getSecond().split("\\s");
			for(String temp: temptext)
			{
				tempList.add(temp);
			}
			for(String s2: StopFile)
			{
				if(tempList.contains(s2))
					tempList.remove(s2);
			}
			String ListString = "";
			for(String s3: tempList)
			{
				ListString += s3+" ";
			}
			Pair<String, String> newPair = new Pair<String, String>(p.getFirst(), ListString); 
			Data.add(newPair);
			//System.out.println(ListString);
		}
		
	}
	public ArrayList<Pair<String, String>> getData(){
		return Data;
	}
	public int getCount(){
		return count;
	}

}
