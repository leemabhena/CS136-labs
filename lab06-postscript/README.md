# Lab 6: P.S. It's Just a Stack
 * [Course Homepage](http://cs.williams.edu/~cs136/) (with TA schedule)
 * [Lab Webpage](https://williams-cs.github.io/cs136s22-www/assets/labs/lab6/postscript.html)
 * Useful Links:
    * [Structure5 Documentation](http://www.cs.williams.edu/~bailey/JavaStructures/doc/structure5/index.html)
    * [Structure5 Source Code](http://www.cs.williams.edu/~bailey/JavaStructures/Software_files/structure-source.tgz)


## Repository Contents
This repository contains the starter files for writing and testing
your postscript interpreter.

 * In this lab, you should only need to modify one Java file: `Interpreter.java`.
 * All files that end in `.ps` are postscript files. You should eventually be able to run them using your interpreter.

## Using Test files
There are several testing programs to help you verify your code as you go.
(You can always run your program interactively and type commands from the
command line as well.)

Rather than wait to test until you have implemented all of the commands,
create a smaller version of the test (the comment character in postscript is `%`),
and add new commands as you implement them.
Start with (a subset of) `basics.ps`! For example:

```
$ cp samples/basics.ps test.ps
$ atom test.ps # this command opens the file test.ps in Atom (if you've installed Atom's "shell commands")
(now modify test.ps so that it only inlcudes a small number of postscript commands)
$ java Interpreter < test.ps
```

Then you can incrementally add more commands to `test.ps`. Eventually, you should be able to run:
```
$ java Interpreter < samples/basics.ps
```

but it would be silly to wait until you've completed all the functionality necessary to run that program before testing your Interpreter. Start small and incrementally test!

## Creating Javadoc
To create Javadoc documentation from the files in this directory,
we can use the `javadoc` command. By default, it will process the
Javadoc-formatted comments in a source file and generate a series
of webpages and resources in the current directory. We don't
want to clutter our repositories, so we will tell Javadoc to place
its output inside the `javadoc/` folder that we have created.
The command we suggest that you use is:
```
$ javadoc -d javadoc/ *.java
```

Notes:
 * The `-d javadoc` option tells the `javadoc` program to place its output in the `javadoc/` directory.
 * The `*.java` argument tells Javadoc to create documentation for every single file in your current directory that ends with the extension `.java`. The `*` is what is called a "wildcard character" (in `*.java`, the `*` will expand to match anything that it can, given the constraint that the string ends in `.java`).

After you are done, you should see many new files in your `javadoc/` directory:
```
$ ls javadoc/
Interpreter.html         help-doc.html            package-tree.html
Reader.html              index-all.html           resources
SymbolTable.html         index.html               script-dir
Token.html               member-search-index.js   script.js
allclasses-index.html    member-search-index.zip  search.js
allpackages-index.html   overview-tree.html       stylesheet.css
constant-values.html     package-search-index.js  system-properties.html
deprecated-list.html     package-search-index.zip type-search-index.js
element-list             package-summary.html     type-search-index.zip
```

If I want to view my newly created documentation, I would open the file `index.html`. This is the webpage that forms the "root" of my Javadoc websites.
In macOS, I could use the `open` command:
```
$ open javadoc/index.html
```

I can also use my browser of choice (e.g., Firefox, Chrome, Safari, Edge, etc.)
to open that local file.
