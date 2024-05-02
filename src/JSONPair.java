package src;
/**
 * Simple, immutable, key/value pairs
 * @author: Sam, David R, Lucas W, William P
 */
public class JSONPair {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key. May not be null.
   */
  private JSONValue key;

  /**
   * The associated value.
   */
  private JSONValue value;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new key/value pair.
   */

  public JSONPair(JSONValue key, JSONValue value) {
    this.key = key;
    this.value = value;
  } // KVPair(K,V)

  // +------------------+--------------------------------------------
  // | Standard methods |
  // +------------------+

  /**
   * Compare for equality.
   */
  @Override
  public boolean equals(Object other) {
    return ((other instanceof JSONPair) && (this.equals((JSONPair) other)));
  } // equals(Object)

  /**
   * Compare for equality.
   */
  public boolean equals(JSONPair other) {
    return ((this.key.equals(other.key)) && (this.value.equals(other.value)));
  } // equals(KVPair<K,V>)

  /**
   * Convert to string form.
   */
  @Override
  public String toString() {
    return  " " + key + " :" + value;
  } // toString()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get the key.
   */
  public JSONValue key() {
    return this.key;
  } // key()

  /**
   * Get the value.
   */
  public JSONValue value() {
    return this.value;
  } // value()
} // KVPair<K,V>
