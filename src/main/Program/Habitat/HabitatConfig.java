package Habitat;

public class HabitatConfig {
    private int total, totalWorker, totalWarrior, workerSpawnTime, warriorSpawnTime, wrkLive, wrrLive;
    private float warriorPercent, workerPercent;
    {
        warriorPercent = 0.6f;
        workerPercent = 0.3f;
        workerSpawnTime = 5000;
        warriorSpawnTime = 10000;
        wrrLive = 15000;
        wrkLive = 8000;
    }


    public int getTotal() {
        return total;
    }

    public int getTotalWorker() {
        return totalWorker;
    }

    public int getTotalWarrior() {
        return totalWarrior;
    }

    public int getWorkerSpawnTime() {
        return workerSpawnTime;
    }

    public int getWarriorSpawnTime() {
        return warriorSpawnTime;
    }

    public int getWrkLive() {
        return wrkLive;
    }

    public int getWrrLive() {
        return wrrLive;
    }

    public float getWarriorPercent() {
        return warriorPercent;
    }

    public float getWorkerPercent() {
        return workerPercent;
    }

    public void setCountToZero(){
        total = totalWarrior = totalWorker = 0;
    }
    public void setWarriorPercent(float percent){
        warriorPercent = percent;
    }
    public void setWorkerPercent(float percent) {
        workerPercent = percent;
    }

    public void setTotal() {
        this.total = totalWarrior + totalWorker;
    }

    public void incrementTotalWorker() {
        this.totalWorker++;
    }

    public void incrementTotalWarrior() {
        this.totalWarrior++;
    }


    public void setWorkerSpawnTime(int time){
        workerSpawnTime = time;
    }
    public void setWarriorSpawnTime(int time){
        warriorSpawnTime = time;
    }
    public void setWrkLive(int time){
        wrkLive = time;
    }
    public void setWrrLive(int time){
        wrrLive = time;
    }
}
