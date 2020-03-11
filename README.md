# Dining Philosophers Problem

COMP 346 - Winter 2020

Programming Assignment 3

Due Date: 11:59 PM - April 3, 20 20

## 1 Objective

Implement the dining philosopher’s problem using a monitor for synchronization.

## 2 Source Code

There are five files that come with the assignment. A soft copy of the code is available to
download from the course web site. This time the source code is barely implemented (though
compiles and runs). You are to complete its implementation.

### 2.1 File Checklist

Files distributed with the assignment requirements:

- common/BaseThread.java - unchanged
- DiningPhilosophers.java - the main()
- Philosopher.java - extends from BaseThread
- Monitor.java - the monitor for the system
- Makefile - take a look

## 3 Background

This assignment is a slight extension of the classical problem of synchronization – the
Dining Philosophers problem. You are going to solve it using the Monitor synchronization
construct built on top of Java’s synchronization primitives. The extension refers to the fact that
sometimes philosophers would like to talk, but only one (any) philosopher can be talking at a
time while they are not eating. If you need help, consult the references at the bottom.

## 4 Tasks

Make sure you put comments for every task that involves coding to the changes that
you’ve made. This will be considered in the grading process.

### Task 1: The Philosopher Class

Complete the implementation of the Philosopher class, that is all its methods according to
the comments in the code. Specifically, eat(), think(), talk(), and run() methods have to be
implemented entirely.

Non-mandatory hints are provided within the code.

### Task 2: The Monitor

Implement the Monitor class for the problem. Make sure it is correct, deadlock- and
starvation-free implementation that uses Java’s synchronization primitives, such as wait() and
notifyAll(); no use of Semaphore objects is allowed. Implement the four methods of the Monitor
class; specifically, pickUp(), putDown(), requestTalk(), and endTalk(). Add as many member
variables and methods to monitor the conditions outlined below as needed:

1. A philosopher is allowed to pickup the chopsticks if they are both available. That implies
having states of each philosopher as presented in your book. You might want to consider the
order in which to pick the chopsticks up.
2. If a given philosopher has decided to make a statement, they can only do so if no one else is
talking at the moment. The philosopher wishing to make the statement has to wait in that case.

### Task 3: Variable Number of Philosophers

Make the application to accept a positive integer number from the command line, and
spawn exactly that number of philosophers instead of the default one. If there are no command
line arguments, the given default should be used. If the argument is not a positive integer, report
this fact to the user, print the usage information as in the example below:

```bash
java DiningPhilosophers -7.a
"-7.a" is not a positive decimal integer
```

Usage:

```bash
java DiningPhilosophers [NUMBER_OF_PHILOSOPHERS]
```

Use Integer.parseInt() method to extract an int value from a character string.
Test your implementation with the varied number of philosophers. Submit your output from
"make regression".

## 5 Deliverables

IMPORTANT: You are allowed to work on a team of 2 students at most (including yourself!).
You and your teammate must be in the same section. Any teams of 3 or more students will result
in 0 marks for all team members. If your work on a team, ONLY one copy of the assignment is to
be submitted for both members. You must make sure that you upload the assignment to the
correct directory of **Programming Assignment 3** using EAS/Moodle. Your instructor will
indicate the exact location. Assignments uploaded to the wrong directory will be discarded and no
resubmission will be allowed.

Naming convention for uploaded file: Create one zip file, containing all needed files for your assignment using the following naming convention: The zip file should be called a#_studentID , where # is the number of the assignment studentID is your student ID(s) number. For example, for the first assignment, student 12345678 would submit a zip file named a1_12345678.zip. if you work on a team and your IDs are 12345678 and 34567890, you would submit a zip file named a1_12345678_34567890.zip.

Submit your assignment electronically via EAS (<https://fis.encs.concordia.ca/eas/)> or Moodle based on the instruction given by your instructor as indicated above. Please see course outline for submission rules and format, as well as for the required demo of the assignment. A working copy of the code and a sample output should be submitted for the tasks that require them. A text file with answers to the different tasks should be provided. Put it all in a file layout as explained below, archive it with any archiving and compressing utility, such as WinZip, WinRAR, tar, gzip, bzip2, or others. You must keep a record of your submission confirmation. This is your proof of submission; which you may need should a submission problem arises.

## 6 Grading Scheme

| Criteria | Marks |
|----------|-------|
| Task 1   | 2     |
| Task 2   | 6     |
| Task 3   | 2     |

## 7 References

1. Java API: <http://java.sun.com/j2se/1.3/docs/api/>

2. [http://java.sun.com/docs/books/tutorial/essential/TOC.html#threads](http://java.sun.com/docs/books/tutorial/essential/TOC.html#threads)
