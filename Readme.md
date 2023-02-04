# **Readme**

ARTIFICIAL INTELLIGENCE : Markov Decision Process and Backward Induction

Few things about this project -
1. inputFile.txt, shows the sample test case. Input file should be in the root directory of the project.
2. The execution arguments can be :  [-df] [-min/max] [-tol] [inputFile.txt] <br>
   2.1. [-df] : discount factor, should be in range [0,1] <br>
   2.2. [-min/max] : To indicate whether we should pick min or max <br>
   2.3. [-tol] : Indicate tolerance level
3. '#' and empty lines will get executed and treated as comments.


To execute the project do run the following commands in the project root directory :

1. mvn clean install
2. java -jar target/AILab-1.0-SNAPSHOT.jar [-df] [-min/max] [-tol] inputFile.txt


