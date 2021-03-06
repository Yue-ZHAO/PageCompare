package com.zhao.temporal.PageCompare;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.zhao.temporal.Utils.CluewebFileProcess;
import com.zhao.temporal.Utils.FileProcess;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NoSuchAlgorithmException, IOException, BoilerpipeProcessingException
    {
    	//	1. Get the folder of target pages
    	String folderPath_TargetPage = args[0];
    	File folder_TargetPage = new File(folderPath_TargetPage);
    	System.out.println(args[0]);
    	
    	//	2. Get the root folder of historical pages
    	String rootFolderPath_HistoricalPage = args[1];
    	File rootFolder_HistoricalPage = new File(rootFolderPath_HistoricalPage);
    	System.out.println(args[1]);
    	
		//	3. Set the output folder for the compared pages and their feature file
		String folderPath_TaggedPage = args[2];
		File folder_TaggedPage = new File(folderPath_TaggedPage);
		if (!folder_TaggedPage.exists() || !folder_TaggedPage.isDirectory())
			folder_TaggedPage.mkdir();
		System.out.println(args[2]);
		
		File file_TargetPageFeatures = new File(folder_TaggedPage, "TargetPageFeatures");
		
		//	4. Threshold
		int lenThreshold = Integer.parseInt(args[3]);
		System.out.println(lenThreshold);
		double simThreshold = Double.parseDouble(args[4]);
		System.out.println(simThreshold);
        
    	//	3. 	process each file in the folder of target pages
    	File[] fileList_TargetPage = folder_TargetPage.listFiles();
    	for (File file_TargetPage: fileList_TargetPage) {

    		//	TO MAKE SURE THE FILE IS THE CLUEWEB PAGE WE NEED
    		if (!file_TargetPage.getName().startsWith("clueweb12"))
    			continue;
    		
    		//	3.0	output the signal to screen
    		System.out.println();
			System.out.println( "Start: " + file_TargetPage.getAbsolutePath());
    	
    		//	3.1	process the file to get path, url, timestamp
    		String filePath_TargetPage = file_TargetPage.getAbsolutePath();
    		String url = CluewebFileProcess.readURLFromCluewebFile(filePath_TargetPage);

    		//		implement the function to extract the timestamp
    		String timestamp_TargetPage = CluewebFileProcess.readTimeFromCluewebFile(filePath_TargetPage);
    		
    		//	3.2 generate TargetPage targetPage(path, url, timestamp)
    		TargetPage targetPage = new TargetPage(filePath_TargetPage, url, timestamp_TargetPage, lenThreshold, simThreshold);
    		
    		//	3.3	find the historical folder of the target page, if not, continue, else    		
			File folder_HistoricalPage = new File(rootFolder_HistoricalPage, targetPage.MD5Code);		    		
    		    		
    		//File folder_HistoricalPage = new File(folderPath_HistoricalPage);
    		if (folder_HistoricalPage.exists()) { 		
    			//	3.3.1	for each file in the historical folder
    			File[] fileList_HistoricalPage = folder_HistoricalPage.listFiles();
    			
    			for (File file_HistoricalPage: fileList_HistoricalPage) {
    				//  For the html file
    				//	String Suffix_HistoricalPage = file_HistoricalPage.getName().substring(file_HistoricalPage.getName().lastIndexOf(".")+1);
    				//	Suffix_HistoricalPage.endsWith("html");
    			
    				//	Make sure the file we check is a historical file
    				//	If the name of the file shorter than 8, skip
    				//	Else if the substring(0, 8) of the name is not numbers, skip
    				if (file_HistoricalPage.getName().length() < 8)
    					continue;
    				else {
    					String histFileNameString = file_HistoricalPage.getName().substring(0, 8);
    					if (!histFileNameString.matches("^[0-9]*$"))
    						continue;
    				}
    				
    				//	3.3.1.1	process to get path, timestamp
    				String filePath_HistoricalPage = file_HistoricalPage.getAbsolutePath();
    				//		for historical page, their time stamp = their name
    				//		change the format of the timestamp to year-month-day
    				String timeTemp = file_HistoricalPage.getName().substring(0, 4) + "-"
    								+ file_HistoricalPage.getName().substring(4, 6) + "-"
    								+ file_HistoricalPage.getName().substring(6, 8);
    				String timestamp_HistoricalPage = timeTemp;
    				
    				//	3.3.1.2	generate HistoricalPage historicalPage(path, url, timestamp)
    				HistoricalPage historicalPage = new HistoricalPage(filePath_HistoricalPage, url, timestamp_HistoricalPage, lenThreshold);   				
    	
    				//	3.3.1.3	targetPage.pageTag(historicalPage, 0.7)
    				targetPage.pageTag(historicalPage, simThreshold);
    			}
    		
    			//	3.3.2	output the features of targetPage to the feature file
    			String feature_TargetPage = targetPage.featureToString();
    			FileProcess.addLinetoaFile(feature_TargetPage, file_TargetPageFeatures.getAbsolutePath());
    			
    			//	3.3.3	output the tagged target page to a file
    			String filename_TaggedTargetPage = file_TargetPage.getName() + "_" + targetPage.MD5Code;
    			File file_TaggedTargetPage = new File (folder_TaggedPage, filename_TaggedTargetPage);
    			targetPage.toFile(file_TaggedTargetPage);
    	
    			//	3.3.4	output the signal to screen
    			System.out.println( "Finish: " + file_TargetPage.getAbsolutePath());
    		} else {
    			System.out.println( "Not Find: " + file_TargetPage.getAbsolutePath());
    		}
    	
    	}
    	//	4.	output "finished"    	
    	System.out.println( "All Finish!" );
    }
}
