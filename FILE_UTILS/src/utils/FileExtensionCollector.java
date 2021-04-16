package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileExtensionCollector {
	//변환할 대상 파일 확장자 리스트
	public static Set<String> extensionSet = new HashSet<String>();
	
	public static String FILE_PATH = "C:\\Users\\SBK\\Pictures\\redis";
	public static void main(String[] args) {
		FILE_PATH = FILE_PATH.replace("\\", "/");
		
		File file = new File(FILE_PATH);
		getFileList(file);
		
		extensionSet.forEach(extensionNm -> System.out.println("확장자명 : " + extensionNm));
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
			
			addExtensionSet(tempFile);
		}
	}
	
	public static void addExtensionSet(File file){
		if(file.isFile()) {
			String fileName = file.getName();
			int lastIndexOfDot = fileName.lastIndexOf(".");
			String extensionName = fileName.substring(lastIndexOfDot+1);
			
			extensionSet.add(extensionName);
		}
	}
}
