package BusinessLogic;

import java.util.ArrayList;
import Model.*;
public class Scheduler {
    public ArrayList<Server> servers;
    private Integer maxNoServers;
    private Integer maxTasksPerServer;
    private Strategy strategy;
    public ArrayList<Thread> threads;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        servers = new ArrayList<>();
        threads = new ArrayList<>();
        strategy = new TimeStrategy();
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        for(int i = 0; i < maxNoServers; i++){
            Server s = new Server(0);
            this.servers.add(s);
            Thread t = new Thread(s);
            this.threads.add(t);
        }
    }

    public Integer getCrtClients(){
        Integer res=0;
        for(Server s : servers){
            res += s.tasks.size();
        }
        return res;
    }

    public void changeStrategy(SelectionPolicy policy){

        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQStrategy();
        }

        else strategy = new TimeStrategy();
    }

    public boolean areAllQueuesEmpty(){
        for (Server s : servers){
            if(!s.tasks.isEmpty())
                return false;
        }
        return true;
    }

    public Double getAvgTime(){
        Double res =0.0;
        for(Server s: servers) {
            System.out.println(s.getTotalWaitingTime().doubleValue() + "    " + s.getTotalClientsServed());
            Double resaux = s.getTotalWaitingTime().doubleValue() / s.getTotalClientsServed();
            res += resaux;
        }

        //res = res / this.servers.size();
        return  res;
    }

    public Integer getSchedulerTotalClientsServed(){
        Integer res = 0;
        for(Server s: servers){
            res+= s.getTotalClientsServed();
        }
        return res;
    }



    public void dispatchTask(Task t){
        this.strategy.addTask(this.servers, t);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

}
