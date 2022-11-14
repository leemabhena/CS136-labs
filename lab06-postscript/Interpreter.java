
// we are the sole authors of the work in this repository.
import structure5.*;

/**
 * An implementation of a basic PostScript interpreter. This creates object that
 * performs
 * postscript commands using a stack vector.
 * 
 */
public class Interpreter {

  /**
   * stack which stores postscript tokens.
   */
  StackVector<Token> stack;

  /**
   * Table to store all postscript symbols.
   */
  SymbolTable table;

  /**
   * Interpreter constructor, initializes a stackVector and symbol table
   */
  public Interpreter() {
    stack = new StackVector<Token>();
    table = new SymbolTable();
  }

  /**
   * Function to read symbols from files or procedures.
   * 
   * @param r is a reader object
   * @pre r is a Reader
   * @post Reads postscript tokens into table and stack
   */
  public void interpreter(Reader r) {
    Token t; // token passed from the reader
    // process tokens from the reader
    while (r.hasNext()) {
      t = (Token) r.next();
      // check if token is quit and exit if it is
      if (t.isSymbol() && t.getSymbol().equals("quit")) {
        break;
      }
      // if symbol is def
      if (t.isSymbol() && t.getSymbol().equals("def")) {
        continue;
      }
      // if symbol starts with a /, add it to table
      if (t.isSymbol() && t.getSymbol().charAt(0) == '/') {
        // grab the first symbol as name of symbol
        String name = t.getSymbol().substring(1);
        // add the next token and name to table
        t = (Token) r.next();
        table.add(name, t);
        continue; // continue to beginning of loop, to avoid interpreting it
      }
      // if t is a symbol evaluate and interpret it
      if (t.isSymbol()) {
        read(t.getSymbol());
        continue;
      }
      // if all fails add token to stack
      stack.push(t);
    }
  }

  /**
   * Function to print the stack using recursion.
   * 
   * @post Prints the stack, and stack is not modified after printing.
   */
  private void printStack() {
    // base case if stack is empty print empty line and start building stack
    if (stack.isEmpty()) {
      System.out.println();
      return;
    }
    // take the last element and print it
    Token last = stack.pop();
    System.out.print(last + " ");
    printStack();
    // rebuild the stack
    stack.add(last);
  }

  /**
   * Function that take the last 2 tokens in stack and add them, add result to
   * stack
   */
  private void add() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and add them
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // for add to work, last 2 tokens should be numbers
    Assert.condition(lastNumber.isNumber() && secOfLast.isNumber(), "Is number");
    stack.push(new Token(lastNumber.getNumber() + secOfLast.getNumber()));
  }

  /**
   * Function that take the last 2 tokens in stack and multiply them, add result
   * to stack
   */
  private void multiply() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and mul them
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // for mul to work, last 2 tokens should be numbers
    Assert.condition(lastNumber.isNumber() && secOfLast.isNumber(), "Is number");
    stack.push(new Token(lastNumber.getNumber() * secOfLast.getNumber()));
  }

  /**
   * Function that take the last 2 tokens in stack and subtract them, add result
   * to stack
   */
  private void subtract() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and subtract them
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // for subtract to work, last 2 tokens should be numbers
    Assert.condition(lastNumber.isNumber() && secOfLast.isNumber(), "Is number");
    stack.push(new Token(secOfLast.getNumber() - lastNumber.getNumber()));
  }

  /**
   * Function that take the last 2 tokens in stack and carryout division, add
   * result to stack
   */
  private void division() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and subtract them
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // for subtract to work, last 2 tokens should be numbers
    Assert.condition(lastNumber.isNumber() && secOfLast.isNumber(), "Is number");
    stack.push(new Token(secOfLast.getNumber() / lastNumber.getNumber()));
  }

  /**
   * Function to duplicate last element in stack
   */
  private void duplicate() {
    // there should be at least 1 element
    Assert.pre(!stack.isEmpty(), "Stack is not empty");
    // take the last token and duplicate it, add result to stack
    stack.push(stack.get());
  }

  /**
   * Function that exchanges the last 2 elements in stack
   */
  private void exchange() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and exchange them
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // exchange last 2 numbers, updating the stack
    stack.push(lastNumber);
    stack.push(secOfLast);
  }

  /**
   * Function that checks if last 2 tokens are equal.
   */
  private void equality() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and check for equality
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // push to table boolean after comparing last 2 tokens
    stack.push(new Token(lastNumber.equals(secOfLast)));
  }

  /**
   * Function that checks if last 2 tokens are not equal.
   */
  private void inequality() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and check for inequality
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    // push to table boolean after comparing last 2 tokens
    stack.push(new Token(!lastNumber.equals(secOfLast)));
  }

  /**
   * Function that checks if last 2 tokens are not equal.
   */
  private void lessThan() {
    // there should be at least 2 tokens in stack
    Assert.pre(stack.size() > 1, "Size is at least 2");
    // take the last two elements in stack and if secOflast is less than last
    Token lastNumber = stack.pop();
    Token secOfLast = stack.pop();
    Assert.condition(lastNumber.isNumber() && secOfLast.isNumber(), "Is number");
    // push to table boolean after comparing last 2 tokens
    stack.push(new Token(secOfLast.getNumber() < lastNumber.getNumber()));
  }

  /**
   * Remove last element from stack
   */
  private void pop() {
    // stack should not be empty
    Assert.pre(!stack.isEmpty(), "Stack is not empty");
    stack.pop();
  }

  /**
   * Executes the if symbol of postscript.
   */
  private void condition() {
    // there should be atleast 2 tokens
    Assert.pre(stack.size() > 1, "Size is at least 2");
    Token token = stack.pop(); // last token
    Token ifBoolean = stack.pop(); // second of last token is boolean
    // secOf last should be a boolean and last procedure
    Assert.condition(ifBoolean.isBoolean() && token.isProcedure(), "Tokens are boolean and procedure");
    // if token is true execute the procedure
    if (ifBoolean.getBoolean()) {
      Reader reader = new Reader(token.getProcedure()); // create reader to read from procedure
      interpreter(reader); // interpret the procedure
    }
  }

  /**
   * Function that executes def symbol in postscript
   * 
   * @param symbol The symbol lookup from symbols table.
   */
  private void def(String symbol) {
    if (table.contains(symbol)) { // check if in table
      // if it is procedure create a reader to read from procedure
      if (table.get(symbol).isProcedure()) {
        Reader procReader = new Reader(table.get(symbol));
        interpreter(procReader); // interpret the procedure
      } else {
        // if not procedure add token to stack
        stack.add(table.get(symbol));
      }
    }
  }

  /**
   * Function to interpret symbols
   * 
   * @param symbol, The symbol to interpret
   * @pre Symbol should be a string
   * @post interprets the symbol, adding it to table or stack if need be.
   */
  protected void read(String symbol) {
    switch (symbol) {
      case "pstack":
        printStack();// print the stack
        break;

      case "add":
        add(); // take the last 2 tokens and add them, add result to stack
        break;

      case "sub":
        subtract(); // take the last 2 tokens and subtract them, add result to stack
        break;

      case "mul":
        multiply(); // take the last 2 tokens and multiply them, add result to stack
        break;

      case "div":
        division(); // divide sec of last with last token
        break;

      case "dup":
        duplicate();// take the last token and duplicate it, add result to stack
        break;

      case "exch":
        exchange();// exchange last 2 tokens,and modify stack
        break;

      case "eq":
        equality();// check if last 2 elements are the same and add result to stack
        break;

      case "ne":
        inequality();// check if last 2 elements are not the same and add result to stack
        break;

      case "lt":
        lessThan();// check if last < second of last and push to stack
        break;

      case "pop":
        pop();// remove last element from stack
        break;

      case "ptable":
        System.out.print(table); // print the table
        break;

      case "if":
        condition(); // execute procedure if true
        break;

      default: // user defined symbols
        def(symbol);
    }

  }

  public static void main(String[] args) {
    // create a new interpreter object to read data
    Interpreter interpret = new Interpreter();
    Reader r = new Reader(); // reader reads from commandline
    interpret.interpreter(r); // interpret data
  }
}
