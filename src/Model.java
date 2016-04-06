import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map.Entry;


public class Model {
	public HashMap<Rule,LinkedList<Rule[]>> rules;
	public LinkedList<Rule> contr_ex;
	public ModPlanner pl;
	
	
	public Model(HashMap<String,Term> objs){
		this.rules=new HashMap<Rule,LinkedList<Rule[]>>();
		this.contr_ex=new LinkedList<Rule>();
		this.pl=new ModPlanner(objs);
	}
	
	public void pl(AtomSet init,Effect goal){
		this.pl.usePlan(this.rules,init,goal);
	}
	

	
	public void print(){
		System.out.print("Rules:");
		Iterator<Entry<Rule,LinkedList<Rule[]>>> hm=rules.entrySet().iterator();
		while(hm.hasNext()){
		hm.next().getKey().print();}
		System.out.println("");
	}
	
	public void irale(Rule ex){
		Iterator<Entry<Rule, LinkedList<Rule[]>>> liru = this.rules.entrySet().iterator();
		LinkedList<Rule> lx=new LinkedList<Rule>();
		Subs sig=new Subs();
		while (liru.hasNext()){
			Entry<Rule, LinkedList<Rule[]>> enr=liru.next();
			Rule ru=enr.getKey();
			Subs newSig=ru.saCouv(ex, sig);
			if (newSig !=null){	
				newSig=ru.e.couv(ex.e, newSig);
				if (newSig==null){lx.addAll(spec(ex,ru,enr.getValue(),newSig));}}}
		if(!lx.isEmpty()){contr_ex.add(ex);}
		this.gen(ex);
		ListIterator<Rule> llx=lx.listIterator();
		while(llx.hasNext()){this.gen(llx.next());}
	}
	
	public LinkedList<Rule> spec(Rule ex,Rule ru,LinkedList<Rule[]> lru,Subs sig){
		boolean b;
		LinkedList<Rule> lx=new LinkedList<Rule>();
			do{if(lru.isEmpty()){b=false;}
				else{
				Rule[] ruP=lru.removeFirst();
				b=ruP[0].contr(ex);
				if (b){lx.add(ruP[1]);}else{lru.addFirst(ruP);}
				}}while(b);
	return lx;}
	
	public void gen(Rule ex){
		int b=0;
		Iterator<Entry<Rule, LinkedList<Rule[]>>> liru = this.rules.entrySet().iterator();
		while (liru.hasNext() && b==0){
			Subs sig=new Subs();
			Entry<Rule, LinkedList<Rule[]>> enr=liru.next();
			Rule ru=enr.getKey();
			sig=ru.aeCouv(ex, sig);
			if (sig!=null){
				LinkedList<AtomSet> liAS=ru.s.gen(ex.s, sig);
				ListIterator<AtomSet> itAS=liAS.listIterator();
				while(itAS.hasNext() && b==0){
					AtomSet as=itAS.next();
					ListIterator<Rule> itEx=this.contr_ex.listIterator();
					if(!itEx.hasNext()){b=1;}
					while(itEx.hasNext()){
						Rule exr=itEx.next();
						AtomSet exs=exr.s;
						Subs tempSig=as.couvPart(exs, sig);
						if(tempSig==null){break;}
						if(!itEx.hasNext()){b=1;}}
					
					if(b==1){
						Rule newR=new Rule("R_"+Global.contR,as,ex.a,ex.e);Global.contR++;
							Rule[] tru=new Rule[2];tru[0]=newR;tru[1]=ex;
							LinkedList<Rule[]> lru=this.rules.get(ru);
							lru.addFirst(tru);
							this.rules.put(newR, lru);
							this.rules.remove(ru);}
				}
			}
		}
		if(b==0){
			Rule[] tru=new Rule[2];
			ex.name="R_"+Global.contR;Global.contR++;
			tru[0]=ex;tru[1]=ex;
			LinkedList<Rule[]> lru=new LinkedList<Rule[]>();
			lru.add(tru);
			this.rules.put(ex, lru);}
	}
	
	
}
