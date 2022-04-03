package BusinessLogic;

import java.util.ArrayList;
import Model.*;
public class ShortestQStrategy implements Strategy{
    @Override
    public void addTask(ArrayList<Server> servers, Task t){
        Server candidate = new Server(Integer.MAX_VALUE);
        for (Server s: servers) {
            if(s.getTasks().size() < candidate.getTasks().size())
                candidate = s;
        }
        candidate.addTask(t);
    }
}
