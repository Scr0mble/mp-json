package src;
import java.io.PrintWriter;

/**
 * JSON strings.
 * @author: Sam, David R, Lucas W, William P
 */
public class JSONString implements JSONValue {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The underlying string.
   */
  String value;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new JSON string for a particular string.
   */
  public JSONString(String value) {
    this.value = value;
  } // JSONString(String)

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  public String toString() {
    return this.value; 
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if(other instanceof JSONString){
      return this.value.equals(((JSONString)other).value);
    }
    return false; 
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    return this.value.hashCode();
  } // hashCode() 

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
    pen.print("\"");
    for(int i = 0; i < this.value.length(); i++) {
      char temp = value.charAt(i);
      if(temp == '\"') {
        pen.print("\\\"");
      } else if (temp == '\\') {
        pen.print("\\\\");
      } else if (temp == '/') {
        pen.print("\\/");
      } else if (temp == '\b') {
        pen.print("\\b");
      } else if (temp == '\f') {
        pen.print("\\f");
      } else if (temp == '\n') {
        pen.print("\\n");
      } else if(temp == '\t') {
        pen.print("\\t");
      } else if(temp == '\r') {
        pen.print("\\r");
      } else {
        pen.print(temp);
      }
    }
    pen.print("\"");
    pen.flush();
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public String getValue() {
    return this.value;
  } // getValue()

} // class JSONString
