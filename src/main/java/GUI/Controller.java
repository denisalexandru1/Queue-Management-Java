package GUI;

import BusinessLogic.*;
import com.sun.corba.se.spi.activation.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {
    public Server model;
    public View view;
    public static SimulationManager manager;
    public Controller(View view) {
        this.model = model;
        this.view = view;

        //view.setVisible(true);

        this.manager = new SimulationManager(this.view);

        view.simBtnListener(new SimBtnListener());
    }


    class SimBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Controller.manager = new SimulationManager(view.getSimInterval(), view.getArrMin(), view.getArrMax(), view.getExMax(), view.getExMin(), view.getNoQ(), view.getNoClients(), SelectionPolicy.SHORTEST_TIME, view, Controller.this);
            Thread t = new Thread(manager);
            t.start();
        }
    }


}
