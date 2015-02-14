import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
/**
 * An simple Naive Bayes text classification implementation 
 * @author Xitao Lv
 *
 */

public class Classifier {
	
/**
 * NBtableList is a vector for text classification. 
 * The construction of NBtableList is Hashtable <Category,<word, count>>	
 * 
 */
	Hashtable<String,Hashtable<String, Integer>> NBtableList = new Hashtable<String,Hashtable<String, Integer>> ();
	
/**
 * countTable is a table used for caculate the number of all words of a category
 */
	Hashtable<String, Integer> countTable = new Hashtable<String, Integer>();
	
	ArrayList<String> postive =new ArrayList<String>();
	
	ArrayList<String> negative = new ArrayList<String>();
	
/**
 * training from a text line
 * store the feature to the Vector NBtableList
 * @param catogery is a class 
 * @param text a line of string
 */
	public void learn( String catogery,String[] text)
	{
		
		if(!NBtableList.containsKey(catogery))
		{
			Hashtable<String, Integer> NBtable = new Hashtable<String, Integer>();
			for(String temp: text)
			{
				temp = temp.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
				if(!NBtable.containsKey(temp))
					NBtable.put(temp, 1);
				else
				{
					NBtable.put(temp, NBtable.get(temp)+1);
				}
			}
			NBtableList.put(catogery, NBtable);
		}
		else
		{
			for(String temp: text)
			{
				temp = temp.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
				if(!NBtableList.get(catogery).containsKey(temp))
					NBtableList.get(catogery).put(temp, 1);
				else
				{
					NBtableList.get(catogery).put(temp, NBtableList.get(catogery).get(temp)+1);
				}
			}
		}
		
	}
	
/**
 * count the number of words of each category(class) according to the feature vector NBtableList
 */
	
	public void countCategory()
	{
		
		for(String category: NBtableList.keySet())
		{
			Hashtable<String, Integer> tempTable = NBtableList.get(category);
			Integer count= 0;
			for(String temptext: tempTable.keySet())
			{
				count = count+ tempTable.get(temptext);
			}
		 countTable.put(category, count);
		}
	}
	
/**
 * classify a text line by vector NBtableList
 * pr(category|textline)
 * variable idf is a inverse doucument frequency  
 * variable Nweight is a weight for words in negative category
 * variable Mweight is a weight for words in mixed category 
 * @param text
 * @return result category(class) for a text line  
 */
	public String countFeature( String[] text)
	{
		int Fcount=0;
		Hashtable<String, Float> classifyTable = new Hashtable<String, Float>();
		String FinalClass ="";
		int size = countTable.size();
		float Nweight = 1.0f;
		float Mweight = 0.1f;
		for(String category: NBtableList.keySet())
		{
			float pr =1.0f;
			Hashtable<String, Integer> tempTable = NBtableList.get(category);
			for(String temp: text)
			{
				//System.out.println(temp);
				//temp = temp.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
				if(tempTable.containsKey(temp))
					{
						pr = pr*((float)tempTable.get(temp)+1/(countTable.get(category)));// key of naive bayes
						int tempcount = 0;
						//do idf
						/*for(String tempclass: NBtableList.keySet())
						{
							if(NBtableList.get(tempclass).containsKey(temp))
								tempcount++;							
						}
						float idf = (float)Math.log10((double)size/(double)tempcount);
						if(idf == 0)
							idf = 0.01f;
						pr = pr* idf;*/
						//change weight for less word category
						
						for(String tempclass: NBtableList.keySet())
						{
							if(NBtableList.get(tempclass).containsKey(temp)&&tempclass.equals("mixed"))
								pr = pr* Mweight;
							else if(NBtableList.get(tempclass).containsKey(temp)&&tempclass.equals("neutral"))
								pr = pr* Nweight;
						}
						/*if(category.equals("negative")&&negative.contains(temp))
						{
							pr= pr*1.8f;
							Fcount++;
						}
						if(category.equals("positive")&&postive.contains(temp))
						{
							pr = pr*1.8f;
							Fcount++;
						}*/
					}
				else{
						pr = pr*(1.0f/(countTable.get(category)));
					}
			}
			classifyTable.put(category, pr);
		}
		
		Float max = (Float)Collections.max(classifyTable.values());
		for(String temp: classifyTable.keySet())
		{
			if(classifyTable.get(temp).equals(max))
				FinalClass= temp;
		}
		
		return FinalClass;
		
	}

	public void initialOpinion() throws FileNotFoundException
	{
		File fileopinionN = new File("src/Data/negative-words.txt");
		File fileopinionP = new File("src/Data/positive-words.txt");
		Scanner scannerP = new Scanner(fileopinionP);
		Scanner scannerN = new Scanner(fileopinionN);

		while(scannerP.hasNext())
		{
			String temp = scannerP.nextLine();
			postive.add(temp);
		}
		
		while(scannerN.hasNext())
		{
			String temp = scannerN.nextLine();
			negative.add(temp);
		}
	}
	

/**
 * set size of feature vector NBtableList
 * sort the NBtableList by value at first
 * @param number
 */
	@SuppressWarnings("unchecked")
	public void setCapbility(int number)
	{
		Hashtable<String,Hashtable<String, Integer>> newtableList = new Hashtable<String,Hashtable<String, Integer>> ();
		int globalcount = 0;
		for(String category: countTable.keySet())
		{
			globalcount += countTable.get(category);
		}
		System.out.println(globalcount);
		for(String category: NBtableList.keySet())
		{
			ArrayList<Map.Entry<String, Integer>> sortList = new ArrayList(NBtableList.get(category).entrySet());
			Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>(){

				@Override
				public int compare(Entry<String, Integer> o1,
						Entry<String, Integer> o2) {
					// TODO Auto-generated method stub
					return o2.getValue().compareTo(o1.getValue());
				}});
			//System.out.println(category+"  here ");
			int newcount = (int) ((float)countTable.get(category)/(float)globalcount*number);
			System.out.println(newcount);
			Hashtable<String, Integer> newtable = new Hashtable<String, Integer>();
			for(int i =0; i<newcount; i++)
			{
				//System.out.println(sortList.get(i).getValue());
				newtable.put(sortList.get(i).getKey(), sortList.get(i).getValue());
			}
			newtableList.put(category, newtable);
		}
		NBtableList = newtableList;
	}
/**
 * call two main method to finish classifying
 * @param text a text line
 * @return result category
 */
	public String classify(String[] text)
	{
		countCategory();
		//setCapbility(100);
		return countFeature(text);
	}
	


}
