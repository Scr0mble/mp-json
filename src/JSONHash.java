package src;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        ret += ((ArrayList<JSONPair>)obj).toString();
      } catch (NullPointerException e) {}
    }
    return ret; 
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if(other instanceof JSONHash) {
      String str = this.toString();
      String ot = ((JSONHash)other).toString();
      return this.toString().equals(((JSONHash)other).toString());
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
  public Iterator<JSONPair> getValue() {
    return this.iterator();
  } // getValue()

  // +-------------------+-------------------------------------------
  // | Hashtable methods |
  // +-------------------+

  /**
   * Get the value associated with a key.
   */
  public JSONValue get(JSONString key) {
    int index = find(key);
    @SuppressWarnings("unchecked")
    ArrayList<JSONPair> alist = (ArrayList<JSONPair>) hashArray[index];
    if (alist == null) {
      throw new IndexOutOfBoundsException("Invalid key: " + key);
    } else {
      for(int i = 0; i < alist.size(); i++) {
        if(alist.get(i).key().equals(key)) {
          return alist.get(i).value();
        }
      }
      return null;
    } // get
  } // get(JSONString)

  /**
   * Get all of the key/value pairs.
   */
  public Iterator<JSONPair> iterator() {
    return null;        // STUB
  } // iterator()

  /**
   * Set the value associated with a key.
   */
  @SuppressWarnings("unchecked")
  public void set(JSONString key, JSONValue value) {
    // If there are too many entries, expand the table.
    if (this.size > (this.hashArray.length * LOAD_FACTOR)) {
      expand();
    } // if there are too many entries

    // Find out where the key belongs and put the pair there.
    int index = find(key);
    ArrayList<JSONPair> alist = (ArrayList<JSONPair>) this.hashArray[index];
    // Special case: Nothing there yet
    if (alist == null) {
      alist = new ArrayList<JSONPair>();
      this.hashArray[index] = alist;
    } else {
      for(int i=0; i<alist.size(); i++){
        if(alist.get(i).key().equals(key)){
          JSONPair newpair = new JSONPair(key, value);
          alist.set(i, newpair);
          this.hashArray[index] = alist;
          return;
        }
      }
    }
    alist.add(new JSONPair(key, value));
    ++this.size;

    // And we're done
    return;
  } // set(JSONString, JSONValue)

  /**
   * Find out how many key/value pairs are in the hash table.
   */
  public int size() {
    return this.size;
  } // size()

  /**
   * Find the index of the entry with a given key. If there is no such entry,
   * return the index of an entry we can use to store that key.
   */
  int find(JSONString key) {
    return Math.abs(key.hashCode()) % this.hashArray.length;
  } // find(K)

  /**
  * Expand the size of the table.
  */
  void expand() {
    // Figure out the size of the new table
    int newSize = 2 * this.hashArray.length;
    // Remember the old table
    Object[] oldBuckets = this.hashArray;
    // Create a new table of that size.
    this.hashArray = new Object[newSize];
    // Move all buckets from the old table to their appropriate
    // location in the new table.
    for (int i = 0; i < oldBuckets.length; i++) {
      this.hashArray[i] = oldBuckets[i];
    } // for
  } // expand()

} // class JSONHash
