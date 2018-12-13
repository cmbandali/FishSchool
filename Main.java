import java.util.concurrent.Semaphore;
import java.time.LocalTime;


public class Main {
  static Fish fish[];
  private static int groups[];
  static Mantis mantis;
  private static Semaphore group; //grouping semiphore

  public static void main(String[] args) {
    int totalFish = 0;
    int groupSize = 0;
    int mantisCapacity = 0;

    /*Get input from user arguments*/
    if(args.length > 0) {
      totalFish = Integer.parseInt(args[0]);
      groupSize = Integer.parseInt(args[1]);
      mantisCapacity = Integer.parseInt(args[2]);
      group = new Semaphore(groupSize);
      group.aquire(groupSize);
    } else {
      totalFish = 13;
      groupSize = 3;
      mantisCapacity = 7;
      group = new Semaphore(groupSize);
      group.aquire(groupSize);
    }
    
    /*Initialize objects*/
    MeetingPlace meetPlace = new MeetingPlace(totalFish, groupSize, group);
    RemoteReef school = new RemoteReef(totalFish, meetPlace);

    fish = new Fish[totalFish];
    for(int i = 0; i < totalFish; i++) {
      fish[i] = new Fish(totalFish, groupSize, meetPlace, school, group);
      fish[i].start();
    }

    mantis = new Mantis(mantisCapacity, meetPlace, school, totalFish, groupSize);
    mantis.start();

    Grouper grouper = new Grouper(totalFish, meetPlace, fish, group);
    grouper.start();
    
    System.out.println("Main Program has done its job and is ending at: " + LocalTime.now());
    System.out.println();
  }
}
