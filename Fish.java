import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.time.LocalTime;

public class Fish extends Thread {
  // private Thread thread;
  private int groupSize;
  private int fishNum;
  private String name;
  private int groupNum;
  boolean needsGroup = false;
  private MeetingPlace meetPlace;
  private RemoteReef school;
  Semaphore group;

  public Fish(int gs, int fn, MeetingPlace mp, RemoteReef s, Semaphore group) {
    this.group = group;

    this.groupSize = gs;
    this.fishNum = fn;
    this.name = "fish[" + UUID.randomUUID().toString() + "]";
    this.meetPlace = mp;
    this.school = s;
  }

  public int getGS() {
    return groupSize;
  }

  public int getFN() {
    return fishNum;
  }

  public String getFishName() {
    return name;
  }

  public int getGroupNum() {
    return groupNum;
  }

  public void setGroupNum(int gn) {
    this.groupNum = gn;
  }

  public void setGroupSize(int gs) {
    this.groupSize = gs;
  }

  // public void setNumGroupMems(int gm) {
  //   this.numGroupMembers = gm;
  // }

  public void setFishNum(int fn) {
    this.fishNum = fn;
  }

  // public boolean groupFull() {
  //   if(meetPlace.getUngrouped() > 0 && meetPlace.getNumMembers() < groupSize){
  //     return false;
  //   } else {
  //     return true;
  //   }
  // }

  public void goToMeetingPlace() throws InterruptedException {
    int sleeptime = (int) Math.random() * 40 + 10;
    meetPlace.addFish(this);
    this.sleep(sleeptime);
  }

  public void dream() throws InterruptedException {
    this.sleep(7000);
  }

  public void interruptFish(Fish classMate) {
    classMate.interrupt();
  }

  public void run() {
    System.out.println("Fish: " + this.getName() + " going to meeting place at: " + LocalTime.now());
    System.out.println();
    try {
      this.goToMeetingPlace();
    } catch(InterruptedException ie) {
      System.out.println();
      ie.printStackTrace();
    }

    // System.out.println(this.getName() + " is getting Grouped");

    System.out.println(this.getName() + "waits to be grouped at: " + LocalTime.now());
    System.out.println();
    this.needsGroup = true;

    try{
      group.acquire(); //semaphore
    } catch(InterruptedException ie) {
      ie.printStackTrace();
    }

    // meetPlace.readyToBeGrouped(this);




    // System.out.println("Fish: " + this.getName() + " is checking number of Fish grouped");
    if(!meetPlace.notifyMantis) {
      System.out.println("Fish: " + this.getName() + " is checking number of Fish grouped at: " + LocalTime.now());
      System.out.println();
      this.yield();
    } else if(!meetPlace.mantisNotified) {
      System.out.println("Fish: " + this.getName() + " is notifying Mantis at: " + LocalTime.now());
      System.out.println();
      meetPlace.notifyMantis();
    }

    // try {
    //   this.sleep(4000);
    // }catch(InterruptedException ie) {
    //   ie.printStackTrace();
    // }

    /*Fish Being Transported*/
    // System.out.println("Fish: " + this.getName() + " in group: " + this.getGroupNum() + " is waiting to be transported");
    try {
      this.dream();
    } catch(InterruptedException ie) {
      System.out.println("Fish:"+this.getName()+" has been interrupted at: " +LocalTime.now());
      System.out.println();
      int idx = school.atReef.size();
      for(int i = 0; i < idx; i++){
        school.atReef.get(i).interrupt();
        // System.out.println("interrupted by Fish:"+this.getName());
      }
    }

    for(int i = 0; i < fishNum; i++) {
      if(currentThread().getName().equals("Thread "+ i)) {
        for(int j = i; j < fishNum; j++) {
          if(Main.fish[j].isAlive()) {
            try {
              System.out.println("joining: "+Main.fish[j] + " at: " +LocalTime.now());
              System.out.println();
              Main.fish[j].join();
            } catch(InterruptedException ie) {
              ie.printStackTrace();
            }
          }
        }
      }
    }

  }
}
