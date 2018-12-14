import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.time.LocalTime;

public class Fish extends Thread {

    /*Places*/
    MeetingPlace meetPlace;
    RemoteReef school;

    /*Semaphores*/
    Semaphore group;
    Semaphore mutex;
    Semaphore mantis;
    Semaphore fishAlert;

    /*Traking variables*/
    int totalFish;
    int groupSize;
    int groupNumber;

    public static long time; //thread start time


  public Fish(int totalFish, int groupSize, MeetingPlace meetPlace, RemoteReef school, Semaphore mantis, Semaphore fishAlert) {

    this.meetPlace = meetPlace;
    this.school = school;

    // this.group = group;
    this.mutex = new Semaphore(1);
    this.mantis = mantis;

    this.totalFish = totalFish;
    this.groupSize = groupSize;
    this.groupNumber = 0;

    time = System.currentTimeMillis();

  }

  public void msg(String m) {
    System.out.println("[" + (System.currentTimeMillis()-time) + "] " + " Fish[" + this.getName() + "]" + ": " + m);
  }


  public void run() {
    System.out.println("Fish[" + this.getName() + "] is going to meeting place at: " + LocalTime.now());

    int sleepTime = (int) Math.random() * 40 + 10;
    try{

      this.sleep(sleepTime);
      
    } catch(InterruptedException ie) {
      System.out.println();
      ie.printStackTrace();
    }
    meetPlace.addFish(this);

    /*Getting grouped*/
    int[] groupInfo = meetPlace.getIntoGroup(this);
    this.groupNumber = groupInfo[0];

    /*checking to see if Manits should be notified*/
    try{

      mutex.acquire();
      System.out.println("Fish[" + this.getName() + "] is checking that everyone has been grouped: " + LocalTime.now());
      if(groupInfo[1] == 1){
        System.out.println("Fish[" + this.getName() + "] is notifying Mantis at: " + LocalTime.now());
        mantis.release();
      }
      mutex.release();
      
    } catch(InterruptedException ie) {
      System.out.println();
      ie.printStackTrace();
    }

    // try{
    //   fishAlert.acquire();
    //   this.msg("has been released at school");
    // } catch(InterruptedException e) {
    //   e.printStackTrace();
    // }


  }


}
