
# SimpleChat - Java Multithreaded Chat Simulation

This project demonstrates thread management in Java using a simple chat user simulation. It highlights thread control operations such as suspend, resume, and stop, along with thread priorities.

## Overview

- **ChatUser** class extends `Thread` to simulate a chat user sending messages continuously.
- Threads can be:
  - **Suspended**: Temporarily paused from sending messages.
  - **Resumed**: Reactivated to continue sending messages.
  - **Stopped**: Terminated gracefully.

## Key Features

- Uses `volatile` variables to manage thread state safely.
- Synchronization with `wait()` and `notify()` for suspend/resume functionality.
- Demonstrates thread priority with different users.
- Proper thread termination using interrupt and a running flag.

## How It Works

- Two `ChatUser` threads are created: "Alice" with normal priority and "Bob" with maximum priority.
- Both start sending messages.
- After some time, Bob is suspended and later resumed.
- Both users are then stopped, and the program waits for their completion before exiting.

## Code Snippet

```java
class ChatUser extends Thread {
    private volatile boolean running = true;
    private volatile boolean suspended = false;

    ChatUser(String name, int priority) {
        super(name);
        setPriority(priority);
    }

    public void run() {
        while (running) {
            synchronized (this) {
                while (suspended) {
                    try { wait(); } catch (InterruptedException e) {}
                }
            }
            System.out.println(getName() + " (priority " + getPriority() + ") is sending a message...");
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
        System.out.println(getName() + " stopped.");
    }

    public void suspendUser() {
        suspended = true;
    }

    public synchronized void resumeUser() {
        suspended = false;
        notify();
    }

    public void stopUser() {
        running = false;
        interrupt();
    }
}
````

## Running the Program

Run the `SimpleChat` main class. The console output will show the chat users sending messages, suspension/resumption of a user, and their termination.

## Sample Output

```
Alice (priority 5) is sending a message...
Bob (priority 10) is sending a message...
Alice (priority 5) is sending a message...
Bob (priority 10) is sending a message...
...
Suspending Bob...
Alice (priority 5) is sending a message...
Alice (priority 5) is sending a message...
Resuming Bob...
Bob (priority 10) is sending a message...
...
Stopping Alice and Bob...
Alice stopped.
Bob stopped.
Alice alive? false
Bob alive? false
Chat ended.
```

-

Would you like me to generate this as a `.md` file for you?
```
****
