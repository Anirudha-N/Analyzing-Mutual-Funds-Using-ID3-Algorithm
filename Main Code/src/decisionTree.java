import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
public class decisionTree 
{
	private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
	//List of all attributes in the training data Set
	String [] allAttributes;
	String node_attribute[] = new String[40];
	String duration[] = new String[]{"less_than_1yr" , "1-3yr" , "more_than_3yr"};
	String onetothree[] = new String[10];
	String oneyrless[] = new String[10];
	node root = null;
	int s = 0;
	int j=0;
	int n = 0;
	
	/**
	 * Print the tree to System.out.
	 */
	public void print() 
	{
		System.out.println("DECISION TREE");
		String indent = " ";
		print(root, indent, "");
	}

	/**
	 * Print a sub-tree to System.out 
	 */
	
	private void print(node nodeToPrint, String indent, String value)
	{
		if(value.isEmpty() == false)
		{
			System.out.println(indent + value);
			node_attribute[s++] = value;
			//node_attribute[s++] = "null";
		}

		String newIndent = indent + "  ";

		// if it is a class node
		if(nodeToPrint instanceof classNode)
		{
			// cast to a class node and print it
			classNode node = (classNode) nodeToPrint;
			System.out.println(newIndent + "  ="+ node.className);
			node_attribute[s++] = node.className;
			//node_attribute[s++] = "null";
		}
		else
		{
			// if it is a decision node, cast it to a decision node
			// and print it.
			//s++;
			decisionNode n1 = (decisionNode) nodeToPrint;
			System.out.println(newIndent + allAttributes[n1.idAttribute] + "->");
			n = n1.idAttribute;
			node_attribute[s++] = allAttributes[n];
			
			newIndent = 			newIndent + "  ";
			// then recursively call the method for subtrees
			for(int i=0; i< n1.nodes.length; i++)
			{
				print(n1.nodes[i], newIndent, n1.attributeval[i]);
				//node_attribute[s++] = "null";
			}
			//node_attribute[s++] = "null";
		}
		
		
	}
	
	public void printMutual_Funds(String ans0,String ans1,String ans2,String ans3,String ans4)
	{
		
		
		try 
		{
			print(ans0,ans1,ans2,ans3,ans4);
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//for(int j=0;j<s;j++)
			//System.out.println("Node is: " + node_attribute[j]);
		//split_Array();
	}
	int status = 0;
	int status1 = 0;
	
	private void print(String Age, String Amt, String Risk, String RateOfReturn, String Tenure )throws Exception
	{
		//String newline = 
		JFrame dispmf = new JFrame ("MUTUAL FUNDS");
		//dispmf.setBackground(Color.MAGENTA);
		JLabel l = new JLabel("YOUR CHOICES FOR INVESTMENT ARE AS FOLLOWS: ");
		l.setFont(new Font("Courier New", Font.BOLD,24));
		//l.setText("\n");
		JLabel []names = new JLabel[50];
		int cnames = 0;
		dispmf.add(l);
		l.setBounds(30, 10, 800,100);
		for(int k = 0; k < 50; k++ )
        {
        	names[k]= new JLabel();
        	 dispmf.add(names[k]);
        } 
		 for(int p=100,q=0;p<=600;p+=30,q++)
		 {
			 names[q].setBounds(30,40+p,600,20);
		 }
		dispmf.getContentPane().setBackground(Color.WHITE);
		dispmf.setLayout(null);  
		dispmf.setLocation(500,400);  
		dispmf.setVisible(true);  
		dispmf.setSize(800,500);
		System.out.println();
		//System.out.println("THE SUGGESTED MUTUAL FUNDS ARE :");
		try 
        {
                // load the MySQL driver.
                Class.forName("com.mysql.jdbc.Driver");
                
                // Setup the connection with the DB
                connect = DriverManager.getConnection("jdbc:mysql://localhost/MF_Schema?"
                                                + "user=root&password=ssa");

                // Statements to issue SQL queries to the database
                statement = connect.createStatement();                
                
        } 
        catch (Exception e) 
        {
        	throw e;
        } 
		String root_node = node_attribute[0];
		if (root_node.equals("tenure"))
		{
			for(int i=1 ; i<s ; i++)
			{
				if(node_attribute[i].equals(Tenure))
				{
					status = i;
					//System.out.println(status);
				}
			}
		}
		//root_node = node_attribute[status+1];
		for (int i = status+1 ; i<s ; i++)
		{
				if(node_attribute[i].equals(duration[0]) || node_attribute[i].equals(duration[1])|| node_attribute[i].equals(duration[2]) )
			{
				status1 = i;
				//System.out.println(status1);
				break;
			}
				else
					status1 = s;
		}
		if ((status1 - status) == 2)
		{
			System.out.println("select quesry for " + node_attribute[status]);
		}
		else
		{		
		for (int i=status+1 ; i<status1 ;i++)
		{
			root_node = node_attribute[i];
			if (root_node.equals(Age))
			{
				if(Age == "Old")
				{
					try
					{
									resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
									"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
									"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
									"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
									"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
									"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'ELSS'\n" + 
									"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
									"order by rand()\n" + 
									"limit 5;");
							while (resultSet.next()&& cnames != 50) 
							{ 
								
				                String SName = resultSet.getString("Fund_Name");
				                names[cnames].setText(SName);
				                //System.out.println(SName);
				                cnames++;
							}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        }
				}
				if (Age == "Middle")
				{
					try
					{
									resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
									"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
									"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
									"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
									"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
									"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Money Market'\n" + 
									"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
									"order by rand()\n" + 
									"limit 5;");
							while (resultSet.next()&& cnames != 50) 
							{ 
								
				                String SName = resultSet.getString("Fund_Name");
				                names[cnames].setText(SName);
				                //System.out.println(SName);
				                cnames++;
							}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        }
				}
				if (Age == "Young")
				{
					try
					{
									resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
									"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
									"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
									"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
									"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Close Ended Schemes'\n" + 
									"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Growth'\n" + 
									"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
									"order by rand()\n" + 
									"limit 5;");
							while (resultSet.next()&& cnames != 50) 
							{ 
								
				                String SName = resultSet.getString("Fund_Name");
				                names[cnames].setText(SName);
				               // System.out.println(SName);
				                cnames++;
							}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        }
				}
				
			}
				
			if (root_node.equals(Amt))
			{
				if (Amt == "less_than_50k")
				{
					try
					{
									resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
									"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
									"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
									"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
									"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
									"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Balanced'\n" + 
									"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
									"order by rand()\n" + 
									"limit 5;");
							while (resultSet.next()&& cnames != 50) 
							{ 
								
				                String SName = resultSet.getString("Fund_Name");
				                names[cnames].setText(SName);
				              // System.out.println(SName);
				                cnames++;
							}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 
					
				}
				if (Amt == "50k-1lac")
				{
					try
					{
						resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
								"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
								"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
								"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
								"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
								"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'GOLD ETFs'\n" + 
								"or MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Income'\n" + 
								"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
								"order by rand()\n" + 
								"limit 5;");
						while (resultSet.next()&& cnames != 50) 
						{ 
							
			                String SName = resultSet.getString("Fund_Name");
			                names[cnames].setText(SName);
			                //System.out.println(SName);
			                cnames++;
						}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 	
					
				}
				if (Amt == "1-3lac")
				{
					try
					{
						resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
								"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
								"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
								"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
								"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
								"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'GOLD ETFs'\n" + 
								"or MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Income'\n" + 
								"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
								"order by rand()\n" + 
								"limit 5;");
						while (resultSet.next()&& cnames != 50) 
						{ 
							
			                String SName = resultSet.getString("Fund_Name");
			                names[cnames].setText(SName);
			                //System.out.println(SName);
			                cnames++;
						}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 	
				}
				if(Amt == "more_than_3lac")
				{
					try
					{
									resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
									"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
									"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
									"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
									"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
									"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Balanced'\n" + 
									"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
									"order by rand()\n" + 
									"limit 5;");
							while (resultSet.next()&& cnames != 50) 
							{ 
								
				                String SName = resultSet.getString("Fund_Name");
				                names[cnames].setText(SName);
				               //System.out.println(SName);
				                cnames++;
							}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 
				}
			}
			
			/// CONSIDERING RISK 
			if (root_node.equals(Risk))
			{
				if(Risk == "High")
				{
						try
						{
										resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
										"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
										"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
										"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
										"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
										"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Floating Rate'\n" + 
										"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
										"order by rand()\n" + 
										"limit 5;");
								while (resultSet.next() && cnames != 50) 
								{ 
										
						                String SName = resultSet.getString("Fund_Name");
						                names[cnames].setText(SName);
						               // System.out.println(SName);
						                cnames++;
						        }
						}
						catch (Exception e) 
				        {
				        	throw e;
				        } 
				}
				if(Risk == "Medium")
				{
					try
					{
						resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
								"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
								"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
								"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
								"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
								"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'ELSS'\n" + 
								"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
								"order by rand()\n" + 
								"limit 5;");
						while (resultSet.next() && cnames != 50) 
						{ 
							
			                String SName = resultSet.getString("Fund_Name");
			                names[cnames].setText(SName);
			                //System.out.println(SName);
			                cnames++;
						}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 	
				}
				if (Risk == "Low")
				{
					try
					{
									resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
									"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
									"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
									"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
									"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
									"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Balanced'\n" + 
									"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
									"order by rand()\n" + 
									"limit 5;");
							while (resultSet.next()&& cnames != 50) 
							{ 
								
				                String SName = resultSet.getString("Fund_Name");
				                names[cnames].setText(SName);
				                //System.out.println(SName);
				                cnames++;
							}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 
					
				}
			}
			
			
			/// CONSIDERING RATEOFRETURN
			if (root_node.equals(RateOfReturn))
			{
				if (RateOfReturn == "less_than_1yr")
				{
					try
					{
					resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
							"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
							"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
							"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
							"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
							"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Gilt'\n" + 
							"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
							"order by rand()\n" + 
							"limit 5;");
					while (resultSet.next()&& cnames != 50) 
					{ 
						
		                String SName = resultSet.getString("Fund_Name");
		                names[cnames].setText(SName);
		               //System.out.println(SName);
		                cnames++;
					}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        }
				}
				if (RateOfReturn == "1-3yr")
				{
					try
					{
						resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
								"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
								"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
								"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
								"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
								"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'GOLD ETFs'\n" + 
								"or MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'Income'\n" + 
								"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
								"order by rand()\n" + 
								"limit 5;");
						while (resultSet.next()&& cnames != 50) 
						{ 
							
			                String SName = resultSet.getString("Fund_Name");
			                names[cnames].setText(SName);
			               // System.out.println(SName);
			                cnames++;
						}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 		
				}
				
				if (RateOfReturn ==  "more_than_3yr")
				{
					try
					{
						resultSet = statement.executeQuery("Select MF_Schema.MF_Funds.* \n" + 
								"from MF_Schema.MF_Types, MF_Schema.MF_Schemes, MF_Schema.MF_Scheme_Types,MF_Schema.MF_Funds\n" + 
								"where MF_Schema.MF_Types.`Scheme_Id` = MF_Schema.MF_Schemes.`Scheme_Id`\n" + 
								"and MF_Schema.MF_Types.`Scheme_Type_Id` = MF_Schema.MF_Scheme_Types.`Scheme_Type_Id`\n" + 
								"and MF_Schema.MF_Schemes.`Scheme_Name`= 'Open Ended Schemes'\n" + 
								"and MF_Schema.MF_Scheme_Types.`Scheme_Type_Name`= 'ELSS'\n" + 
								"and MF_Schema.MF_Funds.`Type_Id` = MF_Schema.MF_Types.`Type_Id`\n" + 
								"order by rand()\n" + 
								"limit 5;");
						while (resultSet.next()&& cnames != 50) 
						{ 
							
			                String SName = resultSet.getString("Fund_Name");
			                names[cnames].setText(SName);
			               //System.out.println(SName);
			                cnames++;
						}
					}
					catch (Exception e) 
			        {
			        	throw e;
			        } 	
				
					
				}
			
		}
			//System.out.println(node_attribute[i]);
		
		//root_node = node_attribute[status+1];
		
		
	}
	}
		}
}
	/*private void close() {
        try 
        {
                if (resultSet != null) 
                {
                        resultSet.close();
                }

                if (statement != null) 
                {
                        statement.close();
                }

                if (connect != null) 
                {
                        connect.close();
                }
        } 
        catch (Exception e) 
        {

        }*/



