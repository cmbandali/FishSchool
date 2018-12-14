import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class RemoteReef {
  ArrayList<Fish> atReef;
  int fishExpected;
  MeetingPlace meetPlace;

  Semaphore fishAlert;

  public RemoteReef(int fishExpected, MeetingPlace mp, Semaphore fishAlert) {
    this.fishExpected = fishExpected;
    atReef = new ArrayList<>();
    meetPlace = mp;
    this.fishAlert = fishAlert;
  }

  public void goToReef(Fish[] travelingFish, int numFish) {

    for(int i = 0; i < numFish; i++){
      atReef.add(travelingFish[i]);
      System.out.println("Fish[" + travelingFish[i].getName() + "] is at school");
      // fishAlert.release();
    }

  }

  // public void arriveAtReef(int[] group) {
  //   if(!atReef.isEmpty()){
  //     double random = Math.random() * (atReef.size() + 1);
  //     // System.out.println((int)random);
  //     Fish f = atReef.get((int)random);
  //     f.interrupt();
  //   }
  //   for(int i = 0; i < group.length; i++) {
  //     // System.out.println("group: "+group[i]+" arrived at Reef");
  //     ArrayList<Fish> fish = meetPlace.groupMembers.get(group[i]);
  //     for(int j = 0; j < fish.size(); j++) {
  //       if( !atReef.contains(fish.get(j)) ) {
  //         atReef.add(fish.get(j));
  //         System.out.println("Fish: " + fish.get(j).getName() + " is at Reef");
  //       }
  //     }
  //   }
  //   System.out.println("total fish at Reef: "+ atReef.size());
  // }

}
