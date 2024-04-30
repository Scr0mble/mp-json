package src;
import java.io.PrintWriter;

public class JSONExpt {
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    //experiments for string
    pen.println("String Experiments");
    JSONString str = new JSONString("s\"hab\u2933a\nb bux\ba");
    pen.println(str.toString());
    pen.println(str.equals(new JSONString("s\"hab\u2933a\nb bux\ba")));
    pen.println(str.equals(new JSONString("groinagr")));
    pen.println(str.hashCode());
    str.writeJSON(pen);
    //experiments for int
    pen.println("");
    pen.println("Int Experiments");
    JSONInteger intg = new JSONInteger(499);
    pen.println(intg.toString());
    pen.println(intg.equals(new JSONInteger(499)));
    pen.println(intg.equals(new JSONInteger(400)));
    pen.println(intg.equals(new JSONReal(499)));
    pen.println(intg.hashCode());
    intg.writeJSON(pen);
    //experiments for real
    pen.println("");
    pen.println("Real Experiments");
    JSONReal real = new JSONReal(700.5);
    pen.println(real.toString());
    pen.println(real.equals(new JSONReal(700.5)));
    pen.println(real.equals(new JSONReal(0.5)));
    pen.println(real.hashCode());
    real.writeJSON(pen);
    //experiments for arrays
    pen.println("");
    pen.println("Array Experiments");
    JSONArray arr = new JSONArray();
    arr.add(new JSONInteger(0));
    arr.add(new JSONInteger(1));
    arr.add(new JSONInteger(4));
    pen.println(arr.toString());
    JSONArray arr2 = new JSONArray(); 
    arr2.add(new JSONInteger(0)); 
    arr2.add(new JSONInteger(1)); 
    arr2.add(new JSONInteger(4));
    pen.println(arr.equals(arr2));
    pen.println(arr.equals(new JSONArray()));
    pen.println(arr.hashCode());
    arr.writeJSON(pen);
  }
}
