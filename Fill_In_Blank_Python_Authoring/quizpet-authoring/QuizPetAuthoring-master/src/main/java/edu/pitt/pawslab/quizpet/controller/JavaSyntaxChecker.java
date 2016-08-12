package edu.pitt.pawslab.quizpet.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import edu.pitt.pawslab.quizpet.instance.JavaSourceFromString;

public class JavaSyntaxChecker {
    public static void main(String[] args) {
    	String code = "import javax.tools.ToolProvider;public class CustomProcessor { /*custom stuff*/ }";
        
        System.out.println(JavaSyntaxChecker.check(code));
    }

    public static List<String> check(String code) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            JavaSourceFromString jsfs = new JavaSourceFromString( "CustomProcessor", code);

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList( jsfs);

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        List<String> messages = new ArrayList<String>();
        Formatter formatter = new Formatter();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            messages.add(diagnostic.getKind() + ":\t Line [" + diagnostic.getLineNumber() + "] \t Position [" + diagnostic.getPosition() + "]\t" + diagnostic.getMessage(Locale.ROOT) + "\n");
        }

        return messages;
    }
}