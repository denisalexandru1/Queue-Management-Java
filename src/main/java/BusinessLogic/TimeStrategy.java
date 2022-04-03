package BusinessLogic;

import java.util.ArrayList;
import Model.*;
public class TimeStrategy implements Strategy{

    @Override
    public void addTask(ArrayList<Server> servers, Task t){
        Server candidate = new Server(Integer.MAX_VALUE);
        for (Server s: servers) {
            if(s.getWaitingPeriod().intValue() < candidate.getWaitingPeriod().intValue())
                candidate = s;
        }
        candidate.addTask(t);
    }
}
