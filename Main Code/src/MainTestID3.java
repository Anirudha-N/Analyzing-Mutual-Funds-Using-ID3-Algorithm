import java.io.*;
import java.io.UnsupportedEncodingException;
import java.net.URL;
public class MainTestID3 
{

	public static void main(String [] arg) throws IOException
	{
		// Read input file and run algorithm to create a decision tree
		algoID3 algo = new algoID3();
		improvedID3 id3 = new improvedID3();
		// There is three parameters:
		// - a file path
		// - the "target attribute that should be used to create the decision tree
		// - the separator that was used in the 
		//file to separate values (by default it is a space)
		decisionTree tree = algo.runAlgorithm(fileToPath("tablefinal.txt"), "mf", "\t");
		algo.printStatistics();
		tree.print();
		
		decisionTree tree1 = id3.runAlgorithm(fileToPath("tablefinal.txt"), "mf", "\t");
		id3.printStatistics();
		tree1.print();
		//tree1.printMutual_Funds();
	}

	public static String fileToPath(String filename) throws UnsupportedEncodingException
	{
		URL url = MainTestID3.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
