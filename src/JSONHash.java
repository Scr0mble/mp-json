package src;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * JSON hashes/objects.
 * @author: Sam, David R, Lucas W, William P
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

  /**
   * Build a new hash table.
   */
  public JSONHash() {
    this.capacity = STARTING_CAP;
    this.size = 0;
    this.hashArray = new Object[STARTING_CAP];
    for(int i = 0; i < STARTING_CAP; i++) {
      hashArray[i] = new ArrayList<JSONPair>();
    }
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
      if(!((ArrayList<JSONPair>)obj).isEmpty()){
        try {
          ret += ((ArrayList<JSONPair>)obj).toString();
        } catch (NullPointerException e) {}
      }
    }
    return ret; 
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if(other instanceof JSONHash) {
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
  public JSONValue get(JSONValue key) {
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
    return Iterator();        // STUB
  } // iterator()

  public Iterator<JSONPair> Iterator() {
    return new Iterator<JSONPair>() {

      // +--------+--------------------------------------------------------
      // | Fields |
      // +--------+

      /**
       * Current position of iterator
       */
      int curArrayIndex = 0;

      /**
       * Current element of iterator
       */
      ArrayList<JSONPair> curArrayList;

      /**
       * The current iterator
       */
      Iterator<JSONPair> listIt = curArrayList.listIterator();

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      /**
       * Progresses to the next element in the hash table
       */
      @SuppressWarnings("unchecked")
      public boolean hasNext() {
        if(listIt.hasNext()){
          return true;
        }
        for(Object obj : hashArray) {
          if(((ArrayList<JSONPair>)obj).size() != 0) {
            return true;
          }
        }
        return false;
      }

      /**
       * Returns the next value in the table. If hasNext is false, returns null.
       */
      @SuppressWarnings("unchecked")
      public JSONPair next() {
        if(!listIt.hasNext()){
          return null;
        } //if
        if(!listIt.hasNext()){
          for(int i = curArrayIndex; i < capacity; i++) {
            if(((ArrayList<JSONPair>)hashArray[i]).size() != 0) {
              curArrayIndex = i;
              listIt = ((ArrayList<JSONPair>)hashArray[curArrayIndex]).listIterator();
            } //if
          } //for
        } //if
        return (JSONPair)listIt.next();
      } //next()

      /**
       * Does nothing because remove is not a required function for iterators >:)
       */
      public void remove() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
      } //remove()
    };
  }// end of iterator
  
  /**
   * Set the value associated with a key.
   */
  @SuppressWarnings("unchecked")
  public void set(JSONValue key, JSONValue value) {
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
  int find(JSONValue key) {
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
