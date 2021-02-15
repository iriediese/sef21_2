# sef21_2
Software Engineering Fundamentals, group 21, lab 2

## Assessment branch
This is the assessment branch, required by P2.  
This branch contains a basic hello world java implementation, coupled with two (sample) unit tests.  
In their current state, the first test should pass, but the second one should fail (as required by P2).  
This branch is cloned by the CI upon receiving the push (using the webhook), which then triggers its compilation and testing using maven.  
This is done by the maven invoker API, which uses the included maven folder (as seen in the main branch, for example).  
After the tests have been executed, an e-mail is sent with the results (success / failure).
