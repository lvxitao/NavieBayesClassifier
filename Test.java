import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Test {
	public static void main(String args[]) throws FileNotFoundException
	{
		Classifier bayes = new Classifier();

		
		//learn classify Janpanese and Chinese from Stanford PPT http://www.stanford.edu/class/cs124/lec/naivebayes.pdf
		
		String[] c1 = "Chinese Beijing Chinese".split("\\s");
		String[] c2 = "Chinese Beijing Chinese".split("\\s");
		String[] c3 = "Chinese Beijing Chinese".split("\\s");
		String[] c4 = "Chinese Beijing Chinese".split("\\s");
		String[] j1 = "Tokyo Japan Chinese".split("\\s");
		
		bayes.learn("c", c1);
		bayes.learn("c", c2);
		bayes.learn("c", c3);
		bayes.learn("c", c4);
		bayes.learn("j", j1);
		
		String[] unknowtext = "Chinese Chinese Chinese Tokyo Japan".split("\\s");
		
		//System.out.println(bayes.classify(Arrays.asList(unknowtext)).getCategory());
		/*bayes.countCategory();
		for(String category: bayes.countTable.keySet())
		{
			System.out.println("class:"+category+" count: "+ bayes.countTable.get(category));
		}*/
		System.out.println(bayes.classify(unknowtext));
		String s = "don't.  do' that! ";
		s = s.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
		System.out.println(s);
		ArrayList<String> postive  =new ArrayList<String>();
		ArrayList<String> negative  =new ArrayList<String>();
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
		System.out.println(postive);
		String a = "a+";
		System.out.println(postive.contains(a));
	}

}
