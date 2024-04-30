package src;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Tests of for the JSON class.
 *
 * @author Garikai
 * @author Zakariye
 * @author SamR
 * // still need basic tests (testing creating the variable and the equals method) for JSONConstant
 * // need tests for complex JSONStrings in the parser, does it correctly read special characters and unicode like "\n" and "\u002F"(this should produce /)
 * // need tests for complex JSONReals in the parser, does it correctly read exponentiated numbers like 2.5e2?
 * // need tests for Arrays in Hashes and Hashes in Arrays
 */
public class ZakGarikaiJSONTests {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * An array of strings for our experiments.
   */
  String[] words = {"aardvark", "anteater", "antelope", "bear", "bison",
      "buffalo", "chinchilla", "cat", "dingo", "elephant", "eel",
      "flying squirrel", "fox", "goat", "gnu", "goose", "hippo", "horse",
      "iguana", "jackalope", "kestrel", "llama", "moose", "mongoose", "nilgai",
      "orangutan", "opossum", "red fox", "snake", "tarantula", "tiger",
      "vicuna", "vulture", "wombat", "yak", "zebra", "zorilla"};

  
  // +--------+------------------------------------------------------
  // | Tests  |
  // +--------+
  /*
   * Check if JSONInteger is storing and retrieving values as intended
   */
  @Test
  void intGetTest() {
    for (int i = 0; i < 100; i++) {
      JSONInteger testInt = new JSONInteger(i);
      assertEquals(BigInteger.valueOf(i), testInt.getValue());
    } // for
  } // intGetTest()

  /*
   * Check if the equals method of JSONInteger works as intended
   */
  @Test
  void intEqualsTest() {
    for (int i = 0; i < 100; i++) {
      JSONInteger testInt = new JSONInteger(i);
      JSONInteger testInt1 = new JSONInteger(i);
      assertEquals(testInt.equals(testInt1), true);
    } // for
  } // intEqualsTest()

  /*
   * Checkk if JSONString is storing and retrieving values as intended
   */
  @Test
  void stringGetTest() {
    for (int i = 0; i < words.length; i++) {
      JSONString testWord = new JSONString(words[i]);
      assertEquals(testWord.getValue(), words[i]);
    } // for
  } // stringGetTest()

  /*
   * Check if the equals method of JSONArray works for equality
   */
  @Test 
  void arrayEqualsTest() {
    JSONArray testArr = new JSONArray();
    JSONArray testArr1 = new JSONArray();
    for (int i = 0; i < words.length; i++) {
      testArr.add(new JSONString(words[i]));
      testArr.add(new JSONReal(i*2));
      testArr1.add(new JSONString(words[i]));
      testArr1.add(new JSONReal(i*2));
    } // for
    assertEquals(testArr.equals(testArr1), true);
  } // arrayEqualsTest()

  /*
   * Check if the equals method of JSONArray works for inequality
   */
  @Test 
  void arrayNotEqualsTest() {
    JSONArray testArr = new JSONArray();
    JSONArray testArr1 = new JSONArray();
    for (int i = 0; i < words.length; i++) {
      testArr.add(new JSONString(words[i]));
      testArr.add(new JSONReal(i*2));
      testArr1.add(new JSONString(words[i]));
      testArr1.add(new JSONReal(i));
    } // for
    assertEquals(testArr.equals(testArr1), false);
  } // arrayNotEqualsTest()

  /*
   * Check if the equals method of JSONHash works as intended
   */
  @Test 
  void hashEqualsTest() {
    JSONHash testHash = new JSONHash();
    JSONHash testHash1 = new JSONHash();
    // set values and check equality
    for (int i = 0; i < words.length; i++) {
      testHash.set(new JSONString(words[i]), new JSONReal(i));
      testHash1.set(new JSONString(words[i]), new JSONReal(i));
    } // for
    assertEquals(testHash.equals(testHash1), true);
    // change values in a different order and check equality
    for (int i = 0; i < words.length; i++) {
      testHash.set(new JSONString(words[words.length-i-1]), new JSONString(words[words.length-i-1]));
      testHash1.set(new JSONString(words[i]), new JSONString(words[i]));
    } // for
    assertEquals(testHash.equals(testHash1), true);
    // change a single value to remove equality
    testHash.set(new JSONString(words[0]), new JSONInteger(0));
    assertEquals(testHash.equals(testHash1), false);
  } // hashEqualsTest()

  /*
   * Check if the parser properly converts into JSONArrays
   */
  @Test
  void parseArrayStringTest() {
    // add values to array and build equivalent string
    JSONArray testArr = new JSONArray();
    StringBuilder parsingString = new StringBuilder();
    parsingString.append("[");
    for (int i = 0; i < words.length; i++) {
      testArr.add(new JSONString(words[i]));
      parsingString.append('"'+words[i]+'"').append(",");
    } // for
    // remove last comma
    parsingString.setLength(parsingString.length()-1);
    parsingString.append(']');
    try {
      JSONValue compare = JSON.parse(parsingString.toString());
      assertEquals(testArr.equals(compare), true);
    } catch (Exception e) {
      fail("Could not parse valid array");
    } // try-catch
  } // parseArrayStringTest() 


  /*
   * Check if the parser properly converts into JSONHashes
   */
  @Test
  void parseHashStringTest() {
    // add values to array and build equivalent string
    JSONHash testHash = new JSONHash();
    StringBuilder parsingString = new StringBuilder();
    parsingString.append("{");
    for (int i = 0; i < words.length; i++) {
      testHash.set(new JSONString(words[i]), new JSONInteger(i));
      parsingString.append('"'+words[i]+'"').append(":"+i+",");
    } // for
    // remove last comma
    parsingString.setLength(parsingString.length()-1);
    parsingString.append('}');
    try {
      JSONValue compare = JSON.parse(parsingString.toString());
      assertEquals(testHash.equals(compare), true);
    } catch (Exception e) {
      fail("Could not parse valid Hash");
    } // try-catch
  } // parseHashStringTest()

  /*
   * Check if the add and get methods of JSONArray work as intended
   */
  @Test
  void arrayAddGetTest() {
    JSONArray array = new JSONArray();
    for (int i = 0; i < words.length*2; i++) {
      if (i < words.length) {
        // while we have words, use them
        array.add(new JSONString(words[i]));
      } else {
        array.add(new JSONInteger(i*2));
      }// if-else
    } // for
    for (int i = 0; i < words.length*2; i++) {
      if (i < words.length) {
        // while we have words, check them
        assertEquals(new JSONString(words[i]), array.get(i));
      } else {
        assertEquals(new JSONInteger(i*2),array.get(i));
      }// if-else
    } // for
  } // arrayAddGetTest()

  /*
   * Check if the JSONArray can build nested arrays and if the parser can read them
   */
  @Test
  void nestedArrayTest() {
    JSONArray array = new JSONArray();
    // initalise arrays with 1 element
    Object[] nests = new Object[1];
    array.add(new JSONString(words[0]));
    nests[0] = array;
    StringBuilder parsingString = new StringBuilder();
    parsingString.append("[\""+words[0]+"\"]");
    for (int i = 1; i < 50; i++) {
      // create new array to add nested array into to nest further
      JSONArray nest = new JSONArray();
      nest.add((JSONValue) nests[0]);
      nests[0] = nest;
      // keep our string equally nested
      parsingString.insert(0, "[");
      parsingString.append("]");
    } // for
    try {
      JSONValue compare = JSON.parse(parsingString.toString());
      assertEquals(compare, nests[0]);
    } catch (Exception e) {
      fail("Failed to read nested array");
    } // try-catch
  } // nestedArrayTest()

  /*
   * Check if the JSONHash can build nested arrays and if the parser can read them
   */
  @Test
  void nestedHashTest() {
    JSONHash array = new JSONHash();
    // initalise arrays with 1 element
    Object[] nests = new Object[1];
    array.set(new JSONString(words[0]), new JSONReal(1));
    nests[0] = array;
    StringBuilder parsingString = new StringBuilder();
    parsingString.append("{\""+words[0]+"\":1.0}");
    for (int i = 1; i < 50; i++) {
      // create new array to add nested array into to nest further
      JSONHash nest = new JSONHash();
      nest.set(new JSONString(words[0]),(JSONValue) nests[0]);
      nests[0] = nest;
      // keep our string equally nested
      parsingString.insert(0, "{\""+words[0]+"\":");
      parsingString.append("}");
    } // for
    try {
      JSONValue compare = JSON.parse(parsingString.toString());
      assertEquals(compare, nests[0]);
    } catch (Exception e) {
      fail("Failed to read nested array");
    } // try-catch
  } // nestedHashTest()

  /*
   * Check if the the parser reads well defined arrays and throws errors for ill defined ones 
   */
  @Test
  void exceptionArrayTest() {
    JSONArray arr = new JSONArray();
    arr.add(new JSONInteger(123));
    arr.add(new JSONInteger(456));
    arr.add(new JSONString("super"));
    String goodArray = "[123, 456, \"super\"]";
    try {
      assertEquals(arr, JSON.parse(goodArray));
    } catch (Exception e) {
      fail("good array was not so good it seems");
    } // try-catch
    // invalid JSONArray syntax. missing comma between values
    String badArray = "[123, 456  \"super\"]";
    // could throw any class depending on implementation
    assertThrows(Exception.class,
        () -> JSON.parse(badArray),
        "prevIndex after add");
  } // exceptionArrayTest()

  /*
   * Check if the the parser reads well defined hashes and throws errors for ill defined ones 
   */
  @Test
  void exceptionHashTest() {
    JSONHash hash = new JSONHash();
    hash.set(new JSONString("1st"),new JSONInteger(1));
    hash.set(new JSONString("2nd"),new JSONInteger(2));
    hash.set(new JSONString("3rd"),new JSONInteger(3));
    String goodTable = "{\"1st\":1, \"2nd\":2, \"3rd\":3}";
    try {
      assertEquals(hash, JSON.parse(goodTable));
    } catch (Exception e) {
      fail("good array was not so good it seems");
    } // try-catch
    // invalid JSONHash syntax. missing colon between key/value pair
    String badTable = "{\"1st\" 1, \"2nd\":2, \"3rd\":3}";
    // could throw any class depending on implementation
    assertThrows(Exception.class,
        () -> JSON.parse(badTable),
        "prevIndex after add");
  } // exceptionHashTest()
  
} // class JSONTests
