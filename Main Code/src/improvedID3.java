import java.io.*;
import java.util.*;
import java.util.Map.Entry;


// Implementing the algorithm
public class improvedID3
{
	//list of all the attributes
	private String [] allAttributes;
	// Storing the index of the target attribute
	private int indexTargetAttribute = -1;
	// The set of all the 'values' the target attribute is going to have
	private Set<String> targetAttributeValues = new HashSet<String>();
	
	private long startTime; // start time of the latest execution
	private long endTime;   // end time of the latest execution
	
	// function that creates the decision tree 
	public decisionTree runAlgorithm(String ipPath, String tAttribute, String seperator)throws IOException
	{
		startTime = System.currentTimeMillis();
	
		//Initially create an Empty Decision Tree
		decisionTree  dt = new decisionTree();
		
		//Reading the first input line, that line will always contain the names of the attributes.
		BufferedReader br = new BufferedReader(new FileReader(ipPath));		
		String line = br.readLine();
		
		// As the first line contains the names of all the attributes we will store those names in allAttributes
		allAttributes = line.split(seperator);
		
		// STEP - 1
		//Now we need to store the position of remaining attributes, hence we use an integer array
		//loops to fill in the data in the arrays
		int [] indexesOfRemainingAttributes = new int [allAttributes.length - 1];
		int pos = 0; 
		//accessing each attribute
		for (int i = 0; i < allAttributes.length; i++)
		{
			if(allAttributes[i].equals(tAttribute))
			{
				//saving the index of the target attribute
				indexTargetAttribute = i;
			}
			else
			{
				indexesOfRemainingAttributes[pos++]= i;
			}
			
		}
		// After storing the names, the next step is to store the instances into some data structure, we store them into List
		List<String[]> instances = new ArrayList<String[]>();
		while (((line = br.readLine()) != null))
		{ 
			// if the line is  a comment, is  empty or is a kind of metadata
			if (line.isEmpty() == true || line.charAt(0) == '#' || line.charAt(0) == '%'|| line.charAt(0) == '@') 
			{
				continue;
			}
			
			// split the line of instance
			String[] lineSplit = line.split(seperator);
			// process the instance
			instances.add(lineSplit);
			// remember the value for the target attribute, we need to store them
			targetAttributeValues.add(lineSplit[indexTargetAttribute]);
			//System.out.println(lineSplit[indexTargetAttribute]);  // contents of target attribute 
			
		}
		//System.out.println();
		
		br.close();
		
		
		//Now we need to create the tree , once all the required data structures are created
		//STEP-2
	//try
		//{
			dt.root = computeID3(indexesOfRemainingAttributes, instances);
		//}
		
		//catch (NullPointerException e)
		//{
		//System.out.println(e);
		//}
			dt.allAttributes = allAttributes;
			endTime = System.currentTimeMillis();  // record end time	
		return dt;
	}
	
	public node computeID3(int []indexesOfRemainingAttributes, List<String[]> instances)
	{
			if (indexesOfRemainingAttributes.length == 0)
			{
				// Count the frequency of class
				Map<String, Integer> targetValuesFrequency = calculateFrequencyOfAttributeValues(instances, indexTargetAttribute);
				//System.out.println(targetValuesFrequency.entrySet());
				
				// Loop over the values to find the class with the highest frequency
				int highestCount = 0;
				String highestName = "";
				
				System.out.println("in computeID3\n");
				for (Entry<String, Integer> entry : targetValuesFrequency.entrySet()) 
				{
					// if the frequency is higher
					if (entry.getValue() > highestCount) 
					{
						highestCount = entry.getValue();
						highestName = entry.getKey();
	 				}
				}
	//			System.out.println(highestCount + highestName);
				// return a class node with the value having the highest frequency
				 classNode cn = new classNode();
				cn.className = highestName;
				return cn;
			}
				// Calculate the frequency of each target attribute value and
				Map<String, Integer> targetValuesFrequency = calculateFrequencyOfAttributeValues(instances, indexTargetAttribute);
			
			if (targetValuesFrequency.entrySet().size() == 1) 
			{
					classNode cn1 = new classNode();
					cn1.className = (String) targetValuesFrequency.keySet().toArray()[0];
					return cn1;
			}
				//Now calculate the Global Entropy
				float globalEntropy = 0;
				for (String value : targetAttributeValues) 
				{
					Integer freqVal = targetValuesFrequency.get(value);
					//System.out.println(freqVal);
					// if the frequency is not zero, then only we will compute further
					if(freqVal != null)
					{
						double frequencyDouble = freqVal / (double) instances.size();
						globalEntropy -= frequencyDouble * Math.log(frequencyDouble) / Math.log(2);
					}
				}
				/*Select the attribute from remaining attributes such that if we split the dataset on this
				attribute, we will get the higher information gain*/
				
				
				int attributeWithHighestGain = 0; //stores the index of the attribute which is possessing the highest gain 
				double highestGain = -99999; //stores the highest value of the gain obtained
				float assFunc[] = new float [30];
				int i = 0;
				float v =0;
				float totalv =0 ;
				double mult = 0;
				
				for (int attributePos : indexesOfRemainingAttributes) 
				{
				//	System.out.println("Attribute pos: " + attributePos);
					v = 0;
					assFunc[attributePos] = calculateAssoFunction(attributePos, instances);
					//v += assFunc[i]; 
					totalv += assFunc[attributePos];
					//System.out.println("Summation(total):  " + assFunc[attributePos]);
					i++;
				}
		//		System.out.println("toatl V(k):  " + totalv);
				//double maxv = -99.99999;
				float vattr[] = new float [10];
				float vk;
				for (int j=0; j< i ; j++ )
				{
					vattr[j] = assFunc[j] / totalv;
			//		System.out.println("Individual v(k): " + vattr[j]);
					j++;
				}
				
				//
				
				int j=0;
				for (int attributePos : indexesOfRemainingAttributes) 
				{
					
					float gain = calculateGain(attributePos, instances, globalEntropy);
					mult = vattr[j] * gain; j++; 
				//	System.out.println("v(k) * gain= " + mult);
					if (mult >= highestGain) 
					{
						highestGain = mult;
						attributeWithHighestGain = attributePos;
					}
				}
				
				if (highestGain == 0)
				{
					classNode cn2 = new classNode();
					// take the most frequent classes
					int topFrequency = 0;
					String className = null;
					for(Entry<String, Integer> entry: targetValuesFrequency.entrySet()) 
					{
						if(entry.getValue() > topFrequency)
						{
							topFrequency = entry.getValue();
							className = entry.getKey();
						}
					}
					cn2.className = className;
					return cn2;
				}
				
				//Create the decision node with highest information gain
				// Create a decision node for the attribute
				//System.out.println("Attribute with highest gain = " + allAttributes[attributeWithHighestGain] + " " + highestGain);
				decisionNode dn = new decisionNode();
				dn.idAttribute = attributeWithHighestGain;
				
				int[] newRemainingAttribute = new int[indexesOfRemainingAttributes.length - 1];
				int pos = 0;
				for (int l = 0; l < indexesOfRemainingAttributes.length; l++) 
				{
					if (indexesOfRemainingAttributes[l] != attributeWithHighestGain) 
					{
						newRemainingAttribute[pos++] = indexesOfRemainingAttributes[l];
					}
				}
				
				// Split the dataset into partitions according to the selected attribute
				Map<String, List<String[]>> partitions = new HashMap<String, List<String[]>>();
				for (String[] instance : instances) 
				{
					String value = instance[attributeWithHighestGain];
					List<String[]> listInstances = partitions.get(value);
					if (listInstances == null)
					{
						listInstances = new ArrayList<String[]>();
						partitions.put(value, listInstances);
					}
					listInstances.add(instance);
				}

				// Create the values for the subnodes
				dn.nodes = new node[partitions.size()];
				dn.attributeval= new String[partitions.size()];

				// For each partition, make a recursive call to create
				// the corresponding branches in the tree.
				int index = 0;
				for (Entry<String, List<String[]>> partition : partitions.entrySet())
				{
					dn.attributeval[index] = partition.getKey();
					//System.out.println("------------------------------------------------------------------------------------------------------------ ");

					dn.nodes[index] = computeID3(newRemainingAttribute,partition.getValue()); // recursive call
					index++;
				}
				//System.out.println("------------------------------------------------------------------------------------------------------------- I AM coming out ");
				// return the root node of the subtree created
				return dn;				
				
	}
	private float calculateAssoFunction(int attributePos, List<String[]> instances)
	{
		int count = 0 ;
		//Map<String, Integer> valuesFrequency = calculateFrequencyOfAttributeValues(instances, attributePos);
		Map<String, Integer> targetValuesFrequency = new HashMap<String, Integer>();
		
		float answer = 0;
		// for each instance of the training set
		for (String[] instance : instances) 
		{
			// get the value of the attribute for that instance
			String targetValue = instance[attributePos];
			//System.out.println("target value= " + targetValue);
			// increase the frequency by 1
			//System.out.println(targetValuesFrequency.get(targetValue));
			if (targetValuesFrequency.get(targetValue) == null) 
			{
				targetValuesFrequency.put(targetValue, 1);
				count++;
			} 
			else 
			{
				targetValuesFrequency.put(targetValue,targetValuesFrequency.get(targetValue) + 1);
			}
			
		}
		//System.out.println("Yes's and no's: ");
		//System.out.println(targetValuesFrequency.entrySet());
		
		for (Entry<String, Integer> entry : targetValuesFrequency.entrySet()) 
		{
			// make the sum 
			//sum += entry.getValue()/ ((double) instances.size())* calculateEntropyIfValue(instances, attributePos,entry.getKey());
			answer += calculateSummation(instances, attributePos,entry.getKey(),count);
		}
		return answer;
		
	}
	
	private double calculateSummation(List<String[]> instances,int attributeIF, String valueIF,int count) 
	{
		Map<String, Integer> valuesFrequency = new HashMap<String, Integer>();
		// for each instance
		for (String[] instance : instances)
		{
			// if that instance has the value for the attribute
			if (instance[attributeIF].equals(valueIF)) 
			{
				String targetValue = instance[indexTargetAttribute];
				// increase the frequency
				if (valuesFrequency.get(targetValue) == null) 
				{
					valuesFrequency.put(targetValue, 1);
				} else 
				{
					valuesFrequency.put(targetValue, valuesFrequency.get(targetValue) + 1);
				}
				// increase the number of instance having the value for that
				// attribute
				//instancesCount++; 
				
			}
		}
		//System.out.println("Individual Yes's and no's: ");
		//System.out.println(valuesFrequency.entrySet());
		
			int x[] = new int[2];
			double result = 0;
			int i,g,f;
			i = -1;g =0 ; f = 0;
			//Integer freqVal, freqval1;
			for (String value : targetAttributeValues)
			{  i++;
				if (i == 0)
				{ 	Integer freqVal = valuesFrequency.get(value); 
					//i++;
					if (freqVal != null)
						g = freqVal;
				}
				else if ( i== 1)
				{
					Integer freqval = valuesFrequency.get(value);
					if (freqval != null)
					{
						f = freqval;
					}
				}
			
			}
			
			if (g > f)
			{
				result = (double)(g - f) / count;
			}
			else 
				result = (double)(f - g )/ count;
		
		//System.out.println("Summation is: " + result);
			return result;
	}
	/*FUNCTION TO CALCULATE FREQUESNCY OF ATTRIBUTES:
	 * This will return a map where
	 	keys -- attributes
	 	value -- number of times these attributes appeared in the training data set
	 */
	private Map<String, Integer> calculateFrequencyOfAttributeValues(List<String[]> instances, int indexAttribute) 
	{
		Map<String, Integer> targetValuesFrequency = new HashMap<String, Integer>();
		
		// for each instance of the training set
		for (String[] instance : instances) 
		{
			// get the value of the attribute for that instance
			String targetValue = instance[indexAttribute];
			//System.out.println("target value= " + targetValue);
			// increase the frequency by 1
			//System.out.println(targetValuesFrequency.get(targetValue));
			if (targetValuesFrequency.get(targetValue) == null) 
			{
				targetValuesFrequency.put(targetValue, 1);
			} 
			else 
			{
				targetValuesFrequency.put(targetValue,targetValuesFrequency.get(targetValue) + 1);
			}
			
		}
		//System.out.println(targetValuesFrequency.entrySet());
		// return the map
		return targetValuesFrequency;
	}
	
	
	
	/*FUNCTION TO CALCULATE THE INFORMATION GAIN
	 * 
	 */
	private float calculateGain(int attributePos, List<String[]> instances, float globalEntropy) 
	{
		// Count the frequency of each value for the attribute
		Map<String, Integer> valuesFrequency = calculateFrequencyOfAttributeValues(instances, attributePos);
		// Calculate the gain
		float sum = 0;
		// for each value
		for (Entry<String, Integer> entry : valuesFrequency.entrySet()) 
		{
			// make the sum 
			sum += entry.getValue()/ ((double) instances.size())* calculateEntropyIfValue(instances, attributePos,entry.getKey());
		}
		// subtract the sum from the global entropy
		return globalEntropy - sum;
	}
	
	/* CALCULATE THE ENTROPY OF INDIVIUAL VALUES USEFUL FOR CREATING THE INFORMATION GAINequency
	 * 
	 */
	private double calculateEntropyIfValue(List<String[]> instances,int attributeIF, String valueIF) 
	{
		
		// variable to count the number of instance having the value for that
		// attribute
		int instancesCount = 0;
		// variable to count the frequency of each value
		Map<String, Integer> valuesFrequency = new HashMap<String, Integer>();
		// for each instance
		for (String[] instance : instances)
		{
			// if that instance has the value for the attribute
			if (instance[attributeIF].equals(valueIF)) 
			{
				String targetValue = instance[indexTargetAttribute];
				// increase the frequency
				if (valuesFrequency.get(targetValue) == null) 
				{
					valuesFrequency.put(targetValue, 1);
				} else 
				{
					valuesFrequency.put(targetValue, valuesFrequency.get(targetValue) + 1);
				}
				// increase the number of instance having the value for that
				// attribute
				instancesCount++; 
			}
			
		}
		//System.out.println(valuesFrequency.entrySet());
		// calculate entropy
		double entropy = 0;
		// for each value of the target attribute
		for (String value : targetAttributeValues) 
		{
			// get the frequency
			Integer count = valuesFrequency.get(value);
			// if the frequency is not null
			if (count != null) 
			{
				// update entropy according to the formula
				double frequency = count / (double) instancesCount;
				entropy -= frequency * Math.log(frequency) / Math.log(2);
			}
		} 
		return entropy;
	}
	public void printStatistics() {
		System.out.println("Time to construct decision tree = "
				+ (endTime - startTime) + " ms");
		System.out.println("Target attribute = "
				+ allAttributes[indexTargetAttribute]);
		System.out.print("Other attributes = ");
		for (String attribute : allAttributes) {
			if (!attribute.equals(allAttributes[indexTargetAttribute])) {
				System.out.print(attribute + " ");
			}
		}
		System.out.println();
	
	}
}
