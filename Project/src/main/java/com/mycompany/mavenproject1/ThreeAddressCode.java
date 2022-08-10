package com.mycompany.mavenproject1;
import java.util.Stack;

public class ThreeAddressCode 
{
    static String text = "";
    static String DataType ="";

    public static String GenerateCode(String originalCode){
        text = "";
        System.out.println(originalCode);
        originalCode = originalCode.trim();
        String firstVariable = originalCode.substring(0, originalCode.indexOf('='));
        int firstSpace = firstVariable.indexOf(" ");
        DataType = firstVariable.substring(0, firstSpace);
        originalCode = originalCode.replaceAll(" ", "");
        String text = originalCode.substring(originalCode.indexOf('=') + 1);
        Node parentNode = BuildTree(text);
        dfs(parentNode);
        text = ThreeAddressCode.text + DataType +
                 firstVariable.substring(firstSpace) + " = E" + (IntermediateCodeGenerator.VariableNumber-1);
        return text;
    }
    private static Node BuildTree(String inputString) {
        Stack<Character> operatorStack = new Stack();
        Stack<Node> expressionStack = new Stack();
        Character c;
        for (int i = 0; i < inputString.length(); i++) {
            c = inputString.charAt(i);
            if (c == '(') {
                operatorStack.push(c);
            }else if(c == ';' || c == '{' || c == '}' || c == '[' || c == ']' || c == '.'){
                continue;
            }
            else if (Character.isDigit(c)) {
                if (i<inputString.length()-1 && Character.isDigit(inputString.charAt(i+1))){
                    String Number = "";
                    for (int j=i; j<inputString.length(); j++){
                        if (isOperator(inputString.charAt(j))) {
                            i = j-1;
                            break;
                        }
                        Number += inputString.charAt(j);
                    }
                    expressionStack.push(new Node('#', Number));
                }
                else {
                    expressionStack.push(new Node(c));
                }
            }
            else if (c == '\"'){
                String temp = inputString.substring(i+1);
                String literal = "\"" + temp.substring(0, temp.indexOf('\"'));
                expressionStack.push(new Node('#', literal));
            }
            else if (Character.isAlphabetic(c)) {
                if (i<inputString.length()-1 && Character.isAlphabetic(inputString.charAt(i+1))){
                    String variableName = "";
                    boolean flag = true;
                    for (int j=i; j<inputString.length(); j++){
                        if (inputString.charAt(j) == '(')
                            flag = false;
                        if (inputString.charAt(j) == ')')
                            flag = true;
                        if (flag && isOperator(inputString.charAt(j))) {
                            i = j-1;
                            break;
                        }
                        variableName += inputString.charAt(j);
                    }
                    expressionStack.push(new Node('#', variableName));
                }
                else {
                    expressionStack.push(new Node(c));
                }
            }
            else if (isOperator(c)) {
                while (getOperatorPrecedence(
                            getTopOfOperator(operatorStack)) >= getOperatorPrecedence(c)) {
                    Character operator = operatorStack.pop();
                    Node e2 = expressionStack.pop();
                    Node e1 = expressionStack.pop();
                    expressionStack.push(
                            new Node(operator, e1, e2, "E" + IntermediateCodeGenerator.VariableNumber++));
                }
                operatorStack.push(c);
            } else if (c == ')') {
                while (getTopOfOperator(operatorStack) != '(') {

                    Character operator = operatorStack.pop();
                    Node e2 = expressionStack.pop();
                    Node e1 = expressionStack.pop();
                    expressionStack.push(
                            new Node(operator, e1, e2, "E" + IntermediateCodeGenerator.VariableNumber++));
                }
                operatorStack.pop();
            } else {
                System.out.println("Invalid Statement: " + c);
            }
        }
        while (!operatorStack.empty()) {
            Character operator = operatorStack.pop();
            Node e2 = expressionStack.pop();
            Node e1 = expressionStack.pop();
            expressionStack.push(new Node(operator, e1, e2, "E" + IntermediateCodeGenerator.VariableNumber++));
        }
        return expressionStack.pop();
    }
    private static void dfs(Node root) {
        if (isOperator(root.charac)) {
            dfs(root.operand1);
            dfs(root.operand2);
            text += DataType + " " + root.name + " = " + isVariable(root.operand1) + " " +
                    root.charac + " " + isVariable(root.operand2) + "\n";
        }
    }
    private static String isVariable(Node node){
        if (node.name.equalsIgnoreCase("#"))
            return node.ExtraVariableName;
        else
            return node.name;
    }
    private static boolean isOperator(Character c) {
        if (c == '+' || c == '-' || c == '/' || c == '*' || c == '%') {
            return true;
        } else {
            return false;
        }
    }
    private static Character getTopOfOperator(Stack<Character> stack) {
        if (stack.isEmpty()) {
            return 'e';
        }
        Character top = stack.peek();
        return top;
    }
    private static int getOperatorPrecedence(Character inOperator) {
        if(inOperator ==  '+' || inOperator == '-') {
            return 1;
        } else if (inOperator == '*') {
            return 2;
        } else if (inOperator == '/') {
            return 3;
        } else if (inOperator == '%') {
            return 4;
        } else if (inOperator == 'e') {
            return 0;
        } else {
            return 0;
        }
    }
    private static class Node
    {
        Node operand1;
        Node operand2;
        Character charac;
        String name;
        String ExtraVariableName;
        Node(Character num){
            charac=num;
            this.name=num+"";
        }
        Node(Character n, String num){
            charac=n;
            this.name = n+"";
            this.ExtraVariableName=num;
        }
        Node(Character num,Node e1,Node e2,String name){
            operand1=e1;
            operand2=e2;
            charac=num;
            this.name=name;
        }
        
    }
}