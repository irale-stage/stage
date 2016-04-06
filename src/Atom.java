
public class Atom {
	public String name;
	public int nbT;
	public Term[] terms;
	
	public Atom(String name,Term[] terms){
		this.name=name;
		this.terms=terms;
	}
	
	public Atom(Atom a) {
		this.name=a.name;
		int i=0,j=a.terms.length;
		this.terms=new Term[j];
		while(i<j){
			this.terms[i]=a.terms[i];
		i++;}
	}

	public Subs gen(Atom r,Subs sig){
		int i=0;
		Term t1,t2; //(c/V)
		Subs newSig=sig;
		//
		//System.out.println();sig.print();
		
		while (i<this.terms.length){
			if( !r.terms[i].type && r.terms[i].name!=this.terms[i].name){return null;}
			t1=this.terms[i];
			t2=sig.find(t1);
			if (t2==null){
				if (sig.revLi.containsKey(r.terms[i])){
					return null;}
				else{newSig.add(t1,r.terms[i]);}}
			else{if (t2!=r.terms[i]){
					return null;}}
			i++;}
		//
	//	r.print();this.print();newSig.print();System.out.println();
		
	return newSig;}
	
	public Atom rev(Subs sig){
		int i=0,j=this.terms.length;
		Term[] tt=new Term[j];
		while (i<j){
			Term t=sig.revLi.get(this.terms[i]);
			if(t==null){tt[i]=this.terms[i];}
			else{tt[i]=t;}
				i++;}
		Atom newAtom=new Atom(this.name,tt);
	return newAtom;}
	
	public boolean egal(Atom a){
		int i=0,j=this.terms.length;
		while(i<j){
			if(this.terms[i]!=a.terms[i]){
				return false;}
			i++;}
		return true;}
	
	
	public void print(){
		System.out.print(" ");
		System.out.print(this.name);
		System.out.print("( ");
		int i=0,j=this.terms.length;
		while(i<j){System.out.print(this.terms[i].name+" ");i++;}
		System.out.print(")");
		System.out.print(" ");}
}
