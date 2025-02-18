package main.java.philosophers;


import main.java.philosophers.common.BaseThread;

import java.text.MessageFormat;

/**
 * Class Philosopher.
 * Outlines main subroutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
    /**
     * Max time an action can take (in milliseconds)
     */
    public static final long TIME_TO_WASTE = 1000;
    public static final double PROBABILITY_OF_TALKING = 0.45;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done eating.
     */
    public void eat() {
        try {
            System.out.println(MessageFormat.format("Philosopher {0} has started eating", getTID()));
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println(MessageFormat.format("Philosopher {0} is done eating", getTID()));
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done thinking.
     */
    public void think() {
        try {
            System.out.println(MessageFormat.format("Philosopher {0} has started thinking", getTID()));
            Thread.yield();
            sleep((long) (Math.random() * TIME_TO_WASTE));
            Thread.yield();
            System.out.println(MessageFormat.format("Philosopher {0} is done thinking", getTID()));
        } catch (InterruptedException e) {
            System.err.println("Philosopher.think():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - yield
     * - Say something brilliant at random
     * - yield
     * - The print that they are done talking.
     */
    public void talk() {
        System.out.println(MessageFormat.format("Philosopher {0} has started talking", getTID()));
        Thread.yield();
        saySomething();
        Thread.yield();
        System.out.println(MessageFormat.format("Philosopher {0} is done talking", getTID()));
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    @Override
    public void run() {
        for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
            DiningPhilosophers.soMonitor.pickUp(getTID());

            eat();

            DiningPhilosophers.soMonitor.putDown(getTID());

            think();

            /*
             * A decision is made at random whether this particular
             * philosopher is about to say something terribly useful.
             */
            if (Math.random() < PROBABILITY_OF_TALKING) {
                DiningPhilosophers.soMonitor.requestTalk();
                talk();
                DiningPhilosophers.soMonitor.endTalk();
            }

            Thread.yield();
        }
    } // run()

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    public void saySomething() {
        String[] astrPhrases = {
                "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                "You know, true is false and false is true if you think of it",
                "2 + 2 = 5 for extremely large values of 2...",
                "If thee cannot speak, thee must be silent",
                "My number is " + getTID() + "",
                "My local philosophy club has free why-fi.",
                "A philosopher never sits down at work. Stands to reason.",
                "I’ve finished my philosophy course. Or have I?"
        };

        System.out.println(MessageFormat.format("Philosopher {0} says: {1}", getTID(), astrPhrases[(int) (Math.random() * astrPhrases.length)]));
    }
}

// EOF
