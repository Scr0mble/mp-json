package src;
import java.io.PrintWriter;

/**
 * JSON values.
 */
public interface JSONValue {

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  public String toString(); 

  /**
   * Compare to another object.
   */
  public boolean equals(Object other);

  /**
   * Compute the hash code.
   */
  public int hashCode();

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen);

  /**
   * Get the underlying value.
   */
  public Object getValue();

} // interface JSONValue
