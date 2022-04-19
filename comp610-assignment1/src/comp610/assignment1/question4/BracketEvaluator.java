/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp610.assignment1.question4;

import java.util.Stack;

/**
 *
 * @author ssr7324
 */
public class BracketEvaluator {

    static boolean isBracketPair(String getLength) {
        Stack<Character> stack = new Stack<>();
        try {

            for (int i = 0; i < getLength.length(); i++) {
                char charForPushBracket = getLength.charAt(i);
                char charForCheckBracket;

                if (charForPushBracket != '[' && charForPushBracket != '<'
                        && charForPushBracket != '(' && charForPushBracket != '{') {
                    if (stack.isEmpty()) {
                        return false;
                    }
                } else {
                    stack.push(charForPushBracket);
                }
                switch (charForPushBracket) {
                    case ']':
                        charForCheckBracket = stack.pop();
                        if (charForCheckBracket != '[') {
                            return false;
                        }
                        break;
                    case ')':
                        charForCheckBracket = stack.pop();
                        if (charForCheckBracket != '(') {
                            return false;
                        }
                        break;
                    case '}':
                        charForCheckBracket = stack.pop();
                        if (charForCheckBracket != '{') {
                            return false;
                        }
                        break;
                    case '>':
                        charForCheckBracket = stack.pop();
                        if (charForCheckBracket != '<') {
                            return false;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return (stack.isEmpty());
    }

    public static void main(String[] args) {
        String[] strings = {"(72)", "(75)", "{((2 x 5)+(3*-2 + 5))}", "{ (2 x 5)+(3*-2 + 5))}",
            "{List<List<E>>}", "{List<List<E> }", "{(<<eeeek>>){}{…}(e(e)e){hello}}",
            "{(< eeeek>>){}{…} e(e)e){hello} "};
        for (String string : strings) {
            System.out.print(string);
            if(isBracketPair(string) == true){
                System.out.println(" - will evaluate successfully");
            } else {
                System.out.println(" - will not evaluate");
            }
        }
    }
}
