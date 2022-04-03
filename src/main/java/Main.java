import GUI.Controller;
import GUI.View;
import BusinessLogic.SimulationManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {
        /*
        BlockingQueue q = new ArrayBlockingQueue(3);
        Prod p = new Prod(q, 10);
        Cons c = new Cons(q, 10);

        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);

        t1.start();
        t2.start();
        */

        //Model.Server model = new Model.Server();

        View view = new View();
        Controller controller = new Controller(view);
        view.setVisible(true);
    }

}
