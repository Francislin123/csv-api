package com.api.csv.impl.hackerrank;

import java.util.*;

public class BalancedParentheses {

    public static boolean isBalanced(String s) {

        if (s == null || s.isEmpty()) {
            return true;
        }

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(') || (c == ']' && top != '[') || (c == '}' && top != '{')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String[] testCases = {"{}()", "[{()}]", "({()})", "{}(", "({)}", "[[", "}{"};

        for (String s : testCases) {
            System.out.println(s + " is balanced: " + isBalanced(s));
        }
    }
}
