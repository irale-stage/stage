import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;


public class Subs {
	public HashMap<Term,Term> li;
	public HashMap<Term,Term> revLi;
	
	
public Subs(){
	li=new HashMap<Term,Term>();
	revLi=new HashMap<Term,Term>();
}
	
public void add(Term t1,Term t2){
		li.put(t1, t2);
		revLi.put(t2, t1);
}

public Term find(Term t){
	if (li.containsKey(t)) {
		return li.get(t);
	}else{return null;}
}

public Term in(Term t){
	Term ttt=null;
	Term tt=this.find(t);
	if (tt!=null){
		ttt=this.revLi.get(tt);}
return ttt;}


public int couv(Subs s){
	//incomp 0, tCs&sCt=1, tCs=2, sCt=3, vide=4
	Iterator<Entry<Term,Term>> itS=s.li.entrySet().iterator();
	int nbT=this.li.size();
	boolean sCt=true,tCs=true; 
	while (itS.hasNext()){
		Entry<Term,Term> sE1=itS.next();
		Term tsk=sE1.getKey();
		if (!this.li.containsKey(tsk)){
			sCt=false;}
		else{nbT--;
			if(s.li.get(tsk)!=this.li.get(tsk)){
				return 0;}
		}		
	}
	if (nbT>0){tCs=false;}
	if (tCs && sCt){return 1;}
	if (tCs){return 2;}
	if (sCt){return 3;}
return 4;}

public void print(){
	Iterator<Entry<Term,Term>> itS=this.li.entrySet().iterator();
	System.out.print(" {");
	while(itS.hasNext()){
		Entry<Term,Term> e=itS.next();
		System.out.print(" "+e.getKey().name+"/"+e.getValue().name);
	}
	System.out.print(" }");	}

public static void llprint(LinkedList<Subs> s){
	ListIterator<Subs> ss=s.listIterator();
	System.out.println();
	System.out.println("Substitution non-recouverte:");
	while (ss.hasNext()){
		ss.next().print();
		
	}
}

public Subs copie(){
	Subs ret=new Subs();
	ret.li.putAll(this.li);
	ret.revLi.putAll(this.revLi);
	return ret;}

public boolean egal(Subs s){
	Iterator<Entry<Term, Term>> it=this.li.entrySet().iterator();
	HashMap<Term,Term> s2=(HashMap<Term, Term>) s.li.clone();
	while(it.hasNext()){
		Entry<Term,Term> e=it.next();
		if (!s2.containsKey(e.getKey())){return false;}
		if (s2.get(e.getKey())!=e.getValue()){return false;}
		s2.remove(e.getKey());}
	if(s2.isEmpty()){return true;}
	return false;}

}
