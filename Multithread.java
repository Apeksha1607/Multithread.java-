class ChatUser extends Thread {
    private volatile boolean running = true;
    private volatile boolean suspended = false;

    ChatUser(String name, int priority) {
        super(name);
        setPriority(priority);
    }

    public void run() {
        while (running) {
            // check suspend
            synchronized (this) {
                while (suspended) {
                    try { wait(); } catch (InterruptedException e) {}
                }
            }
            // simulate sending a message
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

public class SimpleChat {
    public static void main(String[] args) throws Exception {
        ChatUser u1 = new ChatUser("Alice", Thread.NORM_PRIORITY);
        ChatUser u2 = new ChatUser("Bob", Thread.MAX_PRIORITY);

        u1.start();
        u2.start();

        System.out.println("Alice alive? " + u1.isAlive());
        System.out.println("Bob alive? " + u2.isAlive());

        Thread.sleep(2000);

        System.out.println("Suspending Bob...");
        u2.suspendUser();
        Thread.sleep(2000);

        System.out.println("Resuming Bob...");
        u2.resumeUser();
        Thread.sleep(2000);

        System.out.println("Stopping Alice and Bob...");
        u1.stopUser();
        u2.stopUser();

        u1.join();
        u2.join();

        System.out.println("Alice alive? " + u1.isAlive());
        System.out.println("Bob alive? " + u2.isAlive());
        System.out.println("Chat ended.");
    }
}
