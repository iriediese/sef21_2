# DD2480 CONTINUOUS INTEGRATION

### Assignment 2, Group 21
*Alexander E, Ioana C, Joaquin B Q, Johan H, Theodor M

### Introduction
The program is a CI-server, inspired by Travis and Jenkins, which should build and test a GitHub repository. The result of the build and tests gets sent as a email notification to the user that did the last commit or if the associated email address is not public then the email is sent to a default one. This simulates the steps taken by other CI servers such as Travis.

### Background

This is the second assignment in the course Software Engineering Fundamentals (DD2480) at KTH Royal Institute of Technology.

### Tech/Framework used

Language: Java 8
Build tool: Maven 3.6.3 by the CI, Maven 4 for the CI

### RUN
step 1: mvn compile
step 2: mvn exec:java -Dexec.mainClass=Main

### Test
We are currently running the CI on a hello_world program, which also includes two sample unit tests. As part of its functionality the CI also runs these tests after compiling the hello_world program. Despite the fact that this already fulfills the specified testing criteria we have decided to also include some unit tests to the CI itself as bonus. 

### Development history
We started by creating the code skeleton for the project and a gitignore that got lost. With the provided Jetty configuration we split the work and pair-programmed the compile and clone functionalities using the jGit API and Maven Invoker. Afterwards we merged the branches together and did some more stuff. Then we created a mock-up project to test the functionality of the CI server. We also whote tests for the mock-up in order to test that functionality as well. At the same time the e-mail link was wrote in order for the server to notify the user of the build success. rest is TODO

### Pass+ criteria
Firstly over 90% of our commits are referencing an issue. Additionally we implemented a history recording feature for the CI. Furthermore the implementation of the email notification system is noteworthy. It makes use of dynamically creating HTML content that is passed as the body of the email. It contains the built status, the date, repository name and URL of the branch on which the commit was made and the committer email. The recipient of the email is the committer, however if the email address is not public the email will be sent to a default address. 

### Special mentions
TODO

### Contribution

* Code Skeleton: Theodor
* gitignore: Theodor
* JavaDoc and HTML: Ioana 
* Documentation: everyone
* Compile feature: Ioana and Theodor
* Git clone feature: Alex and Joaquin
* Test project(hello_world): Theodor
* E-mail notification feature: Ioana
* Test-task feature: Alex and Joaquin
* gitignore 2 revenge of the .idea: Alex
* History recording feature: Ioana and Johan
* Refactoring: Ioana
* Logging: Theodor
* TODO add more: All of us

