package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Test {
	public static void main(String[] args) throws SQLException, IOException {
		String path="/Users/Qi/Documents/workspace/QuizSystem/WebContent/class/BankAccount.java";
		BufferedReader br1 = new BufferedReader(new FileReader(path));
		System.out.println(new File(path).exists());
		String fileLine = null;
		String extraCode="";
		while ((fileLine = br1.readLine()) != null) {
			extraCode += fileLine + "\n";
		}
		System.out.println(extraCode);
	}
}
