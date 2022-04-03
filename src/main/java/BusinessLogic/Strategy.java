package BusinessLogic;

import java.util.ArrayList;
import Model.*;
public interface Strategy {
    public void addTask(ArrayList<Server> servers, Task t);
}
