package GUI;

import com.sun.corba.se.spi.activation.Server;
import javafx.concurrent.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    public JTextArea queuesPane = new JTextArea(30,100);
    public JScrollPane scroll;
    private JTextField textFieldC = new JTextField();
    private JTextField textFieldQ = new JTextField();
    private JTextField textFieldTMax = new JTextField();
    private JTextField textFieldMinArrTime = new JTextField();
    private JTextField textFieldMaxArrTime = new JTextField();
    private JTextField textFieldMinSerTime = new JTextField();
    private JTextField textFieldMaxSerTime = new JTextField();
    private JButton simulationButton = new JButton("Start simulation");

    public void simBtnListener(ActionListener SimBtnListener){
        simulationButton.addActionListener(SimBtnListener);
    }

    public Integer getNoClients(){
        String s = textFieldC.getText();
        return Integer.parseInt(s);
    }

    public Integer getNoQ(){
        String s = textFieldQ.getText();
        return Integer.parseInt(s);
    }

    public Integer getSimInterval(){
        String s = textFieldTMax.getText();
        return Integer.parseInt(s);
    }
    public Integer getArrMin(){
        String s = textFieldMinArrTime.getText();
        return Integer.parseInt(s);
    }
    public Integer getArrMax(){
        String s = textFieldMaxArrTime.getText();
        return Integer.parseInt(s);
    }
    public Integer getExMin(){
        String s = textFieldMinSerTime.getText();
        return Integer.parseInt(s);
    }
    public Integer getExMax(){
        String s = textFieldMaxSerTime.getText();
        return Integer.parseInt(s);
    }

    public View(){

        //JFrame frame = new JFrame("Queues");

        queuesPane.setPreferredSize(new Dimension(6000, 6000));
        //queuesPane.setEditable(false);
        //queuesPane.setCaretPosition(queuesPane.getDocument().getLength());

        textFieldC.setPreferredSize(new Dimension(10,10));
        textFieldQ.setPreferredSize(new Dimension(10,10));
        textFieldTMax.setPreferredSize(new Dimension(10,10));
        textFieldMinArrTime.setPreferredSize(new Dimension(10,10));
        textFieldMaxArrTime.setPreferredSize(new Dimension(10,10));
        textFieldMaxSerTime.setPreferredSize(new Dimension(10,10));
        textFieldMaxSerTime.setPreferredSize(new Dimension(10,10));
        JLabel labelC = new JLabel("Number of clients: ");
        JLabel labelQ = new JLabel("Number of queues: ");
        JLabel labeltmax = new JLabel("Simulation interval: ");
        JLabel labelArrTime = new JLabel("Arrival time interval: ");
        JLabel labelSerTime = new JLabel("Service time interval: ");
        JPanel labelsPanel = new JPanel(new GridLayout(5, 3));
        labelsPanel.add(labelC);
        labelsPanel.add(textFieldC);
        labelsPanel.add(new JLabel());
        labelsPanel.add(labelQ);
        labelsPanel.add(textFieldQ);
        labelsPanel.add(new JLabel());
        labelsPanel.add(labeltmax);
        labelsPanel.add(textFieldTMax);
        labelsPanel.add(new JLabel());
        labelsPanel.add(labelArrTime);
        labelsPanel.add(textFieldMinArrTime);
        labelsPanel.add(textFieldMaxArrTime);
        labelsPanel.add(labelSerTime);
        labelsPanel.add(textFieldMinSerTime);
        labelsPanel.add(textFieldMaxSerTime);

        JScrollPane scrollPanelQ = new JScrollPane(queuesPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPanelQ.setViewportView(queuesPane);
        //queuesPane.update(queuesPane.getGraphics());


        JPanel finalPanel = new JPanel(new FlowLayout());
        finalPanel.setSize(getToolkit().getScreenSize());
        finalPanel.add(labelsPanel);
        finalPanel.add(scrollPanelQ);
        finalPanel.add(simulationButton);


        this.setContentPane(finalPanel);

        //this.getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setResizable(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
