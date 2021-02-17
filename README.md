# DD2480 CONTINUOUS INTEGRATION

### Assignment 2, Group 21
*Alexander E, Ioana C, Joaquin B Q, Johan H, Theodor M

### Introduction
The program is a CI-server, inspired by Travis and Jenkins, which builds and tests a GitHub repository. The result of the build and tests gets sent as a email notification to the user who commited last. If the associated email address is private, then the email is sent to a default (git) email address, instead. This simulates the steps taken by other CI servers such as Travis.

### Background
This is the second assignment in the course Software Engineering Fundamentals (DD2480) at KTH Royal Institute of Technology.

### Tech/Framework used
Language: Java 8
Build tool: Maven 3.6.3 used by the CI, Maven 4 used for building the CI

### RUN
step 1: mvn compile
step 2: mvn exec:java -Dexec.mainClass=Main

### Test
We are currently running the CI on a hello world program (found on the assessment branch), which also includes two sample unit tests. As part of its functionality, the CI also runs these tests after compiling the hello world program. This completely fulfills the specified testing criteria in P2. 

### Development history
We started by creating the code skeleton for the project. With the provided Jetty configuration, we split the work and pair-programmed the compile and clone functionalities using the jGit API and Maven Invoker. Afterwards, we merged the branches together. Then, we created a sample hello world project to test the functionality of the CI server. We also wrote tests for the hello world program in order to test that functionality as well. At the same time the e-mail link was written in order for the server to notify the user of the build success. 

### Pass+ criteria
Firstly, over 90% of our commits are referencing an issue. Additionally, the implementation of the email notification system is noteworthy (confirmed to fulfill P7). It makes use of dynamically creating HTML content that is passed as the body of the email. It contains the build status (green / red light), the date, repository name and URL of the branch where the commit was made, and the committer's email address. The recipient of the email is the committer. However, if the committer's email address is private, the email will be sent to a default address. 

### Contribution
* Code Skeleton: Theodor, Johan
* gitignore: Theodor, and when it got lost, Alex
* JavaDoc and HTML: Ioana 
* Documentation: everyone
* Compile feature: Ioana and Theodor
* Git clone feature: Alex and Joaquin
* Test project (hello_world / assessment): Theodor
* E-mail notification feature: Ioana
* Test-task feature: Alex and Joaquin
* Refactoring: Ioana
* readme: Theodor, Johan and Joaquin