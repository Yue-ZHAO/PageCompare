# PageCompare

This is the 3rd program which is used to compare the target page to the historical pages to tag the timestamps for the paragraphs. 

## How to use
1. Compile
```mvn compile```

2. Package
```mvn clean package -Dmaven.test.skip=true```  
Skip the test because the AppTest.class is not implemented.

3. Run
```java -jar PageCompare.jar /CluewebDocFolder /HistoricalDocFolder /ResultFolder [Length Threshold] [Similarity Threshold] ```

/CluewebDocFolder is the file path of the folder whose files are clueweb docs need to be tagged.  

/HistoricalDocFolder is the file path of the folder who has all the historical docs crawled by [IA-Downloader](https://github.com/Yue-ZHAO/IA-Downloader).  

/ResultFolder is the file path of the folder who contains the tagged result.  

Length Threshold is the length threshold of sub-documents. Only sub-docs whose lengths are longer than the threshold are considered informative.  

Similarity Threshold is the similarity threshold of the comparison. if the similarity of two sub-docs is higher than the threshold, we think they are the same. The comparison method is JaroWinklerTFIDF is [SecondString](secondstring.sourceforge.net)


## Example
1. ```/Clueweb12_Crawled/clueweb12-0000tw/ /AWSCrawled/Disk1_ClueWeb12_00_0000tw-CRAWLED/ /Desktop/TestFolder 50 0.7 ```

## Update

### 0.1.2
1. Add the procedure to deal with the historical page files who are gzipped.

### 0.1.1
1. Change the content extracting algorithm by using br2nl method.  
Now, If we set the length threshold as 1, more than 95% pages can be extracted more than 99% content on the pages.
