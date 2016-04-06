import java.util.LinkedList;
import java.util.ListIterator;


public class Global {
	
	public static int contV=1;
	public static int contC=1;
	public static int contR=1;
	public static int contP=1;
	public static int contA=1;
	
	
	public static int nbAr=0;
	public static LinkedList<Subs> susu=new LinkedList<Subs>();
	
	public static void susuAdd(Subs s){
		ListIterator<Subs> lisu=susu.listIterator();
		while(lisu.hasNext()){
			if (lisu.next().egal(s)){return;}}
		susu.add(s);
	}

	public static int makeRandInt(int min,int max){
		return (int)(Math.random()*(max-min))+min;}

}