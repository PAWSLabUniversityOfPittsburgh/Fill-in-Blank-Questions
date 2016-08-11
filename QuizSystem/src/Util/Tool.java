package Util;

import java.util.ArrayList;
import java.util.List;

public class Tool {
	/**
	 * split the whole code line by line
	 * @param code
	 * @return An arrayList whose each element is one line of the code
	 */
	public static List<Integer> getAllSemicolon(String code){
		List<Integer> list=new ArrayList<>();
		list.add(0);
		for(int i=0;i<code.length();i++){
			if(code.charAt(i)=='\n'||code.charAt(i)=='\r'){
				list.add(i);
			}
		}
		return list;
	}
	/**
	 * calculate the number of space at the head of the code which we need to fill
	 * @param string
	 * @return
	 */
	
	public static int calcualteNumberofSpace(String string){
		int count=0;
		if(string.startsWith(" ")){
			for(int i=0;i<string.length();i++){
				if(string.charAt(i)==' '){
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}
	/**
	 * calculate the number of Tab at the head of the code which we need to fill
	 * @param string
	 * @return
	 */
	public static int calcualteNumberofTab(String string){
		int count=0;
		if(string.startsWith(" ")){
			for(int i=0;i<string.length();i++){
				if(string.charAt(i)=='\t'){
					count++;
				}else{
					break;
				}
			}
		}
		return count;
	}
}
