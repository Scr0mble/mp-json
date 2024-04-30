package src;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * JSON arrays.
 */
public class JSONArray implements JSONValue {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The underlying array.
   */
  ArrayList<JSONValue> values;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new array.
   */
  public JSONArray() {
    this.values = new ArrayList<JSONValue>();
  } // JSONArray() 

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  public String toString() {
    return this.values.toString();          
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if(other instanceof JSONArray) {
      String thisStr = this.values.toString();
      String otherStr = ((JSONArray)other).values.toString();
      return thisStr.compareTo(otherStr) == 0;
    } else {
      return false;
    }
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    return this.values.hashCode();
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
    pen.print("[");
    if(this.values.isEmpty()) {
      pen.print(" ]");
      pen.flush();
      return;
    }
    Object[] tempArray = this.values.toArray();
    for(int i = 0; i < tempArray.length; i++) {
      pen.print(tempArray[i]);
      if(i + 1 != tempArray.length) {
        pen.print(", ");
      } else {
        pen.print("]");
      }
    }
    pen.flush();
    return;
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public ArrayList<JSONValue> getValue() {
    return this.values;
  } // getValue()

  // +---------------+-----------------------------------------------
  // | Array methods |
  // +---------------+

  /**
   * Add a value to the end of the array.
   */
  public void add(JSONValue value) {
    this.values.add(value);
  } // add(JSONValue)

  /**
   * Get the value at a particular index.
   */
  public JSONValue get(int index) throws IndexOutOfBoundsException {
    return this.values.get(index);
  } // get(int)

  /**
   * Get the iterator for the elements.
   */
  public Iterator<JSONValue> iterator() {
    return this.values.iterator();
  } // iterator()

  /**
   * Set the value at a particular index.
   */
  public void set(int index, JSONValue value) throws IndexOutOfBoundsException {
    this.values.set(index, value);
  } // set(int, JSONValue)

  /**
   * Determine how many values are in the array.
   */
  public int size() {
    return this.values.size();
  } // size()
} // class JSONArray
