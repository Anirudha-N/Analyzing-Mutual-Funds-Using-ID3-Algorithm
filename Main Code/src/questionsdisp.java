//import java.awt.*;  
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
//import java.lang.*;
import java.net.URL;
import java.awt.event.*;
import javax.swing.*;

class questionsdisp extends JFrame implements ActionListener
{
		/**
	 * Just default
	 
	 */
	private static final long serialVersionUID = 1L;
		String ans0,ans1,ans2,ans3,ans4 = null; // options selected by the user
		String a00,a11,a22,a33,a44 = null;//backend answers which is send to printing
		JLabel dispans[] = new JLabel[5]; 
		
	    JLabel l;  
	    JRadioButton jb[]=new JRadioButton[5];  
	    JButton b1;  
	    ButtonGroup bg;  
	    int count=0,current=0;
	    float threshold = 0.0f;
	    int m[]=new int[10];    
	    String result[] = new String[5];
	    int counter = 0;
	    int storepos = 0;
	    questionsdisp(String s)  
	    {  
	        super(s); 
	        getContentPane().setBackground(Color.WHITE);
	        l=new JLabel(); 
	        add(l);
	        
	        for(int k = 0; k < 5; k++ )
	        {
	        	dispans[k]= new JLabel();
	        	 add(dispans[k]);
	        }    
	        bg=new ButtonGroup();  
	        for(int i=0;i<5;i++)  
	        {  
	            jb[i]=new JRadioButton();     
	            add(jb[i]);  
	            bg.add(jb[i]);  
	        }  
	        b1=new JButton("NEXT");    
	        //b1.setVisible(false);
	        b1.addActionListener(this); 
	        add(b1);  
	       set();  
	        l.setBounds(30,40,450,20);  
	   
	        jb[0].setBounds(50,80,140,30);  
	        jb[1].setBounds(50,110,140,30);  
	        jb[2].setBounds(50,140,140,30);  
	        jb[3].setBounds(50,170,140,30); 
	        jb[4].setBounds(50,200,140,30);
	      
	        b1.setBounds(250,240,100,30);  
	        dispans[0].setBounds(50,80,140,30);  
	        dispans[1].setBounds(50,110,140,30);  
	        dispans[2].setBounds(50,140,140,30);  
	        dispans[3].setBounds(50,170,140,30); 
	        dispans[4].setBounds(50,200,140,30);
	          
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        setLayout(null);  
	        setLocation(550,300);  
	        setVisible(true);  
	        setSize(600,350);  
	    }
	    void set()
	    {
	    	
	    	jb[0].setBackground(Color.WHITE);
	    	jb[1].setBackground(Color.WHITE);
	    	jb[2].setBackground(Color.WHITE);
	    	jb[3].setBackground(Color.WHITE);
	    	jb[4].setBackground(Color.WHITE);
	    	
	    	//jb[0].addActionListener(l);
	    	
	    	  
	         if(current==0)  
	         {  
	             l.setText("Q1: What is your age?");  
	             jb[0].setText("Below 30");jb[1].setText("31 to 40");jb[2].setText("41 to 50");jb[3].setText("51 to 65"); jb[4].setText("Over 65");
	             
	         }  
	         
	         if(current==1)  
	         {  
	             l.setText("Q2: How much do you want to invest?");  
	             jb[0].setText("Less than 50K");jb[1].setText("50K - 1Lac");jb[2].setText("1Lac - 3Lac");jb[3].setVisible(true);jb[3].setText("Above 3 Lac");jb[4].setVisible(false);
	             
	         } 
	         if(current==2)  
	         {  
	             l.setText("Q3: How much risk would you like to take?");  
	             jb[0].setText("Low");jb[1].setText("Medium");jb[2].setText("High");jb[3].setVisible(false); jb[4].setVisible(false);  
	         }  
	         if(current==3)  
	         {  
	             l.setText("Q4: How much return do you expect to receive?");  
	             jb[0].setText("Less than 5%");jb[1].setText("5% - 10%");jb[2].setVisible(true);jb[2].setText("10% and more");jb[3].setVisible(false); jb[4].setVisible(false);  
	         }
	         if(current==4)  
	         {  
	             l.setText("Q5: For how long do you wish to invest?");  
	             jb[0].setText("Less than 1 yr");jb[1].setText("1yr - 3 yr");jb[2].setVisible(true);jb[2].setText("3yrs or more");jb[3].setVisible(false); jb[4].setVisible(false);  
	         } 
	         
	         
	    }
	    @Override
		public void actionPerformed(ActionEvent e)
		{
	    	
			if(e.getSource() == b1) 
			{
				storechoice();
				current++;
				set();
				if (current == 4)
				{
					b1.setText("SUBMIT");
				}
				if (current == 5)
				{     
					
					jb[0].setVisible(false);
					jb[1].setVisible(false);
					jb[2].setVisible(false);
					jb[3].setVisible(false);
					jb[4].setVisible(false);
					
				}
			}
	       
			if(e.getActionCommand().equals("SUBMIT"))
			{
				l.setText("OPTIONS SELECTED ARE");
				b1.setText("SHOW");
				dispans[0].setText(ans0);
				dispans[1].setText(ans1);
				dispans[2].setText(ans2);
				dispans[3].setText(ans3);
				dispans[4].setText(ans4);
				try {
					storeinfile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.out.println("Stored");

			}
			if(e.getActionCommand().equals("SHOW"))
			{
				try
				{
					b1.setVisible(false);
					improvedID3 id3 = new improvedID3();
					decisionTree tree1 = id3.runAlgorithm(fileToPath("tablefinal.txt"), "mf", "\t");
					id3.printStatistics();
					tree1.print();
					tree1.printMutual_Funds(a00,a11,a22,a33,a44);

				}
				catch(IOException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.exit(0);
			} 
		}
	    private void storechoice()// throws IOException
	    {
	    	int s;
	    	int flag ;
	    		if (current == 0)
	    		{
	    			flag = 0;
	    			for(s = 0; s < 5; s++)
		    		{
		    				if(jb[s].isSelected())
		    				{
		    					flag = 1;
		    					ans0 = jb[s].getText();
		    				}
		    		}
	    			if (flag == 0)
	    			{
	    				ans0 = "Below 30";
	    			}
	    		}
	    		if (current == 1)
	    		{
	    			flag = 0;
	    			for( s = 0; s < 5; s++)
		    		{
		    				if(jb[s].isSelected())
		    				{
		    					flag = 1;
		    					ans1 = jb[s].getText();
		    				}
		    		}
	    			if (flag == 0)
	    			{
	    				ans1 = "50K - 1Lac";
	    			}
	    		}
	    		if (current == 2)
	    		{
	    			flag = 0;
	    			for( s = 0; s < 5; s++)
		    		{
		    				if(jb[s].isSelected())
		    				{
		    					flag = 1;
		    					ans2 = jb[s].getText();
		    				}
		    		}
	    			if (flag == 0)
	    			{
	    				ans2 = "Medium";
	    			}
	    		}
	    		if (current == 3)
	    		{
	    			flag = 0;
	    			for( s = 0; s < 5; s++)
		    		{
		    				if(jb[s].isSelected())
		    				{
		    					flag = 1;
		    					ans3 = jb[s].getText();
		    				}
		    		}
	    			if (flag == 0)
	    			{
	    				ans3 = "5% - 10%";
	    			}
	    		}
	    		if(current == 4)
	    		{
	    			flag = 0;
	    			for( s = 0; s < 5; s++)
		    		{
		    				if(jb[s].isSelected())
		    				{
		    					flag = 1;
		    					ans4 = jb[s].getText();
		    				}
		    		}
	    			if (flag == 0)
	    			{
	    				ans4 = "Less than 1 yr";
	    			}

	    		}
	    }
	    
	    private void storeinfile()throws IOException
	    {
	    	try
	    	{
	    		FileWriter fw = new FileWriter("/home/admin123/test/src/tablefinal.txt",true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				
				//QUESTION 1
				if(ans0.equals("Below 30") || ans0.equals("31 to 40"))
    			{
    				pw.print("\nYoung\t"); 
    				a00 = "Young";
    				threshold ++;
    			}
    			else if (ans0.equals("41 to 50") || ans0.equals("51 to 65"))
    			{
    				pw.print("\nMiddle\t"); 
    				a00 = "Middle";
    				threshold += 0.5;
    			}
    			else
    			{
    				pw.print("\nOld\t"); 
    				a00 = "Old";
    				threshold-- ; 
    			}
				
				
				
				//QUESTION 2
				if (ans1.equals("Less than 50K") )
				{
    				pw.print("less_than_50k\t"); 
    				a11 = "less_than_50k";
    				threshold --; 
    			}
    			else if (ans1.equals("50K - 1Lac"))
    			{	pw.print("50k-1lac\t");
    				a11 = "50k-1lac";
    				threshold-= 0.5;  
    			}
    			else if(ans1.equals("1Lac - 3Lac"))
    			{	
    				pw.print("1-3lac\t");
    				a11="1-3lac";
    				threshold+=0.5;      
    			}
    			else
    			{	
    				pw.print("more_than_3lac\t"); 
    				a11 = "more_than_3lac";
    				threshold++; 
    			}
				
				
				
				
				
				//QUESTION 3
				if (ans2.equals("Low"))
    			{	pw.print("Low\t"); 
    				a22 = "Low";
    				threshold--;  
    			}
      			else if(ans2.equals("Medium"))
      			{
      				pw.print("Medium\t");
      				a22 = "Medium";
      				threshold += 0.5;
      			}
      			else if (ans2.equals("High"))
    			{	pw.print("High\t"); 
    				a22 = "High";
    				threshold++;      
    			}
				
				
				
				
				//QUESTION 4
				if (ans3.equals("Less than 5%"))
    			{	
    				pw.print("less_than_5%\t");
    				a33 = "less_than_5%";
    				threshold-- ; 
    			}
    			else if (ans3.equals("5% - 10%"))
    			{	
    				pw.print("5%-10%\t"); 
    				a33 = "5%-10%";
    				threshold += 0.5;
    			}
    			else
    			{	pw.print("more_than_10%\t");
    				a33 = "more_than_10%";
    				threshold ++; 
    			}
				
				
				
				
				//QUESTION 5
				if (ans4.equals("Less than 1 yr"))
    			{	
    				pw.print("less_than_1yr\t"); 
    				a44 = "less_than_1yr";
    				threshold-- ; 
    			}
    			else if (ans4.equals("1yr - 3 yr"))
    			{	
    				pw.print("1-3yr\t"); 
    				a44 = "1-3yr";
    				threshold += 0.5;
    			}
    			else
    			{	pw.print("more_than_3yr\t");
    				a44 = "more_than_3yr";
    				threshold ++; 
    			}
				
				
				
				if (threshold >= 1.5)
					pw.print("Yes\t");  
				else
					pw.print("No\t");   	
				
				pw.close();
	    	}
	    	catch(Exception e1)
	    	{
	    		System.out.println(e1);
	    	}
	    }
	    public static String fileToPath(String filename) throws UnsupportedEncodingException
		{
			URL url = questionsdisp.class.getResource(filename);
			 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
		}
	    public static void main(String s[])  
	    {  
	        new questionsdisp("QUESTIONARRIE");  
	  
		
		}
}
