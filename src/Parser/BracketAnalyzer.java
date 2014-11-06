package Parser;

/**
 * Created by Дмитрий on 03.11.2014.
 */
public class BracketAnalyzer {
    private class Stack{

        private char[] myStack;
        private int size = 64;
        private int head = -1;

        public Stack(){
            myStack = new char[size];
        }

        public Stack(int size){
            myStack = new char[size];
        }

        public void push(char ch){
            myStack[++head] = ch;
        }

        public char pop(){
            if (head > -1){
                return myStack[head--];
            }
            return '0';
        }

        public boolean isEmpty(){
            return head < 0;
        }
    }


    Stack stack;
    int stackSize = 64;

    public BracketAnalyzer(){
        stack = new Stack();
    }

    public BracketAnalyzer(int stackSize){
        stack = new Stack(stackSize);
        this.stackSize = stackSize;
    }

    public boolean analyze(String str){
        if(!stack.isEmpty()){
            stack = new Stack(stackSize);
        }
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == '('){
                stack.push('(');
            }
            if (str.charAt(i) == ')')
            {
                if (stack.isEmpty()){
                    System.out.print("Invalid balance (close bracket without open bracket)");
                    return false;
                }
                else {
                    stack.pop();
                }
            }
        }
        if (!stack.isEmpty()){
            System.out.print("Invalid balance (open bracket without close bracket)");
            return false;
        }
        return true;
    }


}
