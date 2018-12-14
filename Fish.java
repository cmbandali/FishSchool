import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.time.LocalTime;

public class Fish extends Thread {

    /*Places*/
    MeetingPlace meetPlace;
    RemoteReef school;
    MantaRay mantis;

    /*Semaphores*/
    Semaphore fish;
    Semaphore mutex;
    Semaphore mantisAlert;
    Semaphore fishAlert;

    /*Traking variables*/
    int totalFish;
    int groupSize;
    int groupNumber;

    public static long time; //thread start time



  public Fish(int totalFish, int groupSize, MeetingPlace meetPlace, RemoteReef school, Semaphore mantisAlert, MantaRay mantis) {

    this.meetPlace = meetPlace;
    this.school = school;
    this.mantis = mantis;

    this.fish = new Semaphore(1);
    this.mutex = new Semaphore(1);
    this.mantisAlert = mantisAlert;

    this.totalFish = totalFish;
    this.groupSize = groupSize;
    this.groupNumber = 0;

    time = System.currentTimeMillis();

    /*Set individual thread semaphore to zero, used for being signaled to transport, being signaled when at school and being signaled when school ends*/
    try {
      fish.acquire(1);
    } catch(InterruptedException e) {
      System.out.println("Error while setting individual fish semaphore to zero");
    }

  }


  public void signalFish(Fish fish, String msg) {

    try{
      mutex.acquire();
      this.msg(msg);
      mantis.fishex.get(fish).release();
      mutex.release();
    } catch(InterruptedException e){
      e.printStackTrace();
    }

  }


  public void signalMantis(String msg) {
    this.msg(msg);
    mantisAlert.release();
  }


  public long fishTime() {
    return System.currentTimeMillis()-time;
  }



  public void msg(String m) {
    System.out.println("[" + (System.currentTimeMillis()-time) + "] " + " Fish[" + this.getName() + "]" + ": " + m);
  }



  public void run() {
    this.msg("is going to meeting place");

    int sleepTime = (int) Math.random() * 40 + 10;
    try{

      this.sleep(sleepTime);
         
    } catch(InterruptedException ie) {
      System.out.println("Error when trying to sleep while going to meeting place");
    }

    meetPlace.addFish(this);

    /*Getting grouped*/
    int[] groupInfo = meetPlace.getIntoGroup(this);
    this.groupNumber = groupInfo[0];

    /*checking to see if Manits should be notified*/
    try{

      mutex.acquire();
      this.msg("is checking if all fish have been grouped");
      if(groupInfo[1] == 1){
        this.signalMantis("is letting mantis know they are ready for school");

      }
      mutex.release();
      
    } catch(InterruptedException ie) {
      System.out.println("Error while either checking that fish have been grouped or notifying mantis");
    }

    mantis.addSemaphore(fish, this);

    /*needs to be signaled to transport*/
    try{
      fish.acquire();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    /*needs to be signaled when at school*/
    try{
      fish.acquire();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    /*needs to be signaled when school day ends*/
    try{
      fish.acquire();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }

  } //run


}
