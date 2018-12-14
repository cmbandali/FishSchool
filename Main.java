import java.util.concurrent.Semaphore;
import java.time.LocalTime;


public class Main {

  /*Characters*/
  static Fish fish[];
  static MantaRay mantis;

  /*Places*/
  static MeetingPlace meetPlace;
  static RemoteReef school;

  /*Helper variables*/
  static int groups[];
  static Semaphore fishAlert;
  static Semaphore mantisAlert;


  public static void main(String[] args) {
    int totalFish = 0;
    int groupSize = 0;
    int mantisCapacity = 0;

    /*Get input from user arguments*/
    if(args.length > 0) {
      totalFish = Integer.parseInt(args[0]);
      groupSize = Integer.parseInt(args[1]);
      mantisCapacity = Integer.parseInt(args[2]);
      // group.acquire(groupSize);
    } else {
      totalFish = 13;
      groupSize = 3;
      mantisCapacity = 7;
      
      // group.acquire(groupSize);
    }

    try{

      fishAlert = new Semaphore(totalFish);
      fishAlert.acquire(totalFish);

    } catch(InterruptedException e) {
      e.printStackTrace();
    }

    mantisAlert = new Semaphore(1);
    
    /*Initialize objects*/
    meetPlace = new MeetingPlace(totalFish, groupSize);
    school = new RemoteReef(totalFish, meetPlace, fishAlert);

    fish = new Fish[totalFish];
    for(int i = 0; i < totalFish; i++) {
      fish[i] = new Fish(totalFish, groupSize, meetPlace, school, mantisAlert, fishAlert);
      fish[i].start();
    }

    mantis = new MantaRay(mantisCapacity, meetPlace, school, totalFish, groupSize, mantisAlert);
    mantis.start();

    // Grouper grouper = new Grouper(totalFish, meetPlace, fish, group);
    // grouper.start();
    
    // System.out.println("Main Program has done its job and is ending at: " + LocalTime.now());
    // System.out.println();
  }


}
