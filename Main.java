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
    } else {
      totalFish = 13;
      groupSize = 3;
      mantisCapacity = 7;
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
    school = new RemoteReef(totalFish, meetPlace);

    fish = new Fish[totalFish];
    mantis = new MantaRay(mantisCapacity, meetPlace, school, totalFish, groupSize, mantisAlert);

    for(int i = 0; i < totalFish; i++) {
      fish[i] = new Fish(totalFish, groupSize, meetPlace, school, mantisAlert, mantis);
      fish[i].start();
    }

    meetPlace.addMantis(mantis);
    school.addMantis(mantis);

    mantis.start();
  }


}
