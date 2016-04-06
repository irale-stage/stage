import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.TimeoutException;



public class ModPlanner extends Thread{
	public HashMap<Rule,LinkedList<Rule[]>> rules;
	public HashMap<String,Term> objs;
	public HashMap<String,Term[]> varPara;
	public HashSet<String> vCons=new HashSet<String>();
	public AtomSet init;
	public String fileNameDom,fileNameProb,fileNameSol,domain,problem;
	
	public ModPlanner(HashMap<String,Term> objs){
		this.objs=objs;
	}
	
	public LinkedList<RuSub> usePlan(HashMap<Rule, LinkedList<Rule[]>> rules,AtomSet init,Effect goal) {
		this.writeFile(rules, init, goal);
		boolean b=false;
		try {
				b=TimeoutController.execute(this, 2000,fileNameSol);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!b){return null;}
		LinkedList<RuSub> plan=new LinkedList<RuSub>();
		readSol(plan);
	return plan;}
	
	public LinkedList<RuSub> usePlan(LinkedList<Rule> rules,AtomSet init,Effect goal) {
		this.writeFile(this.toHashMap(rules), init, goal);
		boolean b=false;
			try {
				b=TimeoutController.execute(this, 2000,fileNameSol);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!b){return null;}
			LinkedList<RuSub> plan=new LinkedList<RuSub>();
			readSol(plan);
	return plan;}
	
	public void readSol(LinkedList<RuSub> plan){
		try (BufferedReader br =new BufferedReader(new FileReader(fileNameSol))){
			String line;
			while((line=br.readLine())!=null){
				if(line.substring(0, 1).equals(";")){continue;}
				System.out.println();
				System.out.println(line);
				line=line.substring(4, line.length()-5);
				String[] parts=line.split(" ");
				RuSub nRus=new RuSub();
				Subs sig=new Subs();
				nRus.name=parts[0];
				int i=1,j=parts.length;
				Term[] itVar=this.varPara.get(parts[0]);
				while(i<j){
					sig.add(this.objs.get(parts[i]),itVar[i-1]);
					System.out.println(itVar[i-1].name);
				i++;}
				nRus.subs=sig;
				sig.print();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}

	
	
	public void run(){
		
		try {
			Runtime.getRuntime().exec("/home/lvlarvin/bin/satplan -domain "+fileNameDom+" -problem "+fileNameProb+" -solution "+fileNameSol);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<Rule, LinkedList<Rule[]>> toHashMap(LinkedList<Rule>ru){
		this.rules=new HashMap<Rule,LinkedList<Rule[]>>();
		ListIterator<Rule> li=ru.listIterator();
		while(li.hasNext()){
			this.rules.put(li.next(), null);
		}
	return this.rules;}
	
	public void writeFile(HashMap<Rule, LinkedList<Rule[]>> rules,AtomSet init,Effect goal){
		this.rules=rules;
		this.init=init;
		this.varPara=new HashMap<String,Term[]>();
		fileNameProb="irale-"+Global.contP+"-prob.pddl";
		fileNameDom="irale-"+Global.contP+"-domain.pddl";
		fileNameSol="irale-"+Global.contP+"-sol.ppdl";
		domain="color-blocksworld-"+Global.contP;
		problem=domain+"-prob";
		Global.contP++;
		defDomF();
		defProbF(goal);
		}
	
	public void findPara(Rule r,HashMap<String,Term> para){
		Iterator<Entry<String, LinkedList<Atom>>> itS=r.s.atoms.entrySet().iterator();
		while(itS.hasNext()){
			Entry<String, LinkedList<Atom>>eR=itS.next();
			LinkedList<Atom> la=eR.getValue();
			ListIterator<Atom> ia=la.listIterator();
			Atom a=null;
			while(ia.hasNext()){
				a=ia.next();
				int i=0,j=a.terms.length;
				while(i<j){
					para.put(a.terms[i].name,a.terms[i]);
					if (!a.terms[i].type){this.vCons.add(a.terms[i].name);}
					i++;}}}
		
		itS=r.e.add.atoms.entrySet().iterator();
		while(itS.hasNext()){
			Entry<String, LinkedList<Atom>>eR=itS.next();
			LinkedList<Atom> la=eR.getValue();
			ListIterator<Atom> ia=la.listIterator();
			Atom a=null;
			while(ia.hasNext()){
				a=ia.next();
				int i=0,j=a.terms.length;
				while(i<j){para.put(a.terms[i].name,a.terms[i]);i++;}}}
		
	}
	
	public void findPred(HashMap<String,Atom> pred){
		Iterator<Entry<Rule,LinkedList<Rule[]>>> itR =this.rules.entrySet().iterator();
		while (itR.hasNext()){
			Rule r=itR.next().getKey();
			Iterator<Entry<String, LinkedList<Atom>>> itS=r.s.atoms.entrySet().iterator();
			while(itS.hasNext()){
				Entry<String, LinkedList<Atom>>eR=itS.next();
				LinkedList<Atom> la=eR.getValue();
				ListIterator<Atom> ia=la.listIterator();
				Atom a=null;
				if(ia.hasNext()){
					a=ia.next();}
				pred.put(a.name,a);}
			
			itS=r.e.add.atoms.entrySet().iterator();
			while(itS.hasNext()){
				Entry<String, LinkedList<Atom>>eR=itS.next();
				LinkedList<Atom> la=eR.getValue();
				ListIterator<Atom> ia=la.listIterator();
				Atom a=null;
				if(ia.hasNext()){
					a=ia.next();}
				pred.put(a.name,a);}
			
		}
	}
	
	public void writeRule(PrintWriter w,Rule r,HashMap<String,Atom> pred,HashMap<String,Term> para){
		para=new HashMap<String, Term>();
		this.findPara(r, para);
		w.println("	(:action "+r.name);
		Term[] ts=new Term[para.size()];
		varPara.put(r.name, ts);
		w.print("		:parameters (");
		Iterator<Entry<String, Term>> itP=para.entrySet().iterator();
		int i=0;
		while(itP.hasNext()){
			Entry<String,Term> ent=itP.next();
			if(ent.getValue().type){
				w.print(" ?"+ent.getKey()+" - var ");
			}else{w.print(" ?"+ent.getKey()+" - c"+ent.getKey());}
			ts[i]=ent.getValue();
			i++;}
		w.println(")");

		w.print("		:precondition (and ");
		Iterator<Entry<String, LinkedList<Atom>>> itS=r.s.atoms.entrySet().iterator();
		while(itS.hasNext()){
			Entry<String, LinkedList<Atom>>eR=itS.next();
			LinkedList<Atom> la=eR.getValue();
			ListIterator<Atom> ia=la.listIterator();
			Atom a=null;
			while(ia.hasNext()){
				a=ia.next();
				w.print("("+a.name+" ");
				i=0;
				int j=a.terms.length;
				while(i<j){
						w.print("?"+a.terms[i].name+" ");
					i++;}
				w.print(")");
				}}
		w.println(")");
		
		w.print("		:effect (and");
		itS=r.e.add.atoms.entrySet().iterator();
		while(itS.hasNext()){
			Entry<String, LinkedList<Atom>>eR=itS.next();
			LinkedList<Atom> la=eR.getValue();
			ListIterator<Atom> ia=la.listIterator();
			Atom a=null;
			while(ia.hasNext()){
				a=ia.next();
				w.print("("+a.name+" ");
				int j=a.terms.length;i=0;
				while(i<j){
					 	w.print("?"+a.terms[i].name+" ");
					i++;}w.print(")");}}
					
		
		itS=r.e.del.atoms.entrySet().iterator();
		while(itS.hasNext()){
			Entry<String, LinkedList<Atom>>eR=itS.next();
			LinkedList<Atom> la=eR.getValue();
			ListIterator<Atom> ia=la.listIterator();
			while(ia.hasNext()){
				Atom a=ia.next();
				w.print("( not("+a.name+" ");
				int j=a.terms.length;i=0;
				while(i<j){
						w.print("?"+a.terms[i].name+" ");
				i++;}w.print(")) ");}
			}
		w.println("))");
	}
	
	public void defProbF(Effect goal){
		try {
			PrintWriter w =new PrintWriter(fileNameProb, "UTF-8");
			w.println(";; "+fileNameProb);
			w.println("(define (problem "+problem+")");
			w.println("	(:domain "+domain+")");
			
			w.print("	(:objects ");
			Iterator<Entry<String,Term>> itO=objs.entrySet().iterator();
			
			while(itO.hasNext()){
				Term t=itO.next().getValue();
				if (this.vCons.contains(t.name)){
					w.write(""+t.name+" - c"+t.name+" ");}}
			itO=objs.entrySet().iterator();
			while(itO.hasNext()){
				Term t=itO.next().getValue();
				if (!this.vCons.contains(t.name)){
					w.write(""+t.name+" ");}}
				w.write("- var");
				
			w.println(")");
			
			w.print("	(:init ");
			Iterator<Entry<String, LinkedList<Atom>>> itA=init.atoms.entrySet().iterator();
			while(itA.hasNext()){
				ListIterator<Atom> liA=itA.next().getValue().listIterator();
				while(liA.hasNext()){
					Atom a=liA.next();
					w.print("("+a.name+" ");
					Term[] ts=a.terms;
					int i=0,j=ts.length;
					while(i<j){
						w.write(ts[i].name+" ");
					i++;}
					w.print(")");}}
				
				
			w.println(")");
			
			
			w.print("	(:goal");
			Iterator<Entry<String, LinkedList<Atom>>> itS=goal.add.atoms.entrySet().iterator();
			if (itS.hasNext()){w.print(" (and ");}
			while(itS.hasNext()){
				Entry<String, LinkedList<Atom>>eR=itS.next();
				LinkedList<Atom> la=eR.getValue();
				ListIterator<Atom> ia=la.listIterator();
				Atom a=null;
				while(ia.hasNext()){
					a=ia.next();
					w.print("("+a.name+" ");
					int i=0,j=a.terms.length;
					while(i<j){w.print(a.terms[i].name+" ");i++;}w.print(")");}}	
			itS=goal.del.atoms.entrySet().iterator();
			w.println(")))");
			
			w.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	}
	
	public void defDomF()  {
		HashMap<String,Atom> pred=new HashMap<String,Atom>();
		HashMap<String,Term> para=new HashMap<String, Term>();
		Iterator<Entry<Rule,LinkedList<Rule[]>>> itR =this.rules.entrySet().iterator();
		while (itR.hasNext()){
			Rule r=itR.next().getKey();
			if(!r.e.add.atoms.isEmpty()||!r.e.del.atoms.isEmpty()){
				this.findPara(r, para);}}
		
		try {
			PrintWriter w =new PrintWriter(fileNameDom, "UTF-8");
			w.println(";; "+fileNameDom);
			w.println();
			w.println("(define (domain "+domain+")");
			w.println("	(:requirements :adl)");
			
			w.println("	(:types var");
			Iterator<Entry<String, Term>> it=para.entrySet().iterator();
			while(it.hasNext()){
				Term t=it.next().getValue();
				if(!t.type){
				w.println("	 c"+t.name);}
			}
			w.print("	)");
			w.println();
			
			w.print("	(:predicates ");
			this.findPred(pred);
			Iterator<Entry<String, Atom>> la=pred.entrySet().iterator();
			while(la.hasNext()){
				Atom a=la.next().getValue();
				w.print("("+a.name+" ");
				int i=0,j=a.terms.length;
				while(i<j){w.print("?"+a.terms[i].name+" ");i++;}w.print(")");}w.print(")");

			itR =this.rules.entrySet().iterator();
			while (itR.hasNext()){
				Rule r=itR.next().getKey();
				if(!r.e.add.atoms.isEmpty()||!r.e.del.atoms.isEmpty())
				{
					w.println();
					this.writeRule(w,r,pred,para);}}
			w.print(")");
			w.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}}



	
}
