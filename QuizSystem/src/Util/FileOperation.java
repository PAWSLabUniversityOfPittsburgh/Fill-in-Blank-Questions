package Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletOutputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;

public class FileOperation {
	public static String replace = "___";
	public static char replaceChar = replace.charAt(0);

	/**
	 * to create an action file path so that we can use this file to run code
	 * 
	 * @param appDir
	 *            A string standing for location.(in Web.xml) e.g.
	 *            /Users/Qi/Documents/workspace/QuizPET_20160119/WebContent/
	 * @param compilerHomeDir
	 *            (in Web.xml)
	 * @param usr
	 *            (in Web.xml)
	 * @param window
	 *            true/false. determine this is Win OS or Mac OS?
	 * @return the directory path where we will run the code
	 */
	public static String createActionFilePath(String appDir, String compilerHomeDir, String usr, boolean window) {
		StringBuffer path = new StringBuffer();
		String delim = window ? "\\\\" : "//";
		path.append(appDir).append("online_compile").append(delim);
		// generate session ID (2008.01.09)
		String[] letters = { "A", "B", "C", "D", "E", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		Random randomNumberSession = new Random(); // random number generator
		String SessionLetters = letters[randomNumberSession.nextInt(15)] + letters[randomNumberSession.nextInt(15)]
				+ letters[randomNumberSession.nextInt(15)] + letters[randomNumberSession.nextInt(15)]
				+ letters[randomNumberSession.nextInt(15)] + letters[randomNumberSession.nextInt(15)];
		/*
		 * SID=SessionLetters if (SID == null || SID.equalsIgnoreCase("null") ||
		 * SID.length() == 0 || SID.equals("") || SID.equals("\"\"") ||
		 * SID.equals("''")) { if (verbose) System.out.println(
		 * "Generating SID: SID is '' or empty or null, use SID=dateStr. " +
		 * dateStr); SID = dateStr; } if (usr == null ||
		 * usr.equalsIgnoreCase("null") || usr.length() == 0 || usr.equals("")
		 * || usr.equals("\"\"") || usr.equals("''")) { if (verbose)
		 * System.out.println(
		 * "Generating folderSurfix: usr is '' or empty or null, use SID+dateStr. "
		 * + dateStr); folderSurfix = SID + dateStr; } else{ folderSurfix = usr;
		 * }
		 */
		long date = System.currentTimeMillis();
		String dateStr = String.valueOf(date);
		String folderSurfix;
		if (usr == null || usr.equalsIgnoreCase("null") || usr.length() == 0 || usr.equals("") || usr.equals("\"\"")
				|| usr.equals("''")) {
			// if (verbose)
			// System.out.println("Generating folderSurfix: usr is '' or empty
			// or null, use SID+dateStr. "
			// + dateStr);
			folderSurfix = SessionLetters + dateStr;
		} else {
			folderSurfix = usr;
		}
		path.append(folderSurfix).append(delim);
		return new String(path);
	}

	/**
	 * get the extra class directory for multiclasses
	 * 
	 * @param appDir
	 *            A string standing for location.(in Web.xml) e.g.
	 *            /Users/Qi/Documents/workspace/QuizPET_20160119/WebContent/
	 * @param window
	 *            true/false. determine this is Win OS or Mac OS?
	 * @return the extra class directory
	 */
	public static String getExtraClassDir4Multiclasses(String appDir, boolean window) {
		String dirDelim = window ? "\\\\" : "//";
		return appDir + "class" + dirDelim;
	}

	/**
	 * 
	 * @param curActionDir:
	 *            the string path from the createActionFile();
	 * @param multiClassFlag:
	 *            multiClassFlag true:it is multiClass. otherwise, it's not.
	 * @param queType
	 * @param codePart
	 *            the code
	 * @param language:
	 *            two options: java and py
	 * @param out:
	 *            to print in the log
	 * @throws IOException
	 * @return testerSurfix
	 * @throws SQLException
	 */
	public static String createTempFile(String curActionDir, boolean multiClassFlag, int queType, String codePart,
			String language, ServletOutputStream out)
					throws IOException {
		long date = System.currentTimeMillis();
		String testerSurfix = String.valueOf(date);
		File curActionD = new File(curActionDir);
		String testerName = (multiClassFlag ? "Tester1" : "Tester");
		if (curActionD.exists() && curActionD.isDirectory()) {
			FileUtils.cleanDirectory(curActionD); // Cleans a directory without
													// deleting it.
			log("INFO: EvaluateAnswer.java cleaning directory: " + curActionD);
		} else if (!curActionD.mkdir())
//			outLog("ERROR: EvaluateAnswer.java directory creation failed: " + curActionDir, out);
			System.err.println("ERROR: EvaluateAnswer.java directory creation failed: " + curActionDir);

		if (codePart.contains("Tester"))
			codePart = codePart.replace("Tester", testerName + testerSurfix);

		String fileName = testerName + testerSurfix + "." + language;
		String finalCodePart = codePart;
		FileWriter writer = new FileWriter(curActionDir + fileName);
		if (queType == 1) {
			if (fileName.endsWith(".py")) {// if (language.equals("py")){
				if (!finalCodePart.endsWith("\n"))
					finalCodePart += "\n";
				finalCodePart += "print result\n";
			} else {
				int codeLen = finalCodePart.length();
				finalCodePart = finalCodePart.substring(0, codeLen - 9) + "System.out.print(result);}}";// "System.out.println(result);}}";
			}
		}
		finalCodePart.replace("\r\n", "\n").replace("\r", "\n").replace("\n\n", "\n");
		writer.write(finalCodePart);
		writer.close();
//		if (multiClassFlag) {
//			for (int i = 0; i < fileNames.size(); i++) {
//				String path=curActionDir + fileNames.get(i).substring(2);
//				System.err.println("path:"+path);
//				FileWriter fw=new FileWriter(path);
//				fw.write(extraCodes[i]);
//				fw.close();
//			}
//		}
		log("INFO: EvaluateAnswer.java writing code to: " + curActionDir + fileName);
		return testerSurfix;

	}

	public static void deleteTempFiles(String tempFileName, String curActionDir, String surfix) {
		// input: Tester(1)+testerSurfix or BankAccout.java
		// if (verbose)
		// log("INFO: Inside: deleteTempJavaAndClassFile " + tempFileName);

		String fileName = tempFileName;
		// remove the surfix from the fileName
		if (tempFileName.contains(surfix))
			fileName = tempFileName.substring(0, tempFileName.lastIndexOf(surfix));

		File f = new File(curActionDir + fileName + surfix);
		if (f.exists()) {
			f.delete();
			// if (verbose)
			// log("INFO: Deleting: " + curActionDir + fileName + surfix);
		}
		if (surfix.equals(".java")) {
			f = new File(curActionDir + fileName + ".class");
			if (f.exists()) {
				f.delete();
				// if (verbose)
				// log("INFO: Deleting: " + curActionDir + fileName + ".class");
			}
		}
	}

	public static String stringTransfer(String string) {
		char[] chars = string.toCharArray();
		StringBuffer buffer_ans = new StringBuffer(chars.length);
		for (int index = 0; index < chars.length; index++) {
			char ch = chars[index];
			if (ch == ' ') {
				buffer_ans.append("<font color=\"#aaaaaa\">&#8226;</font>");
			} else if (ch == replaceChar && index <= (chars.length - 3) && chars[index + 1] == replaceChar
					&& chars[index + 2] == replaceChar) {
				buffer_ans.append("<font color=\"#aaaaaa\">&#182;</font><br />");
				index = index + 2;
			} else {
				buffer_ans.append(ch);
			}
		}
		return buffer_ans.toString();
	}

	public static void log(String str) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		str = dateFormat.format(date) + " " + str;
		System.out.println(str);
	}

//	public static void outLog(String str, ServletOutputStream out) throws IOException {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Date date = new Date();
//		str = dateFormat.format(date) + " " + str;
//		System.out.println(str);
//		out.println(str);
//	}
}
