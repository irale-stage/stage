import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;


public class AtomSet {
	public String name;
	public HashMap<String,LinkedList<Atom>> atoms=new HashMap<String,LinkedList<Atom>>();
	
	
	public AtomSet(String name){
		this.name=name;
	}
	
	public AtomSet(){

	}
	
	public AtomSet(AtomSet s){
		Iterator<Entry<String, LinkedList<Atom>>> ita=s.atoms.entrySet().iterator();
		this.name=s.name;
		while(ita.hasNext()){
			Entry<String, LinkedList<Atom>> eas=ita.next();
			ListIterator<Atom> lia=eas.getValue().listIterator();
			while(lia.hasNext()){
				Atom a=new Atom(lia.next());
				this.addAtom(a);
			}
		}
	}
	
	public AtomSet rev(Subs sig){
		Iterator<Entry<String, LinkedList<Atom>>> itR=this.atoms.entrySet().iterator();
		AtomSet newAs=new AtomSet(this.name);
		while(itR.hasNext()){
			ListIterator<Atom> aRL=itR.next().getValue().listIterator();
			while(aRL.hasNext()){
				Atom newAEx=aRL.next().rev(sig);
				newAs.addAtom(newAEx);}}
		return newAs;}
	
	
	public void addAtom(Atom a){
		LinkedList<Atom> as;
		if(atoms.containsKey(a.name)){
			as=atoms.get(a.name);
			as.add(a);}
		else{
			as= new LinkedList<Atom>();
			as.add(a);}
		atoms.put(a.name, as);
	}

	public Subs couvParf(AtomSet ex,Subs sig){
	Subs newSig=sig;
	if ((this.atoms.isEmpty() && !ex.atoms.isEmpty())||(!this.atoms.isEmpty() && ex.atoms.isEmpty())){return null;}
	Iterator<Entry<String, LinkedList<Atom>>> itL=ex.atoms.entrySet().iterator();
	while (itL.hasNext()){
		Entry<String, LinkedList<Atom>> nomAl=itL.next();
			LinkedList<Atom> aEx=ex.atoms.get(nomAl.getKey());
			LinkedList<Atom> aR=this.atoms.get(nomAl.getKey());	
			if (aEx==null || aR==null){return null;}
			int nbA=aEx.size();
			if(nbA!=aR.size()){return null;}
			LinkedList<Atom> aRc=(LinkedList<Atom>) aR.clone();
			LinkedList<Atom> aExc=(LinkedList<Atom>) aEx.clone();
			Subs tempSig=couvRecParf(aExc,aRc,newSig,nbA,0);
			if(tempSig==null){return null;}
			else{newSig=tempSig;}}
	return newSig;}
	
	private LinkedList<Atom> concat(LinkedList<Atom> l1,LinkedList<Atom> l2){
		while(!l2.isEmpty()){
			l1.addLast(l2.removeFirst());}
	return l1;}
	
	private LinkedList<Atom> concat2(ListIterator<Atom> l1,LinkedList<Atom> l2){
		while(l1.hasNext()){
			l2.addFirst(l1.next());}
	return l2;}
	
	//regle complement couverte par l'exemple.
	public Subs couvPart(AtomSet ex,Subs sig){
		Subs newSig=sig;
		Iterator<Entry<String, LinkedList<Atom>>> itL=ex.atoms.entrySet().iterator();
		while (itL.hasNext()){
				Entry<String, LinkedList<Atom>> nomAl=itL.next();
				LinkedList<Atom> aEx=nomAl.getValue();
				LinkedList<Atom> aR=this.atoms.get(nomAl.getKey());
				if(aR!=null){
					int nbA=aR.size();
					LinkedList<Atom> aRc=(LinkedList<Atom>) aR.clone();
					LinkedList<Atom> aExc=(LinkedList<Atom>) aEx.clone();
					Subs tempSig=couvRecPart(aRc,aExc,newSig,nbA,0);
					if(tempSig==null){return null;}
					else{newSig=tempSig;}}}
		return newSig;}
	

//s'arrete a la premiere converture parfaite des atoms de même nom.
	public Subs couvRecParf(LinkedList<Atom> liE,LinkedList<Atom> liR,Subs sig,int nbA,int cont){
		if (cont==nbA){return sig;}
		Subs newSig=sig;
		Atom aE=liE.removeFirst();
		LinkedList<Atom> tempLi=new LinkedList<Atom>();
		while (!liR.isEmpty()){
			Atom aR=liR.removeFirst();
			Subs tempSig=aE.gen(aR, newSig);
			if (tempSig==null){
				tempLi.add(aR);}
			else{cont++;
				tempSig=couvRecParf(liE,concat(liR,tempLi),tempSig,nbA,cont);
				if(tempSig!=null){
					return tempSig;}}}
	return null;}

//couverture partiel de l'example.
	public Subs couvRecPart(LinkedList<Atom> liR,LinkedList<Atom> liE,Subs sig,int nbA,int cont){
		if (cont==nbA){return sig;}
		Subs newSig=sig;
		Atom aR=liR.removeFirst();
		LinkedList<Atom> tempLi=new LinkedList<Atom>();
		while (!liE.isEmpty()){
			Atom aE=liE.removeFirst();
			Subs tempSig=aE.gen(aR, newSig);
			if (tempSig==null){
				tempLi.add(aE);}
			else{cont++;
				tempSig=couvRecPart(liR,concat(liE,tempLi),tempSig,nbA,cont);
				if(tempSig!=null){
					return tempSig;}}}
	return null;}
	
	public void genRec(LinkedList<Atom> lR,LinkedList<Atom> lE,Subs sig){
		Subs newSig=sig;
		if (lR.isEmpty()){Global.susuAdd(newSig);}
		else{
			Atom aR=lR.removeFirst();
			LinkedList<Atom> tempLi=new LinkedList<Atom>();
			while (!lE.isEmpty()){
				Atom aE=lE.removeFirst();
				Subs tempSig=aE.gen(aR, newSig.copie());
				if (tempSig!=null){
					Global.susuAdd(tempSig);
					genRec((LinkedList<Atom>)lR.clone(),concat((LinkedList<Atom>)lE.clone(),tempLi),tempSig);}
				tempLi.add(aE);
				genRec((LinkedList<Atom>)lR.clone(),concat((LinkedList<Atom>)lE.clone(),tempLi),newSig);
				}
			}
	}
	
	public LinkedList<Subs> genSup(AtomSet ex,Subs sig){
		Iterator<Entry<String, LinkedList<Atom>>> itL=this.atoms.entrySet().iterator();
		Global.susuAdd(sig);
		LinkedList<Subs> subPoss=new LinkedList<Subs>();;
		while (itL.hasNext()){
			Entry<String,LinkedList<Atom>> etSet=itL.next();
			subPoss.addAll(Global.susu);
			ListIterator<Subs> itS =subPoss.listIterator();
			while(itS.hasNext()){	
				Subs s=itS.next();
				LinkedList<Atom> RR=etSet.getValue();
				LinkedList<Atom> itEx=ex.atoms.get(etSet.getKey());
				genRec((LinkedList<Atom>)RR.clone(),(LinkedList<Atom>)itEx.clone(),s);
			}
		}
	subPoss.addAll(Global.susu);
	return reduc(subPoss);}
	
	public LinkedList<AtomSet> gen(AtomSet ex,Subs sig){
		LinkedList<Subs> liSup= this.genSup(ex,sig);
		LinkedList<AtomSet> res = new LinkedList<AtomSet>(); int i=0;
		ListIterator<Subs> sl=liSup.listIterator();
		while(sl.hasNext()){
			AtomSet newAT=new AtomSet("gen"+i);i++;
			Subs s=sl.next();
			Iterator<Entry<String, LinkedList<Atom>>> r=this.atoms.entrySet().iterator();
			while(r.hasNext()){
				Entry<String, LinkedList<Atom>> enr=r.next();
				ListIterator<Atom> ael = ex.atoms.get(enr.getKey()).listIterator();
				while(ael.hasNext()){
					Atom ae=ael.next();
					ListIterator<Atom> arl = enr.getValue().listIterator();
					while(arl.hasNext()){
						Atom ar=arl.next();
						Subs sb=ae.gen(ar,s);
						if (sb!=null){
							int a=0,j=ae.terms.length;
							Term[] tl=new Term[j];
							while (a<j){
								Term t=new Term(true);
								t.name=ar.terms[a].name;
								tl[a]=t;//ar.terms[a];
								a++;}
							Atom at=new Atom(ar.name,tl);
							newAT.addAtom(at);
						break;}}
					}
				}
				res.add(newAT);
			}
		
	return res;}
		
	public LinkedList<Subs> reduc(LinkedList<Subs> subPoss){
		LinkedList<Subs> res=new LinkedList<Subs>();
		ListIterator<Subs> l=subPoss.listIterator();
		Subs s,s2;
		while (l.hasNext()){
			s=l.next();
			LinkedList<Subs> lres=new LinkedList<Subs>();
			lres.addAll(res);
			res=new LinkedList<Subs>();
			boolean b=true;
				while(!lres.isEmpty()){
					s2=lres.removeFirst();
					int r=s.couv(s2);
					if(r==0){res.add(s2);}
					if(r==2 || r==1){res.add(s2);b=false;}
				}
				if(b){res.add(s);}
			}
	return res;}
		
	
	public void print(){
		Iterator<Entry<String, LinkedList<Atom>>> itR=this.atoms.entrySet().iterator();
		while(itR.hasNext()){
			Entry<String, LinkedList<Atom>> ent=itR.next();
			ListIterator<Atom> liR=ent.getValue().listIterator();
			while (liR.hasNext()){
				liR.next().print();}}
	}
	
	public static void llprint(LinkedList<Atom> s){
		ListIterator<Atom> ss=s.listIterator();
		System.out.println();
		System.out.println("Atoms:");
		while (ss.hasNext()){
			ss.next().print();
			
		}
		System.out.println();
	}
	
	public static void llprintS(LinkedList<AtomSet> s){
		ListIterator<AtomSet> ss=s.listIterator();
		System.out.println();
		System.out.println("Set:");
		while (ss.hasNext()){
			ss.next().print();
			System.out.println();
			
		}
		System.out.println();
	}
}
