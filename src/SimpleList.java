package src;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Very simple lists.
 */
public interface SimpleList<T> extends Iterable<T> {
  public Iterator<T> iterator();

  public ListIterator<T> listIterator();
} // interface SimpleList<T>
