import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.time.LocalTime;


public class MeetingPlace {

	/*characters*/
	ArrayList<Fish> fish;
	MantaRay mantis;

	/*helpers*/
  int totalFish;
  int numArrived;

  int groupSize;
  int groupNumber;
  int numGroupMembers;
  int numFishGrouped;
  Fish[][] groupMembers;

  /*Semaphores*/
  static Semaphore mutex;
  static Semaphore group;


  public MeetingPlace(int totalFish, int groupSize) {

  	// this.school = school;

  	mutex = new Semaphore(1);
  	// this.group = group;

  	this.totalFish = totalFish;
  	this.groupSize = groupSize;
  	this.numGroupMembers = 0;
  	this.numFishGrouped = 0;
  	this.groupNumber = 0;

  	int numGroups = totalFish / groupSize;
  	if(totalFish % groupSize != 0) numGroups++;
  	this.groupMembers = new Fish[numGroups][groupSize];

  	this.numArrived = 0;
  	this.fish = new ArrayList<>();

  }

  public void addMantis(MantaRay mantis) {
  	this.mantis = mantis;
  }

  public void addFish(Fish fish) {

		try {

			mutex.acquire();
			this.fish.add(fish);
			numArrived++;
			// System.out.println("Fish[" + fish.getName() + "] has arrived at meetingPlace at: " + LocalTime.now());
			fish.msg("has arrived at the meeting place");
			mutex.release();

		} catch(InterruptedException e) {

				// e.printStackTrace();
			System.out.println("error in addFish() in MeetingPlace");

		}

  }


  public int[] getIntoGroup(Fish fish) {
  	int gn = 0;
  	int lastGrouped = 0;

  	try{

  		mutex.acquire();
  		/*group the fish*/
  		if(numGroupMembers < groupSize) {
  			groupMembers[groupNumber][numGroupMembers++] = fish;
  			fish.msg("is a new member of group: " + groupNumber);
  			gn = groupNumber;
  		} else {
  			groupNumber++;
  			numGroupMembers = 0;
  			groupMembers[groupNumber][numGroupMembers++] = fish;
  			fish.msg("is a new member of group: " + groupNumber);
  			gn = groupNumber;
  			if(groupNumber*groupSize+numGroupMembers == totalFish){
  				lastGrouped = 1;
  			}
  		}
  		mutex.release();

	  } catch(InterruptedException e) {

	  		e.printStackTrace();

	  }

	  int[] groupInfo = new int[2];
	  groupInfo[0] = gn;
	  groupInfo[1] = lastGrouped;

	  return groupInfo;

	}


	public void goToSchool(int travelCapacity, RemoteReef school){

		int currentNumOfTravelers = 0;
		int totalTraveled = 0;
		int index = 0;
		Fish[] travelingFish = new Fish[travelCapacity];

		try{
			mutex.acquire();

			for(int i = 0; i < groupMembers.length; i++) {

				if((currentNumOfTravelers + groupMembers[i].length) <= travelCapacity){

					System.out.println("group[" + i + "] is traveling");

					for(int f = 0; f < groupMembers[i].length; f++) {
						if(groupMembers[i][f] != null) {
							travelingFish[index++] = groupMembers[i][f];
							currentNumOfTravelers++;
							totalTraveled++;

							/*Signal fish that they are going to travel to school*/
							String msg = "Fish[" + groupMembers[i][f].getName() + "] is traveling to school";						
							mantis.signalFish(groupMembers[i][f], msg);
						}

						if(totalTraveled == totalFish) {
							school.goToReef(travelingFish, currentNumOfTravelers);
							f = groupMembers[i].length;
						}
					}

				} else {

					school.goToReef(travelingFish, currentNumOfTravelers);
					travelingFish = new Fish[travelCapacity];
					index = 0;
					currentNumOfTravelers = 0;
					i--;

				}

			} //for

			mutex.release();

		} catch(InterruptedException e) {
			e.printStackTrace();
		}

	} //end of method


}
