import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;

public class Envi {

	public LinkedList<Rule> ru;
	public AtomSet etat;
	public HashMap<String,Term> objs;
	
	public Envi(){
		
		objs=new HashMap<String,Term>();
		Term F=new Term("FLOOR",false);
		objs.put(F.name, F);
		
		ru=new LinkedList<Rule>();

		Term A=new Term("A",true);
		Term B=new Term("B",true);
		Term C=new Term("C",true);
		Term X=new Term("X",true);
		Term Y=new Term("Y",true);
		
		Term[] a={A};
		Atom clA =new Atom("clear1",a);
		Term[] b={B};
		Atom clB =new Atom("clear1",b);
		Term[] c={C};
		Atom clC =new Atom("clear1",c);
		Term[] ab={A,B};
		Atom onAB =new Atom("on2",ab);
		Term[] af={A,F};
		Atom onAF =new Atom("on2",af);
		Term[] ac={A,C};
		Atom onAC =new Atom("on2",ac);
		Term[] ax={A,X};
		Atom colAX =new Atom("color2",ax);
		Term[] ay={A,Y};
		Atom colAY =new Atom("color2",ay);
		Term[] bx={B,X};
		Atom colBX =new Atom("color2",bx);
		Term[] by={B,Y};
		Atom colBY =new Atom("color2",by);
		Atom movAB=new Atom("move2",ab);
		Atom movAF=new Atom("move2",af);
		
		AtomSet r1s=new AtomSet("r1s");
		r1s.addAtom(clA);r1s.addAtom(onAB);
		AtomSet r1eDel=new AtomSet("r1eDel");
		AtomSet r1eAdd=new AtomSet("r1eAdd");
		r1eAdd.addAtom(clB);r1eAdd.addAtom(onAF);
		r1eDel.addAtom(onAB);
		Effect r1e=new Effect(r1eAdd,r1eDel);
		Rule r1=new Rule("R1",r1s,movAF,r1e);
		
		AtomSet r2s=new AtomSet("r2s");
		r2s.addAtom(clA);r2s.addAtom(clB);r2s.addAtom(colAX);r2s.addAtom(colBX);r2s.addAtom(onAF);
		AtomSet r2eAdd=new AtomSet("r2eAdd");
		r2eAdd.addAtom(onAB);
		AtomSet r2eDel=new AtomSet("r2eDel");
		r2eDel.addAtom(clB);r2eDel.addAtom(onAF);
		Effect r2e=new Effect(r2eAdd,r2eDel);
		Rule r2=new Rule("R2",r2s,movAB,r2e);
		
		AtomSet r3s=new AtomSet("r3s");
		r3s.addAtom(clA);r3s.addAtom(clB);r3s.addAtom(colAX);r3s.addAtom(colBX);r3s.addAtom(onAC);
		AtomSet r3eAdd=new AtomSet("r3eAdd");
		r3eAdd.addAtom(onAB);r3eAdd.addAtom(clC);
		AtomSet r3eDel=new AtomSet("r3eDel");
		r3eDel.addAtom(clB);r3eDel.addAtom(onAC);
		Effect r3e=new Effect(r3eAdd,r3eDel);
		Rule r3=new Rule("R3",r3s,movAB,r3e);
		
		AtomSet r4s=new AtomSet("r4s");
		r4s.addAtom(clA);r4s.addAtom(clB);r4s.addAtom(colAX);r4s.addAtom(colBY);
		AtomSet r4eAdd=new AtomSet("r4eAdd");
		r4eAdd.addAtom(colAY);
		AtomSet r4eDel=new AtomSet("r4eDel");
		r4eDel.addAtom(colAX);
		Effect r4e=new Effect(r4eAdd,r4eDel);
		Rule r4=new Rule("R4",r4s,movAB,r4e);
	
		this.ru.add(r1);
		this.ru.add(r2);
		this.ru.add(r3);
		this.ru.add(r4);
	}
	
	public Rule resolv(Atom a){
		AtomSet add=new AtomSet("add");
		AtomSet del=new AtomSet("del");
		Effect e=new Effect(add,del);
		AtomSet s=new AtomSet(this.etat);
		Rule ex=new Rule("Action_env_null",s,a,e);
		Iterator<Rule> itR=ru.iterator();
		Subs sig=new Subs();
		Subs newSig=new Subs();
		Rule r=null;
		while (itR.hasNext()){
			r=itR.next();
			newSig=r.saCouv(ex, sig);
			if(newSig!=null){
				ex.e=r.e.rev(newSig);ex.name="Action_env_"+r.name;break;}}
		maj(ex.e);
	return ex;}
	
	public void maj(Effect e){
		AtomSet add=e.add;
		AtomSet del=e.del;
		Iterator<Entry<String, LinkedList<Atom>>> deL=del.atoms.entrySet().iterator();
		Entry<String, LinkedList<Atom>> nomAl;
		while(deL.hasNext()){
			nomAl=deL.next();
			ListIterator<Atom> deLT =nomAl.getValue().listIterator();
			while(deLT.hasNext()){
				Atom deA=deLT.next();
				LinkedList<Atom> atomsEta=this.etat.atoms.get(nomAl.getKey());
				ListIterator<Atom> eta=atomsEta.listIterator();
				while(eta.hasNext()){
					Atom potD=eta.next();
					if (deA.egal(potD)){
						atomsEta.remove(potD);
						break;}}
			}
		}
		
		Iterator<Entry<String, LinkedList<Atom>>> adL=add.atoms.entrySet().iterator();
		while(adL.hasNext()){
			nomAl=adL.next();
			ListIterator<Atom> adLT =nomAl.getValue().listIterator();
			while(adLT.hasNext()){
				this.etat.addAtom(adLT.next());
			}
		}
		
	}
	
	public void etat1(){
		Term A=new Term("A",false);
		Term B=new Term("B",false);
		Term C=new Term("C",false);
		Term X=new Term("X",false);
		Term Y=new Term("Y",false);
		Term Z=new Term("Z",false);
		Term F=objs.get("FLOOR");
		
		objs.put(A.name, A);
		objs.put(B.name, B);
		objs.put(X.name, X);
		objs.put(Y.name, Y);
		objs.put(Z.name, Z);
		objs.put(C.name, C);
		
		
		Term[] a={A};
		Atom clA =new Atom("clear1",a);
		Term[] c={C};
		Atom clC =new Atom("clear1",c);
		Term[] ab={A,B};
		Atom onAB =new Atom("on2",ab);
		Term[] bf={B,F};
		Atom onBF=new Atom("on2",bf);
		Term[] cf={C,F};
		Atom onCF=new Atom("on2",cf);
		Term[] ax={A,X};
		Atom colAX =new Atom("color2",ax);
		Term[] by={B,Y};
		Atom colBY =new Atom("color2",by);
		Term[] cz={C,Z};
		Atom colCZ =new Atom("color2",cz);
		
		etat=new AtomSet("r1s");
		etat.addAtom(clA);etat.addAtom(onAB);etat.addAtom(colBY);etat.addAtom(colAX);etat.addAtom(onBF);
		etat.addAtom(clC);etat.addAtom(onCF);etat.addAtom(colCZ);
	}
	
	
	public AtomSet makeRandAS(int nbOMin,int nbOMax,int nbCMin,int nbCMax){
		int nbO=Global.makeRandInt(nbOMin,nbOMax),nbC=Global.makeRandInt(nbCMin,nbCMax);
		HashMap<Integer, Term> pos=new HashMap<Integer,Term>();
		HashMap<Integer, Term> col=new HashMap<Integer,Term>();
		LinkedList<Term> ter=new LinkedList<Term>();
		AtomSet at=new AtomSet();
		int i=0;
		Term f=objs.get("FLOOR");
		while(i<nbO){
			Term t=new Term(false);
			objs.put(t.name, t);
			ter.add(t);
			int temp=Global.makeRandInt(0,pos.size());
			if(temp==0){
				Term[] tt={t,f};
				at.addAtom(new Atom("on2",tt));
				pos.put(pos.size(),t);
			}else{
				Term oldT=pos.get(temp);
				Term[] tt={t,oldT};
				at.addAtom(new Atom("on2",tt));
				pos.put(temp,t);}i++;}
		i=0;
		while(i<pos.size()){
			Term[] tt={pos.get(i)};
			at.addAtom(new Atom("clear1",tt));
		i++;}
		ListIterator<Term> li=ter.listIterator();
		while(li.hasNext()){
			Term t=li.next();
			int temp=Global.makeRandInt(0,nbC);
			Term c;
			if (col.containsKey(temp)){
				c=col.get(temp);
			}else{
				c=new Term(false);
				objs.put(c.name, c);
				col.put(temp, c);}
			Term[] tt={t,c};
			at.addAtom(new Atom("color2",tt));
		}
	return at;}
	
	public Effect makePb(int nbOMin,int nbOMax,int nbCMin,int nbCMax,int nbIte){
		this.etat=this.makeRandAS(nbOMin, nbOMax, nbCMin, nbCMax);

		AtomSet init=new AtomSet(this.etat);
		int i=0,j=nbIte-1;
		while(i<j){
			this.resolv(selectAction(this.etat));
			i++;}
		Effect e=this.resolv(selectAction(this.etat)).e;
		Iterator<Entry<String, LinkedList<Atom>>> it=e.add.atoms.entrySet().iterator();
		AtomSet goalAdd=new AtomSet(this.etat);
		AtomSet goalDel=new AtomSet(e.del);
		Effect goal=new Effect(goalAdd,goalDel);
		goalDel.atoms.remove("clear1");		
		int posAt=Global.makeRandInt(1,goalAdd.atoms.size()+goalAdd.atoms.size());
		i=0;
		AtomSet atomGoal=goalAdd;
		this.etat=init;
		
		ModPlanner pl=new ModPlanner(objs);
		pl.usePlan(this.ru, this.etat, goal);
		System.out.println();
		init.print();
		return goal;}
	
	public Atom selectAction(AtomSet etat){
		LinkedList<Atom> lla=this.etat.atoms.get("clear1");
		ListIterator<Atom> lia=lla.listIterator();
		int i=0,id1=Global.makeRandInt(0, lla.size()),id2=Global.makeRandInt(0, lla.size());
		Term t1=null,t2=null;
		while(t1 == null || t2 ==null){
			Term t=lia.next().terms[0];
			if(i==id1){t1=t;}
			if(i==id2){t2=t;}
			i++;}
		if(id1==id2){t2=objs.get("FLOOR");}
		Term[] tt={t1,t2};
		Atom action=new Atom("move2",tt);
return action;}
	
	
	
}
