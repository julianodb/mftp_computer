
package tcc_gui;

public class Timer extends Thread {

    private long timeout;
    private boolean timedOut =false;

    public Timer (long timeout) {
        this.timeout = timeout;
    } // constructor

    public void run() {
        try {
            timedOut = false;
            sleep(timeout);
            timedOut = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
    } // run

    public boolean timedOut() {
        return timedOut;
    }

} // Timer
