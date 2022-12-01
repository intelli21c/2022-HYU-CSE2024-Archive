package engine;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    public static int count;
    public static int max_count;

    static boolean spinlock;
    public static boolean endp;

    public static int countDown(int max_count) {
        count = max_count;

        Timer m_timer = new Timer();
        TimerTask m_task = new TimerTask() {
            public void run() {
                if (count > 0) {
                    count--;
                } else {
                    m_timer.cancel();
                }
            }
        };
        endp=false;
        m_timer.schedule(m_task, 1000);
        return count;
    }

    public static int update() {
        Timer m_timer = new Timer();
        TimerTask m_task = new TimerTask() {
            public void run() {
                if (count > 0) {
                    spinlock=false;
                    count--;
                } else {
                    spinlock=false;
                    endp=true;
                    m_timer.cancel();
                }
            }
        };
        if(spinlock) return count;
        m_timer.schedule(m_task, 1000);
        spinlock=true;
        return count;
    }
}