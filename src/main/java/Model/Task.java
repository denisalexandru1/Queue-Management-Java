package Model;

public class Task {
    private int id;
    private int arrTime;
    private int exTime;
    private int waitingTime;

    public Task(int id, int arrTime, int exTime) {
        this.id = id;
        this.arrTime = arrTime;
        this.exTime = exTime;
        this.waitingTime = 0;
    }

    @Override
    public String toString() {
        return "(" + id + ", " + arrTime + ", " + exTime + ')';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrTime() {
        return arrTime;
    }

    public void setArrTime(int arrTime) {
        this.arrTime = arrTime;
    }

    public int getExTime() {
        return exTime;
    }

    public void setExTime(int exTime) {
        this.exTime = exTime;
    }

    public void incWaitingTime(){
        this.waitingTime++;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int compareTo(Task t){
        return this.arrTime - t.arrTime;
    }


}
