package main.java.philosophers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
    /*
     * ------------
     * Data members
     * ------------
     */
    private boolean philosopherIsTalking = false;
    int numOfPhilosophersWaitingToTalk = 0;

    private enum Status {
        FULL,
        HUNGRY,
        HAS_RIGHT_CHOPSTICK,
        HAS_LEFT_CHOPSTICK,
        EATING
    }

    private ArrayList<Status> states;
    private Condition talking;

    int numOfPhilosophers;

    /**
     * Constructor
     */
    public Monitor(int piNumberOfPhilosophers) {
        numOfPhilosophers = piNumberOfPhilosophers;
        states = new ArrayList<>(numOfPhilosophers);

        for (int i = 0; i < numOfPhilosophers; i++) {
            states.add(Status.FULL);
        }
    }

    /*
     * -------------------------------
     * User-defined monitor procedures
     * -------------------------------
     */

    private void check(int index) {

        if (bothChopsticksAreFree(index) && wantsToEat(index)) {
            states.set(index, Status.EATING);
        } else if (rightChopstickIsFree(index) && wantsToEat(index)) {
            System.out.println(MessageFormat.format("Philosopher {0} takes the chopstick on his right", index + 1));
            states.set(index, Status.HAS_RIGHT_CHOPSTICK);
        } else if (leftChopstickIsFree(index) && wantsToEat(index)) {
            System.out.println(MessageFormat.format("Philosopher {0} takes the chopstick on his left", index + 1));
            states.set(index, Status.HAS_LEFT_CHOPSTICK);
        }
    }

    private int onLeft(int index) {
        if (index == 0) {
            return numOfPhilosophers - 1;
        }
        return index - 1;
    }

    private int onRight(int index) {
        if (index == numOfPhilosophers - 1) {
            return 0;
        }
        return index + 1;
    }

    private boolean wantsToEat(int index) {
        return isHungry(index) || hasRightChopstick(index) || hasLeftChopstick(index);
    }

    private boolean isHungry(int index) {
        return states.get(index) == Status.HUNGRY;
    }

    private boolean isEating(int index) {
        return states.get(index) == Status.EATING;
    }

    private boolean isFull(int index) {
        return states.get(index) == Status.FULL;
    }

    private boolean hasRightChopstick(int index) {
        return states.get(index) == Status.HAS_RIGHT_CHOPSTICK;
    }

    private boolean hasLeftChopstick(int index) {
        return states.get(index) == Status.HAS_LEFT_CHOPSTICK;
    }

    private boolean rightChopstickIsFree(int index) {
        return !(isEating(onRight(index)) || hasLeftChopstick(onRight(index)));
    }

    private boolean leftChopstickIsFree(int index) {
        return !(isEating(onLeft(index)) || hasRightChopstick(onLeft(index)));
    }

    private boolean bothChopsticksAreFree(int index) {
        return rightChopstickIsFree(index) && leftChopstickIsFree(index);
    }

    /**
     * Grants request (returns) to eat when both chopsticks/forks are available.
     * Else forces the philosopher to wait()
     */
    public synchronized void pickUp(final int philosopherThreadId) {
        int id = philosopherThreadId - 1;

        try {
            states.set(id, Status.HUNGRY);
            while (isHungry(id) || hasRightChopstick(id) || hasLeftChopstick(id)) {
                check(id);

                if (isHungry(id)) {
                    System.out.println(MessageFormat.format("Philosopher {0} is hungry", philosopherThreadId));
                    DiningPhilosophers.soMonitor.wait();
                } else if (hasRightChopstick(id)) {
                    System.out.println(MessageFormat.format("Philosopher {0} has the right chopstick", philosopherThreadId));
                    DiningPhilosophers.soMonitor.wait();
                } else if (hasLeftChopstick(id)) {
                    System.out.println(MessageFormat.format("Philosopher {0} has the left chopstick", philosopherThreadId));
                    DiningPhilosophers.soMonitor.wait();
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Monitor.pickUp():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * When a given philosopher's done eating, they put the chopsticks/forks down
     * and let others know they are available.
     */
    public synchronized void putDown(final int philosopherThreadId) {
        int id = philosopherThreadId - 1;

        states.set(id, Status.FULL);

        check(onLeft(id));

        if (isEating(onLeft(id))) {
            DiningPhilosophers.soMonitor.notifyAll();
        }

        check(onRight(id));

        if (isEating(onRight(id))) {
            DiningPhilosophers.soMonitor.notifyAll();
        }
    }

    /**
     * Only one philosopher at a time is allowed to philosophy
     * (while she is not eating).
     */
    public synchronized void requestTalk() {
        try {
            numOfPhilosophersWaitingToTalk++;

            while (philosopherIsTalking) {
                DiningPhilosophers.soMonitor.wait();
            }

            numOfPhilosophersWaitingToTalk--;
            philosopherIsTalking = true;
        } catch (InterruptedException e) {
            System.err.println("Monitor.requestTalk():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
    }

    /**
     * When one philosopher is done talking stuff, others
     * can feel free to start talking.
     */
    public synchronized void endTalk() {
        philosopherIsTalking = false;

        if (numOfPhilosophersWaitingToTalk > 0) {
            DiningPhilosophers.soMonitor.notifyAll();
        }
    }
}

// EOF