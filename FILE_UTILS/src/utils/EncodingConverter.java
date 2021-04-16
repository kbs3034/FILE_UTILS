package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingConverter {
	public static final Logger logger = LoggerFactory.getLogger(EncodingConverter.class);
	
	//변환할 대상 파일 확장자 리스트
	public static List<String> targetExtensionPattern = Arrays.asList("java","xml","txt","html","jsp","properties");
	
	//euc-kr -> utf-8
	public static final String BEFORE_ENCODING = "EUC-KR";
	public static final String AFTER_ENCODING = "UTF-8";
	
	//utf-8 -> euc-kr
//	public static final String BEFORE_ENCODING = "UTF-8";
//	public static final String AFTER_ENCODING = "EUC-KR";
	
	public static String FILE_PATH = "F:\\test\\";
	
	public static StringBuffer logBuffer = new StringBuffer();
	public static void main(String[] args) {
		FILE_PATH = FILE_PATH.replace("\\", "/");
		
		File file = new File(FILE_PATH);
		getFileList(file);
	}
	
	public static void getFileList(File file) {
		File[] fileList = file.listFiles();
		int fileListLen = fileList.length;
		for(int i = 0; i < fileListLen; i++) {
			File tempFile = fileList[i];
			
			if(tempFile.isDirectory()) {
				getFileList(tempFile);
				continue;
			}
			
			String fileName = tempFile.getName();
			int lastIndexOfDot = fileName.lastIndexOf(".");
			String extensionName = fileName.substring(lastIndexOfDot+1);
			
			if(targetExtensionPattern.contains(extensionName)) {
				convertEncoding(tempFile);
			}
		}
	}
	
	public static void convertEncoding(File file){
		
		String fileName = file.getName();
		File afterFile = new File(file.getAbsolutePath()+"temp");
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),BEFORE_ENCODING));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afterFile), AFTER_ENCODING));
			
			String readLine = null;
			while( ( readLine =  br.readLine()) != null ){
				bw.write(readLine);
				bw.write("\n");
            }
			bw.close();
			br.close();
			
			file.delete();
			afterFile.renameTo(file);
			logger.info("path : {} Result : {}", file.getAbsolutePath(),"Converting Success");
		}catch(IOException e) {
			logger.error("path : {} Result : {}", file.getAbsolutePath(),"Converting Fail");
			logger.error(e.getMessage(), e);
		}finally{
			if(br != null) try {br.close();} catch (IOException e) {}
			if(bw != null) try {bw.close();} catch (IOException e) {}
		}
	}
}
