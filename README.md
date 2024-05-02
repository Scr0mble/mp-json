Mini-Project 8: JSON
====================

A simple JSON parser.

Implemented by David Rhoades, Lucas Willett, William Pitchford for CSC-207-01 2022Sp.

Acknowledgements:

* Skeleton code by SamR

Talked with Ishita about static finals
Boston helped understanding how to make our iterator for the hash table work, by giving the us the idea to have an iterator
in our iterator.

referenced https://www.geeksforgeeks.org/convert-string-to-double-in-java/ for a refresher on how to convert from Strings to Doubles

Used code from Lab-hashtables and MP8

This is a parser that can read in JSON values (Strings, Real, Aarray, Constant, Integer) and creates JSON objects for them. Each JSON object
has a toString method that can be used to print them out. Also there is a fully functional chained hashtable with an iterator where we can store
Array lists of Pairs with type <JsonValue, JsonValue>. 