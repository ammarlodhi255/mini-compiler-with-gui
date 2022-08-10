package com.mycompany.mavenproject1;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LexemeGenerator {
    String sourceStream;
    Scanner reader;
    ArrayList<String> lines;
    int tempIndex;
    Map<String, List<String>> symbolTable;
    int numOfBlankLines = 0;
    TokenTable tokenTable;
    SymbolTable symbolTableForm;
    
    LexemeGenerator(String sourceStream, TokenTable tokenTable, SymbolTable symbolTableForm) {
        this.sourceStream = sourceStream;
        reader = new Scanner(sourceStream);
        lines = new ArrayList<>();
        symbolTable = new HashMap<>();
        this.symbolTableForm = symbolTableForm;
        this.tokenTable = tokenTable;
        
        readLines();
        fillSymbolTable();
        System.out.println(this.getLines());
        fillTokenTable();
    }
    
    private void readLines() {
        while (reader.hasNextLine()) {
            String str = reader.nextLine();
            if (!(str.length() == 0)) {     
                String [] strSplit = str.trim().split("\\s+|\\s*,\\s*|\\;+|\\.+|\\:+|\\[+|\\]+");     
                List <String> list = Arrays.asList(strSplit);       
                this.lines.addAll(list);                                 
            } else numOfBlankLines++;
            this.lines.add("linebreak");
        }
    }
    
    private void fillSymbolTable() {
        List<String> keywords = getKeywords(lines);   
        this.symbolTable.put("Keywords", keywords);

        List<String> operators = getOperators(lines);                
        this.symbolTable.put("Math Operators", operators); 
        
        List<String> logical = getLogiOperators(lines);        
        this.symbolTable.put("Logical Operators", logical);
        
        String [] linesArray = lines.toArray(new String [0]);
        putDigits(linesArray);
        putIdentifiers(linesArray);
        putOthers(linesArray);       
    }

    private void putDigits(String[] linesArray) {
        List<String> digits = new ArrayList<String>();
        for (int count = 0; count < linesArray.length; count++) {
            String currentToken = linesArray[count];
            if (isDigit(currentToken)) {
                digits.add(linesArray[count]);
            }
        }
        this.symbolTable.put("Numerical Values", digits);
    }
    
    private void putIdentifiers(String[] linesArray) {
        List<String> identifiers = new ArrayList<String>();     
        for (int count = 0;  count < linesArray.length; count++) {
            String currentToken = linesArray[count];
            if (isIdentifier(currentToken)) {  //Use regex here for variables  
                if (!identifiers.contains(linesArray[count])) {
                    identifiers.add(linesArray[count]);    
                }
            }        
        }
        this.symbolTable.put("Identifiers", identifiers);
    }
    
    private void putOthers(String[] linesArray) {
        List<String> others = new ArrayList<String>();     
        for (int count = 0;  count < linesArray.length; count++) {
            String currentToken = linesArray[count];
            if (isSpecial(currentToken)) {  
                if (!this.symbolTable.get("Identifiers").contains(linesArray[count])) {
                    others.add(linesArray[count]);    
                }
            }        
        }
        this.symbolTable.put("Others", others);
    }
    
    public List getLines() {
        return this.lines;
    }
    
    private String getAhead(int index, String literal) {
        this.tempIndex = index;
        while(!lines.get(tempIndex).contains("\""))
            literal += lines.get(tempIndex++);
        
        literal += lines.get(tempIndex).substring(0, lines.get(tempIndex).indexOf("\""));
        return literal;
    }
    
    private void fillTokenTable() {
        int lineNumber = 1;
        
        for (int i = 0; i < this.lines.size(); i++) {
            String token = lines.get(i);
  
            if (token.contains("\"")) {
                i = insertLiteral(token, lineNumber, i);
                continue;
            }          
            if(token.equals("linebreak")) lineNumber++;
            InsertToken(token, lineNumber, i);
        }
    }

    private int insertLiteral(String token, int lineNumber, int i) {
        int idx = 1;
        String remaining = "";
        if (token.contains("(")) {
            idx = token.indexOf("(");
            String id = token.substring(0, idx);
            remaining = token.substring(idx + 2, token.length());
            InsertToken(id, lineNumber, i);
        }
        
        String literal = remaining;
        literal = getAhead(i + 1, literal);
        i = tempIndex;
        tempIndex = 0;
        this.tokenTable.model.insertRow(this.tokenTable.model.getRowCount(),
                new Object[]{Integer.toString(lineNumber), "String Literal", literal});
        return i;
    }

    private void InsertToken(String token, int lineNumber, int index) {
        String returnedStr;
        if((returnedStr = this.isKeyword(token)) != null) 
            insertDataPoint(lineNumber, "Keyword", returnedStr);
        else if((returnedStr = this.isOperator(token)) != null) 
            insertDataPoint(lineNumber, "Operator", returnedStr);
        else if((returnedStr = this.isLogiOperator(token)) != null) 
            insertDataPoint(lineNumber, "Logical Operator", returnedStr);
        else if(isDigit(token)) 
            insertDataPoint(lineNumber, "Numeric", token);
        else if(isIdentifier(token)) {
            if(token.contains("(")) {
                token = token.substring(0, token.indexOf("("));
                InsertToken(token, lineNumber, index);
                InsertToken("(", lineNumber, index);
                InsertToken(")", lineNumber, index);
            } else 
                insertDataPoint(lineNumber, "Identifier", token);
            
            String type = lines.get(index - 1).equals("linebreak") ? "Unknown" : lines.get(index - 1);
            this.symbolTableForm.model.insertRow(this.symbolTableForm.model.getRowCount(),
                new Object[]{Integer.toString(lineNumber), "ID", token, type});
        }
        else if(isSpecial(token)) 
            insertDataPoint(lineNumber, "Special Symbol", token);
    }

    private void insertDataPoint(int lineNumber, String category, String returnedStr) {
        this.tokenTable.model.insertRow(this.tokenTable.model.getRowCount(),
                new Object[] {Integer.toString(lineNumber), category, returnedStr});
    }
    
    private static void addKeyword(ArrayList<String> lines, List<String> keywords, String keyword) {
        int index = 0;
        index = lines.indexOf(keyword);   
        keywords.add(lines.get(index));
    }

    private static void addOp(ArrayList<String> lines, List<String> operators, String op) {
        int index = 0;
        index = lines.indexOf(op);   
        operators.add(lines.get(index));
    }

    private static void addLogiOp(ArrayList<String> lines, List<String> logical, String logiOp) {
        int index = 0;
        index = lines.indexOf(logiOp);   
        logical.add(lines.get(index));
    }

    private static List<String> getKeywords(ArrayList<String> lines) {
        List<String> keywords = new ArrayList<String>(); 
        if (lines.contains("int") || lines.contains("float") 
        || lines.contains("if") || lines.contains("else") 
        || lines.contains("public") || lines.contains("class")) {         
            if (lines.contains("public"))
                addKeyword(lines, keywords, "public");
            if (lines.contains("class"))
                addKeyword(lines, keywords, "class");
            if (lines.contains("int")) 
                addKeyword(lines, keywords, "int");  
            if (lines.contains("float")) 
                addKeyword(lines, keywords, "float");  
            if (lines.contains("if")) 
                addKeyword(lines, keywords, "if");
            if (lines.contains("else"))     
                addKeyword(lines, keywords, "else");           
        } 
        return keywords;
    }

    private static List<String> getOperators(ArrayList<String> lines) {
    	List<String> operators = new ArrayList<String>();        
        if (lines.contains("=") || lines.contains("-") || lines.contains("+") || lines.contains("*")) {         
            if (lines.contains("=")) 
                addOp(lines, operators, "=");              
            if (lines.contains("-")) 
                addOp(lines, operators, "-");          
            if (lines.contains("+")) 
                addOp(lines, operators, "+");              
            if (lines.contains("*")) 
                addOp(lines, operators, "*");                        
        }
        return operators;   
    }

    private static List<String> getLogiOperators(ArrayList<String> lines) {
        List<String> logical = new ArrayList<String>(); 
        if (lines.contains("<") || lines.contains(">") || lines.contains("<=") || lines.contains(">=")) {         
            if (lines.contains("<")) 
                addLogiOp(lines, logical, "<");
            if (lines.contains(">")) 
                addLogiOp(lines, logical, ">");
            if (lines.contains("<=")) 
                addLogiOp(lines, logical, "<=");
            if (lines.contains(">=")) 
                addLogiOp(lines, logical, ">=");        
        } 
        return logical;
    }
    
    private String isKeyword(String token) {
        if(token.equals("int")) return "int";
        if(token.equals("float")) return "float";
        if(token.equals("if")) return "if";
        if(token.equals("else")) return "else";
        if(token.equals("public")) return "public";
        if(token.equals("class")) return "class";
        if(token.equals("void")) return "void";
        if(token.equals("double")) return "double";
        if(token.equals("String")) return "String";
        if(token.equals("static")) return "static";
        return null;
    }
    
    private String isOperator(String token) {
        if(token.equals("+")) return "+";
        else if(token.equals("-")) return "-";
        else if(token.equals("*")) return "*";
        else if(token.equals("/")) return "/";
        else if(token.equals("%")) return "%";
        else if(token.equals("=")) return "=";
        else return null;
    }
    
    private String isLogiOperator(String token) {
        if(token.equals("<")) return "<";
        else if(token.equals(">")) return ">";
        else if(token.equals("<=")) return "<=";
        else if(token.equals(">=")) return ">=";
        else if(token.equals("==")) return "==";
        else if(token.equals("!=")) return "!=";
        else return null;
    }
    
    private boolean isDigit(String token) {
        return token.matches("\\d+|\\d+\\.\\d+");
    }
    
    private boolean isIdentifier(String token) {
        return !(token.equals("linebreak")) && 
                ((token.matches("\\w+") || token.matches("\\w+\\(\\)")) && !token.matches("\\d+") && !token.matches("int|float|if|else|public|class"));
    }
    
    private boolean isSpecial(String token) {
        return token.matches("\\(|\\)|\\{|\\}");
    }
}
