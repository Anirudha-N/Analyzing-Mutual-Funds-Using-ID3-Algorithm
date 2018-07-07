import java.io.*;
//import java.lang.*;

public class displayquestions
{
	public static void main(String [] args) throws IOException
	{
		int [] x = new int [15]; //store answer of each subquestion.
		int i = 0;

	
		float threshold = 0.0f; // criteria to decide the risk factor analysis. add - high risk taking capability&& minus - low risk taking capability

		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

		System.out.println("******WELCOME!******* \nFill out the questionnaire: \n");

		System.out.println(" 1.What is your age? \n i.30 and under\n ii.31 to 40\n iii.41 to 50\n iv.51 to 65\n v.Over 65");
		x[i++] = Integer.parseInt(br.readLine());
		
		//System.out.println(" 2.	What is your marital status?\n i.Married \n ii.Unmarried \n iii.Others ");
		//x[i++] = Integer.parseInt(br.readLine());

		System.out.println("2. How much do you want to invest?\n i.Less than 50k \n ii. 50k to 1 Lac\n iii. 1 to 3 Lac \n iv. Above 3 Lac");
		x[i++] = Integer.parseInt(br.readLine());

		System.out.println("3. How much risk do you prefer to take\n i.Low \n ii. High");
		x[i++] = Integer.parseInt(br.readLine());

		System.out.println("4. How much return do you expect to receive?\n i. less than 5%\n ii. 5%-10%\n iii.10% more");
		x[i++] = Integer.parseInt(br.readLine());

		System.out.println("5. For how long do you wish to invest? \n i. Less than a year\n ii. 1-3 years\n iii. 3 years and more");
		x[i++] = Integer.parseInt(br.readLine());

		//System.out.println("7. You belong to which of the following categories: \n i. Employed \n ii.Retired\n iii. Businessman\n iv.Others ");
		//x[i++] = Integer.parseInt(br.readLine());


		try
		{
			FileWriter fw = new FileWriter("tablefinal.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);

			//pw.println("Age\tMarital_status\tAmount\tRisk\tReturn\tTenure\tCategoty\tMutual_fund\tThreshold");
			//pw.println("\n");
			//question1 
			if (x[0] == 1 || x[0] == 2)
			{	pw.print("\nYoung\t"); 
				threshold ++;
		 	}
			else if (x[0] == 3|| x[0] == 4)
			{	pw.print("\nMiddle\t");
			 	threshold += 0.5;  
			}
			else
			{	pw.print("\nOld\t");  
				threshold-- ; 
		  	}
	
	
			//question 2
			/*if (x[1] == 1 )
			{	pw.print("Married\t"); 
				threshold --; 
			}
	  		else if (x[1] == 2)
			{	pw.print("unmarried\t"); 
				threshold++;  
			}
			else
			{	pw.print("Others\t"); 
				threshold += 0.5;      
			}
			*/

			//question 3
			if (x[1] == 1 )
			{	pw.print("less_than_50k\t");  
				threshold --; 
			}
			else if (x[1] == 2)
			{	pw.print("50k-1lac\t");
				threshold-= 0.5;  
			}
			else if(x[1] == 3)
			{	pw.print("1-3lac\t"); 
				threshold+=0.5;      
			}
			else
			{	pw.print("more_than_3lac\t"); 
				threshold++; 
			}


			//question 4
			if (x[2] == 1 )
			{	pw.print("Low\t"); 
				threshold--;  
			}
  			else
			{	pw.print("High\t"); 
				threshold++;      
			}	


			//question 5
			if (x[3] == 1 )
			{	pw.print("less_than_5%\t");  
				threshold-- ; 
			}
			else if (x[3] == 2)
			{	pw.print("5%-10%\t"); 
				threshold += 0.5;
			}
			else
			{	pw.print("more_than_10%\t ");	
				threshold ++; 
			}


			//question 6
			if (x[4] == 1 )
			{	pw.print("less_than_1yr\t"); 
				threshold --; 
			}
 			else if (x[4] == 2)
			{	pw.print("1-3yr\t"); 
				threshold += 0.5;  
			}
			else
			{	pw.print( "more_than_3yr\t ");	
				threshold++; 
			}

			if (threshold >= 1 )
				pw.print("Yes\t");  
			else
			{	pw.print("No\t");      }	
			

			
			//pw.print(threshold);
			pw.close();
		}

		catch (IOException e)
		{
			System.out.println(e);
		}
	}
}