package src;
import java.io.PrintWriter;
import java.util.Iterator;


/**
 * JSON hashes/objects.
 */
public class JSONHash implements JSONValue {



  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  public static final int STARTING_CAP = 100;
  public static final double LOAD_FACTOR = 0.5;

  Object[] hashArray;
  int size;
  int capacity;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  public JSONHash() {
    this.capacity = STARTING_CAP;
    this.size = 0;
    this.hashArray = new Object[STARTING_CAP];
  }

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  @SuppressWarnings("unchecked")
  public String toString() {
    String ret = "";
    for(Object obj : hashArray) {
      try {
        ret += ((SimpleCDLL<KVPair<Integer, JSONValue>>)obj).toString();
      } catch (NullPointerException e) {}
    }
    return ret; 
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if(other instanceof JSONHash) {
      return this.hashArray.equals(((JSONHash)other).hashArray);
    } else {
      return false;
    }
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    return this.hashArray.hashCode();
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
    pen.print("[" + this.toString() + " ]");
    pen.flush();
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public Iterator<KVPair<JSONString,JSONValue>> getValue() {
    return this.iterator();
  } // getValue()

  // +-------------------+-------------------------------------------
  // | Hashtable methods |
  // +-------------------+

  /**
   * Get the value associated with a key.
   */
  public JSONValue get(JSONString key) {
    return null;        // STUB
  } // get(JSONString)

  /**
   * Get all of the key/value pairs.
   */
  public Iterator<KVPair<JSONString,JSONValue>> iterator() {
    return null;        // STUB
  } // iterator()

  /**
   * Set the value associated with a key.
   */
  public void set(JSONString key, JSONValue value) {
                        // STUB
  } // set(JSONString, JSONValue)

  /**
   * Find out how many key/value pairs are in the hash table.
   */
  public int size() {
    return this.size;           // STUB
  } // size()

} // class JSONHash
