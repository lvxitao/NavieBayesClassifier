import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Stock {
	
	public static void processStock( ArrayList<String> trainingData, ArrayList<String> testData) throws FileNotFoundException{
		
		Classifier bayes = new Classifier();
		int ApCp, ApCn, ApCne, ApCm;
		ApCp = ApCn = ApCne = ApCm = 0;
		int AnCp, AnCn, AnCne, AnCm;
		AnCp = AnCn = AnCne = AnCm = 0;
		int AneCp, AneCn, AneCne, AneCm;
		AneCp = AneCn = AneCne = AneCm = 0;
		int AmCp, AmCn, AmCne, AmCm;
		AmCp = AmCn = AmCne = AmCm = 0;
		int uc=0;
		//bayes.setMemoryCapacity(1800);
		
		for(String S: trainingData)
		{
			
				String temp = S.substring(0, S.indexOf("@@"));
				String[] Text;
				Text = temp.split("\\s");
				if(S.contains("@@positive"))
				{
						
				    bayes.learn("positive", Text);						
				}
				else if(S.contains("@@negative"))
				{
					
					bayes.learn("negative", Text);
				}
				else if(S.contains("@@neutral"))
				{
					
					bayes.learn("neutral", Text);
				}
				else if(S.contains("@@mixed"))
				{
					bayes.learn("mixed",Text);
				}
			
		}
		bayes.initialOpinion();
		//bayes.countCategory();
		//bayes.setCapbility(5000);
		
		/*for(String S: testData)
		{
			
			String temp = S.substring(0, S.indexOf("@@"));
			String[] Text;
			Text = temp.split("\\s");
			if(S.contains("@@positive"))
			{
					
			    bayes.learn("positive", Arrays.asList(Text));						
			}
			else if(S.contains("@@negative"))
			{
				
				bayes.learn("negative", Arrays.asList(Text));
			}
			else if(S.contains("@@neutral"))
			{
				
				bayes.learn("neutral", Arrays.asList(Text));
			}
			else if(S.contains("@@mixed"))
			{
				bayes.learn("mixed", Arrays.asList(Text));
			}
		
	}*/
		for(String S: testData)
		{
			uc++;
			String temp = S.substring(0, S.indexOf("@@"));
			String[] unknownText;
			
			
			unknownText = temp.split("\\s");
			if(S.contains("@@positive"))
			{
				if(bayes.classify(unknownText).equals("positive"))
				{
					ApCp++;
				}
				else if(bayes.classify(unknownText).equals("negative"))
				{
					ApCn++;
				}
				else if(bayes.classify(unknownText).equals("neutral"))
				{
					ApCne++;
				}
				else if(bayes.classify(unknownText).equals("mixed"))
				{
					ApCm++;
				}
			}
			else if(S.contains("@@negative"))
			{
				if(bayes.classify(unknownText).equals("positive"))
				{
					AnCp++;
				}
				else if(bayes.classify(unknownText).equals("negative"))
				{
					AnCn++;
				}
				else if(bayes.classify(unknownText).equals("neutral"))
				{
					AnCne++;
				}
				else if(bayes.classify(unknownText).equals("mixed"))
				{
					AnCm++;
				}
			}
			else if(S.contains("@@neutral"))
			{
				if(bayes.classify(unknownText).equals("positive"))
				{
					AneCp++;
				}
				else if(bayes.classify(unknownText).equals("negative"))
				{
					AneCn++;
				}
				else if(bayes.classify(unknownText).equals("neutral"))
				{
					AneCne++;
				}
				else if(bayes.classify(unknownText).equals("mixed"))
				{
					AneCm++;
				}
			}
			else if(S.contains("@@mixed"))
			{
				if(bayes.classify(unknownText).equals("positive"))
				{
					AmCp++;
				}
				else if(bayes.classify(unknownText).equals("negative"))
				{
					AmCn++;
				}
				else if(bayes.classify(unknownText).equals("neutral"))
				{
					AmCne++;
				}
				else if(bayes.classify(unknownText).equals("mixed"))
				{
					AmCm++;
				}
			
			}
			
			
		}
		System.out.println("*******************"+" \n"
							+"   CP  CN  CNE  CM"+"\n"
							+"AP "+ApCp+"  "+ApCn+"   "+ApCne+"   "+ApCm+"\n"
							+"AN "+AnCp+"  "+AnCn+"   "+AnCne+"   "+AnCm+"\n"
							+"ANe"+AneCp+"  "+AneCn+"   "+AneCne+"  "+AneCm+"\n"
							+"AM "+AmCp+"   "+AmCn+"   "+AmCne+"   "+AmCm+"\n"
							
				
				);
		//int Pp = ApCp/(ApCp+ AnCp+ AneCp+ AmCp);
		float rp = (float)ApCp/(float)(ApCp+ ApCn+ ApCne+ ApCm);
		float rn = (float)AnCn/(float)(AnCp+ AnCn+ AnCne+ AnCm);
		float rne = (float)AneCne/(float)(AneCp+ AneCn+ AneCne+ AneCm);
		float rm = (float)AmCm/(float)(AmCp+ AmCn+ AmCne+ AmCm);
		
		float pp = (float)ApCp/(float)(ApCp+ AnCp+ AneCp+ AmCp);
		float pn = (float)AnCn/(float)(ApCn+ AnCn+ AneCn+ AmCn);
		float pne = (float)AneCne/(float)(ApCne+ AnCne+ AneCne+ AmCne);
		float pm = (float)AmCm/(float)(ApCm+ AnCm+ AneCm+ AmCm);
		
		float fp = 2*pp*rp/(pp+ rp);
		float fn = 2*pn*rn/(pn+ rn);
		float fne = 2*pne*rne/(pne+ rne);
		float fm = 2*pm*rm/(pm+ rm);
		System.out.println("*******************");
		System.out.println("precision of positive is: "+ pp);
		System.out.println("precision of negative is: "+ pn);
		System.out.println("precision of neutral is: "+ pne);
		System.out.println("precision of mixed is: "+ pm);	
		System.out.println("*******************");
		System.out.println("recall of positive is: "+ rp);
		System.out.println("recall of negative is: "+ rn);
		System.out.println("recall of neutral is: "+ rne);
		System.out.println("recall of mixed is: "+ rm);
		System.out.println("*******************");
		System.out.println("F-Score of positive is: "+ fp);
		System.out.println("F-Score of negative is: "+ fn);
		System.out.println("F-Score of neutral is: "+ fne);
		System.out.println("F-Score of mixed is: "+ fm);	
		System.out.println("*******************");
		
		float OA = (float)(ApCp+ AnCn+ AneCne+ AmCm)/(float)uc;
		
		System.out.println("overall accuracy is: "+OA);
		
	}
	
	public static void main(String args[]) throws IOException
	{
		File fileTraining = new File("src/Data/F_training.txt");
		File fileTest = new File("src/Data/Test_Stock.txt");
		Preprocess P1 = new Preprocess();
		Preprocess P2 = new Preprocess();
		P1.readfile(fileTraining);
		//P1.setMword();
		//P1.removem_word();
		//P1.getProcessedFile();
		P2.readfile(fileTest);
		//P2.removeStop();
		
		Stock.processStock(P1.getProcessedFile(),P2.getProcessedFile() );
		
		
	}

}
