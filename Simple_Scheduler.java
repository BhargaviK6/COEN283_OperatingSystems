package graphicuserinterfaces_2;
/*Author: Mani Bhargavi Ketha*/
/*COEN 283 Operating Systems*/
/*Angela Musurlian*/
/*September 1, 2016*/

import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
//using semaphores to synchronize the threads
class Thread4 implements Runnable{
	public Thread4(){}
	public void run(){
		Read r1=new Read();
		int th4=r1.getThread4();
		for(int i=0;i<=10;i++){
			System.out.println(th4);
		}
	}
}
class Read  extends Thread4 implements Runnable{
	//overriding the run() method here
	protected int tok=0;
	int th4=4;
	public Read(){

	}
	public int getThread4(){
		return th4;
	}
	public void run(){
		//taking input for thread names to put into arrays
		int Thread1[] = new int[10];
		int Thread2[] = new int[10];
		int Thread3[] = new int[10];
		int Thread4[] = new int[10];
		Scanner scan=new Scanner(System.in);
		//Taking input from console for 4 arguments
		while(System.in != null){

			int th1=scan.nextInt();
			int th2=scan.nextInt();
			int th3=scan.nextInt();
			th4=scan.nextInt();
			//creating semaphores to synchronize the threas
			Semaphore sem1 = new Semaphore(1);
			Semaphore sem2 = new Semaphore(1);
			Semaphore sem3 = new Semaphore(1);
			//Acquires a permit from this semaphore, blocking until one is available, or the thread is interrupted
			try {
				//acquiring the semaphore
				sem1.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//acquiring the semaphore
				sem2.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				//acquiring the semaphore
				sem3.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//acquires a permit if it is available and reduces the number of available permits
			//if no permit is available then the thread becomes disabled and lies dormant till
			//another thread invokes the release() method for that semaphore
			//or another thread interrupts the current thread

			//creating the threads with thread name
			Thread t1 = new Thread(new Simple_Scheduler(sem3, sem1, new int[] { th1, th1, th1, th1, th1, th1, th1, th1, th1, th1 }));
			Thread t2 = new Thread(new Simple_Scheduler(sem1, sem2, new int[] { th2, th2, th2, th2, th2, th2, th2, th2, th2, th2 }));
			Thread t3 = new Thread(new Simple_Scheduler(sem2, sem3, new int[] { th3, th3, th3, th3, th3, th3, th3, th3, th3, th3, }));
			//starting the threads
			t1.start();
			t2.start();
			t3.start();
			sem3.release();
			//Releases a permit, returning it to the semaphore
			// join() method to ensure all threads that started from main must end in order in which they started
			// and also main should end in last
			try {
				t1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				t3.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sem1.release();
			sem2.release();
			sem3.release();
			Thread t4 = new Thread(new Thread4());
			t4.start();

		}

	}


}
public class Simple_Scheduler extends Read implements Runnable {

	private final Semaphore previous;
	private final Semaphore next;
	private final int[] threadName;
	//creating the semaphores
	public Simple_Scheduler(Semaphore previous, Semaphore next, int[] threadName) {
		//parameterized constructor for the Simple_Scheduler
		this.previous = previous;
		this.next = next;
		this.threadName = threadName;
	}

	@Override
	public void run() {
		//Overriding the run method for the thread
		//System.out.println("In thread print!");
		for (int i = 0; i < threadName.length; i++) {
			waitGreen();
			System.out.println(threadName[i]);
			switchGreen();
		}
	}



	private void waitGreen() {
		try {
			previous.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private void switchGreen() {
		next.release();
	}

	public static void main(String argv[]) throws InterruptedException, BrokenBarrierException {
		//Starting point for the whole program
		int token=0;
		//Creating the read keyboard thread which spawns the other threads
		//This is the main thread from where all the other threads and scheduler and semaphores are implemented
		Thread t=new Thread(new Read());
		t.start();
		/*To check the output please enter the following input:*/
		/* 1
		 * 2
		 * 3
		 * 4*/



	}

}