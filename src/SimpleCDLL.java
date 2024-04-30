package src;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class SimpleCDLL<T> implements SimpleList<T> {

  // +--------+--------------------------------------------------------
  // | Fields |
  // +--------+

  Node2<T> dummy;
  int numChanges;
  int size;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  public SimpleCDLL() {
    this.dummy = new Node2<T>(this.dummy, null, this.dummy);
    this.numChanges = 0;
    this.size = 0;
  }

  // +-----------+---------------------------------------------------------
  // | Methods   |
  // +-----------+

  public String toString() {
    String ret = "{";
    Node2<T> temp = dummy.next;
    while(temp != dummy) {
      ret += (temp.value.toString());
      if(temp.next != dummy) {
        ret += ",";
      }
    }
    ret += " }";
    return ret;
  }

  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator() {
    return listIterator();
  }

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {
      // +--------+--------------------------------------------------------
      // | Fields |
      // +--------+

      int pos = 0;
      int numChanges = SimpleCDLL.this.numChanges;
      Node2<T> prev = SimpleCDLL.this.dummy;
      Node2<T> next = SimpleCDLL.this.dummy.next; // looked at this file for a full 20 minutes without changing anything trying to figure out what was wrong with my code, added the .next and everything worked, fml.

      Node2<T> update = null;

      // +---------+-------------------------------------------------------
      // | Methods |
      // +---------+

      /**
       * failFast(), makes sure our iterators fail if the underlying list is modified by a different iterator
       */
      private void failFast() {
        if(this.numChanges != SimpleCDLL.this.numChanges) {
          throw new ConcurrentModificationException();
        }
        return;
      }

      /**
       * add(T val), adds an element to the list
       */
      public void add(T val) throws UnsupportedOperationException {
        failFast();
        if(this.numChanges != SimpleCDLL.this.numChanges) {
          throw new UnsupportedOperationException();
        }
        ++this.numChanges;
        ++SimpleCDLL.this.numChanges;
        this.prev = this.prev.insertAfter(val);
        this.update = null;
        ++SimpleCDLL.this.size;
        ++this.pos;
      }
      
      /**
       * hasNext(), checks if there is a next element
       */
      public boolean hasNext() {
        failFast();
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      /**
       * hasPrevious(), checks if there is a previous element
       */
      public boolean hasPrevious() {
        failFast();
        return (this.pos > 0);
      } // hasPrevious()

      /**
       * next(), moves to the element in the list
       */
      public T next() {
        failFast();
        if (!this.hasNext()) {
          throw new NoSuchElementException();
        }
        this.update = this.next;
        this.prev = this.next;
        this.next = this.next.next;
        ++this.pos;
        return this.update.value;
      } // next 

      /**
       * nextIndex(), checks the next index
       */
      public int nextIndex() {
        failFast();
        return this.pos;
      } // nextIndex()

      
      /**
       * previousIndex(), checks the previous index
       */
      public int previousIndex() {
        failFast();
        return this.pos - 1;
      } // prevIndex

      /**
       * previous(), moves to the previous element in the list
       */
      public T previous() throws NoSuchElementException {
        failFast();
        if (!this.hasPrevious())
          throw new NoSuchElementException();
        this.update = this.prev;
        this.next = this.prev;
        this.prev = this.prev.prev;
        --this.pos;
        return this.update.value;
      } // previous()

      /**
       * remove(), removes the last iterated over element in the list
       */
      public void remove() {
        failFast();
        if(this.update == null) {
          throw new IllegalStateException();
        }

        if(this.next == this.update) {
          this.next = this.update.next;
        }

        if(this.prev == this.update) {
          this.prev = this.update.prev;
          --this.pos;
        }

        ++this.numChanges;
        ++SimpleCDLL.this.numChanges;
        this.update.remove();
        --SimpleCDLL.this.size;
        this.update = null;
      }
      
      /**
       * set(), sets the element at the last iterated over element
       */
      public void set(T val) {
        failFast();
        if(this.update == null) {
          throw new IllegalStateException();
        }
        this.update.value = val;
        this.update = null;
      }
    };
  }

}