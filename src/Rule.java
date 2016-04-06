
public class Rule {
	public String name;
	public AtomSet s;
	public Atom a;
	public Effect e;
	
public Rule(String name,AtomSet s,Atom a,Effect e){
	this.name =name;
	this.s=s;
	this.a=a;
	this.e=e;
	}


public Subs aCouv(Rule ex,Subs sig){
	sig=ex.a.gen(this.a, sig);
return sig;}

public boolean contr(Rule ex){
	Subs sig=new Subs();
	sig=this.saCouv(ex,sig);
	if (sig==null){return false;}
	sig=this.e.couv(ex.e, sig);
	if(sig==null){return true;}
return false;}

public Subs aeCouv(Rule ex,Subs sig){
	Subs newSig=this.aCouv(ex,sig);
	if (newSig!=null){
		newSig=this.e.couv(ex.e,newSig);}
	
return newSig;}

public Subs sCouv(Rule ex,Subs sig){
	Subs newSig=this.s.couvPart(ex.s, sig);
return newSig;}

public Subs saCouv(Rule ex,Subs sig){
	Subs newSig=aCouv(ex,sig);
	Subs neSig;
	if(newSig==null){return null;}
	else{neSig=sCouv(ex,newSig);}
return neSig;}


public void print(){
	System.out.println();
	System.out.print(this.name+": ");
	this.s.print();
	System.out.print("/");
	this.a.print();
	System.out.println("/");
	this.e.print();
	System.out.println();}

}
