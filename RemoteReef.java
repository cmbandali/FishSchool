import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class RemoteReef {
  ArrayList<Fish> atReef;
  int fishExpected;
  MeetingPlace meetPlace;
  MantaRay mantis;


  public RemoteReef(int fishExpected, MeetingPlace mp) {
    this.fishExpected = fishExpected;
    atReef = new ArrayList<>();
    meetPlace = mp;
  }

  public void addMantis(MantaRay mantis) {
    this.mantis = mantis;
  }

  public void goToReef(Fish[] travelingFish, int numFish) {
    System.out.println();
    for(int i = 0; i < numFish; i++){
      atReef.add(travelingFish[i]);

      /*Signal fish that they are going to travel to school*/
      String msg = "Fish[" + travelingFish[i].getName() + "] is at school at [" + travelingFish[i].fishTime() + "] fish-time";           
      mantis.signalFish(travelingFish[i], msg);
    }

    System.out.println();

    if(atReef.size() == fishExpected) {
      String msg = "is done transporting fish to school and is taking a break \n";
      mantis.msg(msg);
      mantis.sleep();
      this.schoolDayEnds();
    }

  }

  public void schoolDayEnds() {
    String msg = "is signaling Fish[" + atReef.get(0).getName() + "] that the school day has ended";
    mantis.signalFish(atReef.get(0), msg);

    for(int i = 1; i < atReef.size(); i++) {
      msg = "is signaling Fish[" + atReef.get(i).getName() + "] that the school day has ended";
      atReef.get(i-1).signalFish(atReef.get(i), msg);
    }

    /*letting mantis know that he can leave*/
    atReef.get(atReef.size() - 1).signalMantis("is letting Mantis know that he can leave");

  }

}
