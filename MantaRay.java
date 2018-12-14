import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

public class MantaRay extends Thread {
	/*Places*/
	MeetingPlace meetPlace;
	RemoteReef school;

  int capacity;
  Semaphore mantis;
  Semaphore mutex;
  static ConcurrentHashMap<Fish, Semaphore> fishex;

  public static long time; //thread start time



  public MantaRay(int capacity, MeetingPlace meetPlace, RemoteReef school, int totalFish, int groupSize, Semaphore mantisAlert) {

  	this.meetPlace = meetPlace;
  	this.school = school;

  	this.capacity = capacity;

  	try{

  		this.mantis = mantisAlert;
  		this.mantis.acquire(1);

  		this.mutex = new Semaphore(1);
  		this.fishex = new ConcurrentHashMap<>();

  	} catch(InterruptedException e) {
  		e.printStackTrace();
  	}

  	time = System.currentTimeMillis();
  	
  }



  public void addSemaphore(Semaphore signal, Fish fish) {

  	try{
  		mutex.acquire();
	  	fishex.put(fish, signal);
	  	mutex.release();
  	} catch(InterruptedException e){
  		e.printStackTrace();
  	}

  }


  public void sleep(){
  	try{
  		this.sleep(5000);
  		this.msg("break is over\n");
  	} catch(InterruptedException e) {
  		System.out.println("Mantis interrupted while sleeping");
  	}
  }



  public void signalFish(Fish fish, String msg) {

  	try{
  		mutex.acquire();
  		this.msg(msg);
	  	fishex.get(fish).release();
	  	mutex.release();
  	} catch(InterruptedException e){
  		e.printStackTrace();
  	}

  }



  public void msg(String m) {
    System.out.println("[" + (System.currentTimeMillis()-time) + "] " + "Mantis[" + this.getName() + "]: " + m);
  }



  public void run() {
  	this.msg("is waiting until fish are grouped \n");

  	try{
  		mantis.acquire();
  	} catch(InterruptedException e) {
  		System.out.println("Error while acquiring mantis semaphore because mantis is waiting until fish gather");
  	}

  	this.msg("mantis has been notified that fish are ready for school \n");

  	meetPlace.goToSchool(capacity, school);

  	try{
  		mantis.acquire();
  		this.msg("is leaving for the day! \n Goodbye!");
  	} catch(InterruptedException e) {
  		System.out.println("Error while acquiring mantis semaphore while he waits to be signaled to leave for the day");
  	}

  }



}
