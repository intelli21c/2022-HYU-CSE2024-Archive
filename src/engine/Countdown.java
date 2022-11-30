package engine;

import java.util.Timer;
import java.util.TimerTask;

public class Countdown {

    public static int count;
    public static int max_count;

    public static int countDown(int max_count){
        count = max_count;

        Timer m_timer = new Timer();
        TimerTask m_task = new TimerTask(){
            public void run() {
                if (count > 0)
                {
                    count--;
                } else {
                    m_timer.cancel();
                }
            }
        };
        m_timer.schedule(m_task, 1000);
        return count;
    }
}