package Model;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    public BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private Integer totalWaitingTime;
    private Integer totalClientsServed;
    private Integer totalServiceTime;

    public Server(Integer waitingPeriod) {
        this.waitingPeriod = new AtomicInteger(waitingPeriod);
        this.tasks = new LinkedBlockingQueue<>();
        this.totalClientsServed =0;
        this.totalWaitingTime = 0;
        this.totalServiceTime = 0;
    }

    public void addTask(Task newTask){
        this.tasks.add(newTask);
        this.waitingPeriod = new AtomicInteger(waitingPeriod.intValue() + newTask.getExTime());
    }

    public void run(){
        while(true) {
            try {
                if(!tasks.isEmpty()){
                    for(Task t: tasks){
                        t.incWaitingTime();
                    }
                    tasks.element().setWaitingTime(tasks.element().getWaitingTime() - 1);
                    tasks.element().setExTime(tasks.element().getExTime() - 1);
                    if(tasks.element().getExTime() == 0){
                        this.totalClientsServed++;
                        this.totalWaitingTime+=tasks.element().getWaitingTime();
                        tasks.take();
                    }
                }
                Thread.sleep(1000);
                if(this.waitingPeriod.intValue() > 0)
                    this.waitingPeriod.decrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> rez = new ArrayList<>();
        for (Task t: this.tasks) {
            rez.add(t);
        }
        return rez;
    }

    @Override
    public String toString() {
       String s = new String();

        for(Task t : tasks)
            s = s + ' ' + t.toString();

       return s;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Integer getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(Integer totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public Integer getTotalClientsServed() {
        return totalClientsServed;
    }

    public void setTotalClientsServed(Integer totalClientsServed) {
        this.totalClientsServed = totalClientsServed;
    }

    public Double getServiceTimeRemaining(){
        Double res =0.0;
        for(Task t:tasks){
            res += t.getExTime();
            return res;
        }
        return res;
    }

    /*
    public Model.Task[] getTasks(){

    }
     */
}
