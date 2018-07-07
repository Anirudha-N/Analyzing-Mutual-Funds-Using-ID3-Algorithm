import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class DownloadJob implements Job {
     @Override
     public void execute(JobExecutionContext arg0) throws JobExecutionException{
    	 
    	 try
    	 {
    		 String fileName = "down.txt"; //The file that will be saved on your computer
    		 URL link = new URL("https://www.amfiindia.com/spages/NAVAll.txt"); //The file that you want to download
    		
         //Code to download
    		 InputStream in = new BufferedInputStream(link.openStream());
    		 ByteArrayOutputStream out = new ByteArrayOutputStream();
    		 byte[] buf = new byte[1024];
    		 int n = 0;
    		 while (-1!=(n=in.read(buf)))
    		 {
    		    out.write(buf, 0, n);
    		 }
    		 out.close();
    		 in.close();
    		 byte[] response = out.toByteArray();
     
    		 FileOutputStream fos = new FileOutputStream(fileName);
    		 fos.write(response);
    		 fos.close();
         //End download code
    		 
    		 System.out.println("Finished");
    	 }
    	 catch(IOException  e)
    	 {
    		 System.out.println("File cannot download");
    	 }  	 
    }
 }
