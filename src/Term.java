
public class Term {
	public String name;
	public Boolean type; //true si variable, false si constante.
	
	public Term(boolean type){
		if(type){
			this.name="V_"+Global.contV;Global.contV++;
			this.type=type;}
		else{
			this.name="C_"+Global.contC;Global.contC++;
			this.type=type;}
			
	}
	
	public Term(String name,boolean type){
		if(type){
			this.name=name;
			this.type=type;}
		else{
			this.name=name;
			this.type=type;}
			
	}

}
