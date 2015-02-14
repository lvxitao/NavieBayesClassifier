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
public class Smoke {
	//public HashMap<String, String> Data;
	public ArrayList<Pair<String, String>> Data;
	public int count;
	
	public Smoke()
	{
		Data = new ArrayList<Pair<String, String>>();
		count = 0;
	}
	public Smoke(ArrayList<Pair<String, String>> Data, int count)
	{
		this.Data = new ArrayList<Pair<String, String>>(Data);
		this.count = count;
	}
	
	public void readxls(String path){
		
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
	
	public void process(int type)
	{
		Classifier  bayes = new Classifier();
		
		int pcount = 0;	
		int acount = 0;
		//int tcount = 0;
		int gcount = 0;
		int count1 = 0;
		int count2 = 0;
		
		long seed = System.nanoTime();
		Collections.shuffle((Data), new Random(seed));
		//System.out.println(count);
		if(type ==1)
		{
		for(Pair<String, String> pair: Data)
		{
			String[] text = pair.getSecond().split("\\s");
			/*for(int i=0; i<text.length; i++)
			{
				if(text[i].contains("http://"))
					text[i]="";
			}*/
			//System.out.println(pair.getFirst()+" "+pair.getSecond());
			if(gcount<0.9*(count))
			{
				if(pair.getFirst().equals("Cessation"))
				{
					bayes.learn("Cessation", text);
					//count1++;
				}
				else if(pair.getFirst().equals("No Cessation")){
					bayes.learn("No Cessation",text);
					//count2++;
				}
				gcount++;
			}
				//ccount++;
			else{
				if(bayes.classify(text).equals(pair.getFirst()))
				{
					acount++;
				}
				else{
					pcount++;
				}
			
				gcount++;
			}
			
		}
		}
		else if(type ==2)
		{
			//mixed
			for(Pair<String, String> pair: Data)
			{
				String[] text = pair.getSecond().split("\\s");
				/*for(int i=0; i<text.length; i++)
				{
					if(text[i].contains("http://"))
						text[i]="";
				}*/
				//System.out.println(pair.getFirst()+" "+pair.getSecond());
				/*for(String s: text)
				{
					System.out.print(s+" ");
				}
				System.out.println();*/
					/*if(pair.getFirst().equals("Cessation"))
					{
						bayes.learn("Cessation", Arrays.asList(text));
						//count1++;
					}
					else if(pair.getFirst().equals("No Cessation")){
						bayes.learn("No Cessation", Arrays.asList(text));
						//count2++;
					}*/
				bayes.learn(pair.getFirst(), text);
			}
				
					//ccount++;
				
					
				for(Pair<String, String> pair: Data)
				{
					String[] text = pair.getSecond().split("\\s");
					
						if(bayes.classify(text).equals(pair.getFirst()))
						{
							acount++;
						}
						else{
							pcount++;
						}
						gcount++;
					
				}
				
			}
			
		//bayes.setMemoryCapacity(1000);
		System.out.println("count cessation" +count1);
		System.out.println("count No cessation" +count2);
		System.out.println("pcount: "+pcount );
		System.out.println("acount: "+acount);
		System.out.println("gcount: "+gcount);
		float Accuracy = (float)acount/(float)(pcount+acount);
		System.out.println("Accuracy is "+Accuracy);
	}
	
	public static void main(String args[]) throws FileNotFoundException
	{
			//Smoke.process(readxls());
		PreprocessXlsx p = new PreprocessXlsx();
		p.readxls("src/Data/Smoke.xlsx");
		//p.removeStop();
		Smoke s = new Smoke(p.getData(), p.getCount());
		//s.readxls("src/Data/Smoke.xlsx");
		
		s.process(1);
		//s.process(2);
		
	}

}