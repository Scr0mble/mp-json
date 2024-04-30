package src;
/**
 * Simple, immutable, key/value pairs
 */
public class KVPair<K,V> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key. May not be null.
   */
  private K key;

  /**
   * The associated value.
   */
  private V value;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new key/value pair.
   */
  public KVPair(K key, V value) {
    this.key = key;
    this.value = value;
  } // KVPair(K,V)

  // +------------------+--------------------------------------------
  // | Standard methods |
  // +------------------+

  /**
   * Compare for equality.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object other) {
    return ((other instanceof KVPair) && (this.equals((KVPair<K,V>) other)));
  } // equals(Object)

  /**
   * Compare for equality.
   */
  public boolean equals(KVPair<K,V> other) {
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
  public K key() {
    return this.key;
  } // key()

  /**
   * Get the value.
   */
  public V value() {
    return this.value;
  } // value()
} // KVPair<K,V>
