import java.util.concurrent.Semaphore;
import java.time.LocalTime;


public class Grouper extends Thread {

  private MeetingPlace meetPlace;
  static Fish fish[];
  private int numFish;
  private int groupNum;
  private Semaphore group;

  public Grouper(int nf, MeetingPlace mp, Fish[] fish, Semaphore group) {
    numFish = nf;
    this.fish = fish;
    meetPlace = mp;

    /*Semaphore*/
    this.group = group;
  }

  public void waitForFish()throws InterruptedException {
    this.sleep((int) Math.random() * 4000 + 200); //wait for fish to be ready to group
  }

  public void run() {
    // try{
    //   this.waitForFish();
    // } catch(InterruptedException ie){
    //   System.out.println();
    //   ie.printStackTrace();
    // }
    // while(meetPlace.getUngrouped() > 0){
    //   for(int i = 0; i < numFish; i++) {
    //     if(!meetPlace.groupFull()){
    //       /*comment print lines out*/
    //       // System.out.println("Boolean Value: "+ meetPlace.groupFull());
    //       // System.out.println(fish[i].getName() + " is getting Grouped");
    //       // fish[i].interruptFish(fish[i]);

    //       fish[i].setGroupNum(meetPlace.getGroupNum());
    //       meetPlace.addGroupMember();
    //       System.out.println(fish[i].getName() + ": group number is: " + fish[i].getGroupNum() + "\n" + " and was grouped at: " + LocalTime.now());
    //       System.out.println();
    //       if(i+1 == numFish) meetPlace.groupFull();
    //       group.release();
    //     } else {

    //       /*comment print lines out*/
    //       // System.out.println("Boolean Value: "+ meetPlace.groupFull());
    //       // System.out.println("group members: " + meetPlace.getNumMembers());
    //       // System.out.println(fish[i].getName() + " is getting Grouped in new group");

    //       meetPlace.fillNextGroup();
    //       fish[i].setGroupNum(meetPlace.getGroupNum());
    //       meetPlace.addGroupMember();
    //       System.out.println(fish[i].getName() + ": group number is: " + fish[i].getGroupNum() + "\n" + " and was grouped at: " + LocalTime.now());
    //       System.out.println();
    //       if(i+1 == numFish) meetPlace.groupFull();
    //       group.release();
    //     }
    //     meetPlace.doneGrouping(fish[i]);
    //   }
    // }
  }
}
