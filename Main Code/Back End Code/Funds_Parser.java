import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Funds_Parser
{

    public static void main( String [] args )
    {
    
    	int n,i,check;
    	List<scname> list = new ArrayList<>();
      
        try
        {
            // create a Buffered Reader object instance with a FileReader
            BufferedReader br = new BufferedReader(new FileReader("010916.txt"));

	    String fileRead = br.readLine();

            for(i=0;i<4;i++)
            	fileRead = br.readLine();

            // loop until all lines are read
            while (fileRead != null)
            {

		check = 0;
                // use string.split to split the lines  
                String tokenize[] = fileRead.split(";");
                if ((tokenize.length) == 1)
                	check = 1;
                if (check == 0)
                {	
		      String schemecode = tokenize[0];	
 	              String schemename = tokenize[3];
	             	
        	      scname tempObj = new scname(schemecode,schemename);
		    // add to array list
        	      list.add(tempObj);

        	 }
        	 
        	
        	fileRead = br.readLine();
                if ("".equals(fileRead))
                	fileRead = br.readLine();
            }

            // close file stream
            br.close();
        }

        // handle exceptions
        catch (FileNotFoundException fnfe)
        {
            System.out.println("file not found");
        }

        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

    try
    {
	PrintWriter pw = new PrintWriter ("funds.txt", "UTF-8");
        
        for (scname each : list)
        {
           pw.println(each);
           
        }
        pw.close();
        
        
    }
    
    catch (IOException e)
    {
    	
    }
    }

}
