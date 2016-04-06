import java.io.File;
import java.util.concurrent.TimeoutException;


public class TimeoutController {
	private TimeoutController(){
		
	}
	
	public static boolean execute (Thread task, long timeout,String fileNameSol) {
		File f=new File(fileNameSol);
		f.delete();
		task.start();
		Boolean b=false;
		synchronized(task){
			try{
		int t=0;
		while (!b && t<timeout){
			System.out.println("Waiting for the planner...");
			if(f.exists()){return true;}
				task.wait(300);t=t+300;}
		}catch(Exception e){}
		}
return b;}
	
	
}
	

