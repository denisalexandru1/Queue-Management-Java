package BusinessLogic;


import GUI.Controller;
import GUI.View;
import Model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationManager implements Runnable{
    public Integer timeLimit;
    public Integer minArrTime;
    public Integer maxArrTime;
    public Integer maxProcessingTime;
    public Integer minProcessingTime;
    public Integer noServers;
    public Integer noClients;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_QUEUE.SHORTEST_TIME;
    public Integer crtTime;
    public Integer peakHour;
    public Integer peakHourClients;

    private Scheduler scheduler;
    private ArrayList<Task> generatedTasks;
    private View view;
    private Controller controller;

    public String getQueuesInfo(){
        String s = new String();
        System.out.println(generatedTasks.size());
        for (Task t : this.generatedTasks){
            s = s + t.toString() + " ";
        }
        s= s + '\n';
        System.out.println();
        for (int i = 0; i <noServers; i++){
            //System.out.println(this.scheduler.servers.get(i).toString());
                s = s + "Queue " + i + ": " + scheduler.servers.get(i).toString() + '\n';
        }
        s=s+("Current time: " + this.crtTime + '\n');
        s=s+("\n====================================END OF LOG====================================\n");
        return s;
    }

    public void createFile() {
        try {
            File newFile = new File("logs.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                newFile.delete();
                newFile.createNewFile();
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void appendFile(String s){
        try {
            FileWriter myWriter = new FileWriter("logs.txt");
            myWriter.append(s);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void generateRandomTasks(){
        this.generatedTasks = new ArrayList<>();
        for(int i =0; i<this.noClients; i++) {
            Task newTask = new Task(i, 1 + (int)(Math.random() * ((this.maxArrTime - minArrTime) + 1)), minProcessingTime + (int)(Math.random() * ((maxProcessingTime - minProcessingTime) + 1)));
            this.generatedTasks.add(newTask);
            this.generatedTasks.sort(Task::compareTo);
        }
    }

    public Double getTotalSerTime(){
        Double res = 0.0;
        for(Task t: this.generatedTasks)
            res += t.getExTime();
        return res;
    }

    public Double getQSerTimeRemaining(){
        Double res =0.0;
        for(Server s: this.scheduler.servers){
            res = res + s.getServiceTimeRemaining();
        }
        return res;
    }

    public void updatePeakHour(){
        if(this.scheduler.getCrtClients()>this.peakHourClients) {
            this.peakHourClients= this.scheduler.getCrtClients();
            this.peakHour = this.crtTime;
        }
    }


    public SimulationManager(View view) {
        this.timeLimit = 0;
        this.minArrTime = 0;
        this.maxArrTime = 0;
        this.maxProcessingTime = 0;
        this.minProcessingTime = 0;
        this.noServers = 0;
        this.noClients = 0;
        this.selectionPolicy = SelectionPolicy.SHORTEST_TIME;

        Scheduler scheduler = new Scheduler(0, 0);
        this.scheduler = scheduler;
        this.generatedTasks = new ArrayList<>();
        this.view = view;
        this.peakHour = 0;
        this.peakHourClients = 0;

    }

    public SimulationManager(Integer timeLimit, Integer minArrTime, Integer maxArrTime, Integer maxProcessingTime, Integer minProcessingTime, Integer noServers, Integer noClients, SelectionPolicy selectionPolicy, View view, Controller controller) {
        this.timeLimit = timeLimit;
        this.minArrTime = minArrTime;
        this.maxArrTime = maxArrTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.noServers = noServers;
        this.noClients = noClients;
        this.selectionPolicy = selectionPolicy;
        this.scheduler = scheduler;
        this.generatedTasks = generatedTasks;
        this.view = view;
        this.controller = controller;
        this.peakHour = 0;
        this.peakHourClients = 0;
        this.scheduler = new Scheduler(this.noServers, 20);
        for(Thread t : scheduler.threads)
            t.start();
        generateRandomTasks();

    }

    @Override
    public void run(){
            this.crtTime = 0;
            createFile();
            Double avgSer = this.getTotalSerTime();

            while(crtTime <= this.timeLimit){
                if(((this.generatedTasks.isEmpty()) && (this.scheduler.areAllQueuesEmpty())))
                    break;
                //System.out.println(crtTime);
                ArrayList<Task> newList = new ArrayList<>();
                for (Task t : this.generatedTasks) {
                    if (t.getArrTime() == crtTime) {
                        scheduler.dispatchTask(t);
                    } else newList.add(t);
                }
                String s = this.getQueuesInfo();
                view.queuesPane.append(s);
                updatePeakHour();
                this.crtTime++;
                this.generatedTasks = newList;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            avgSer = (avgSer - this.getTotalSerTime() - this.getQSerTimeRemaining())/this.scheduler.getSchedulerTotalClientsServed();
            view.queuesPane.append("\nAverage Waiting time: " + this.scheduler.getAvgTime() +'\n');
            view.queuesPane.append("\nAverage Service time: " + avgSer +'\n');
            view.queuesPane.append("\nPeak hour: " + this.peakHour + '\n');
            String s = view.queuesPane.getText();
            //s = s + ("\nAverage Waiting time: " + this.scheduler.getAvgTime() +'\n');
            appendFile(s);
    }
}

