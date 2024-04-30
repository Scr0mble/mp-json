package src;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;


/**
 * Utilities for our simple implementation of JSON.
 */
public class JSON {
  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /**
   * The current position in the input.
   */
  static int pos;

  // +----------------+----------------------------------------------
  // | Static methods |
  // +----------------+

  /**
   * Parse a string into JSON.
   */
  public static JSONValue parse(String source) throws ParseException, IOException {
    return parse(new StringReader(source));
  } // parse(String)

  /**
   * Parse a file into JSON.
   */
  public static JSONValue parseFile(String filename) throws ParseException, IOException {
    FileReader reader = new FileReader(filename);
    JSONValue result = parse(reader);
    reader.close();
    return result;
  } // parseFile(String)

  /**
   * Parse JSON from a reader.
   */
  public static JSONValue parse(Reader source) throws ParseException, IOException {
    pos = 0;
    JSONValue result = parseKernel(source);
    if (-1 != skipWhitespace(source)) {
      throw new ParseException("Characters remain at end", pos);
    }
    return result;
  } // parse(Reader)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  /**
   * Parse JSON from a reader, keeping track of the current position
   */
  static JSONValue parseKernel(Reader source) throws ParseException, IOException {
    int ch;
    ch = skipWhitespace(source);
    if (-1 == ch) {
      throw new ParseException("Unexpected end of file", pos);
    }
    //while(-1 != ch){
      if(ch == ','){
        ch = skipWhitespace(source);
      }
      if(ch == '"'){
        ch = skipWhitespace(source);
        String str = "";
        while(ch != '"'){
          str += (char) ch;
          ch = skipWhitespace(source);
        }
        JSONString jstr = new JSONString(str);
        //System.out.println(jstr);
        return jstr;
      } else if(ch == '['){
        System.out.println("poop");
        JSONArray arr = new JSONArray();
        while(ch != ']'){        
          arr.add(parseKernel(source));
        }
        return arr;
      } else if(ch == '{'){
        JSONHash obj = new JSONHash();
        while(ch != ']'){          
          //recursively add things
        }
        return obj;
      } else if(Character.isDigit(ch) || ch == '-'){
        String str = "";
        str += ch;
        ch = skipWhitespace(source);
        while (Character.isDigit(ch) || ch == 'E' || ch == 'e'){
          str += ch;
          ch = skipWhitespace(source);
        }
        if(str.contains("e")){
          String splt[] = str.split("e");
          str = String.valueOf((Math.pow((Double.parseDouble(splt[0])), Double.parseDouble(splt[1]))));
        }
        if(str.contains("E")){
          String splt[] = str.split("E");
          str = String.valueOf((Math.pow((Double.parseDouble(splt[0])), Double.parseDouble(splt[1]))));
        }
        if(str.contains(".")){
          JSONReal jreal = new JSONReal(str);
          return jreal;
        } else {
          JSONInteger jint = new JSONInteger(str);
          return jint;
        }
      } else if (ch == 't') {
        for(int i = 0; i < 3; i++) {
          skipWhitespace(source);
        }
        return JSONConstant.TRUE;
      } else if (ch == 'f') {
        for(int i = 0; i < 4; i++) {
          skipWhitespace(source);
        }
        return JSONConstant.FALSE;
      } else if (ch == 'n') {
        for(int i = 0; i < 3; i++) {
          skipWhitespace(source);
        }
        return JSONConstant.NULL;
      } else {
        throw new ParseException("Non-valid token", pos);
      }
    //}
  } // parseKernel

  /**
   * Get the next character from source, skipping over whitespace.
   */
  static int skipWhitespace(Reader source) throws IOException {
    int ch;
    do {
      ch = source.read();
      ++pos;
    } while (isWhitespace(ch));
    return ch;
  } // skipWhitespace(Reader)

  /**
   * Determine if a character is JSON whitespace (newline, carriage return,
   * space, or tab).
   */
  static boolean isWhitespace(int ch) {
    return (' ' == ch) || ('\n' == ch) || ('\r' == ch) || ('\t' == ch);
  } // isWhiteSpace(int)

} // class JSON
