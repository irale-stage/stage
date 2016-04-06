
public class Effect {
	public AtomSet add;
	public AtomSet del;
	
	public Subs couv(Effect ex,Subs sig){
		Subs newSig=sig;
		newSig=this.add.couvParf(ex.add, newSig);
		if (newSig!=null){
			newSig=this.del.couvParf(ex.del, newSig);}
	return newSig;		
}
	
	public Effect rev(Subs sig){
		AtomSet newAdd=add.rev(sig);
		AtomSet newDel=del.rev(sig);
		Effect ex=new Effect(newAdd,newDel);
	return ex;}
	
	public Effect(AtomSet add,AtomSet del){
		this.add=add;
		this.del=del;
	}

	public void print(){
		System.out.print("Add[ ");
		this.add.print();
		System.out.print(" ]; ");
		System.out.print("Del[ ");
		this.del.print();
		System.out.print(" ]");
	}
}
