import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.time.LocalTime;

public class MantaRay extends Thread {
	/*Places*/
	MeetingPlace meetPlace;
	RemoteReef school;

  int capacity;
  Semaphore mantis;

  public static long time; //thread start time



  public MantaRay(int capacity, MeetingPlace meetPlace, RemoteReef school, int totalFish, int groupSize, Semaphore mantisAlert) {

  	this.meetPlace = meetPlace;
  	this.school = school;

  	this.capacity = capacity;

  	try{
  		this.mantis = mantisAlert;
  		this.mantis.acquire(1);
  	} catch(InterruptedException e) {
  		e.printStackTrace();
  	}

  	time = System.currentTimeMillis();
  	
  }


  public void msg(String m) {
    System.out.println("[" + (System.currentTimeMillis()-time) + "] " + this.getName() + ": " + m);
  }


  public void run() {
  	System.out.println("Mantis[" + this.getName() + "] is waiting until fish are gathered at: " + LocalTime.now() + '\n');

  	try{
  		mantis.acquire();
  	} catch(InterruptedException e) {
  		e.printStackTrace();
  	}

  	System.out.println("Mantis has been notified at: " + LocalTime.now() + "\n");

  	meetPlace.goToSchool(capacity, school);

  }

}
