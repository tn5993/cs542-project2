# An Indexing Mechanism
<p>Cory Hayward, Thinh Nguyen, He Xuebin <br/>
Project 2 for CS542: Database Management Systems <br/>
Professor Singh <br/>
4 March 2015</p>

<h4>Introduction</h4>
<p>Following instructions provided in the course website (http://cs542.wpi.datathinks.org/proj), this project aims to provide a better understanding, as well as an implementation, of the indexing mechanism utilized in B+ trees. We accomplished this task using the Java programming language and the following interfaces:</p>
<code> 
void Put(string key, string data_value);<br/>
void Put(string key, Number data_value);<br/>
String Get(Number data_value);<br/>
String Get(string data_value);<br/>
void Remove(string key);
</pre>
<h1>B+ Tree</h1>
<p>The definition of B+ Tree can be found at <a href="http://en.wikipedia.org/wiki/B%2B_tree"> Wikipedia </a>. However, we modified this definition a bit for ease of implementation but still reserve characteristics of B+ tree. Classically, a node in the tree has k keys and k+1 pointers to children. In our implementation, we dismissed the left most pointer, so each node in our tree has k children. For instance, the figure below depicts our B+ Tree</p> 
<img src="https://cloud.githubusercontent.com/assets/8074347/6494940/8b7e34bc-c293-11e4-8c71-6d1d8a62b22f.png" width="500px;"/>
<h1>Thread safe</h1>
<p> For thread safe, we created a singleton class named DBHelper that keeps only one instance of BTree and performs put, get, and remove operations</p>

<h1> Tests </h1>
We performed test for the BTree in test source folder. Also, thread safe tests were executed.
