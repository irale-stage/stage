import java.util.LinkedList;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//testEnv();
		//testGen();
		//testIrale();
		//testWriteFile();
		testProb();
	}
	
	
public static void testWriteFile(){
	Envi env=new Envi();
	env.etat1();
	Model m=new Model(env.objs);
	
	Term[] tt1=new Term[2];
	tt1[0]=env.objs.get("a");
	tt1[1]=env.objs.get("floor");
	Atom act1=new Atom("move2",tt1);

	Rule x1=env.resolv(act1);
	m.irale(x1);
	
	Term[] tt2=new Term[2];
	tt2[0]=env.objs.get("a");
	tt2[1]=env.objs.get("b");
	Atom act2=new Atom("move2",tt2);
		
	Rule x2=env.resolv(act2);
	m.irale(x2);
	
	Term[] tt3=new Term[2];
	tt3[0]=env.objs.get("a");
	tt3[1]=env.objs.get("floor");
	Atom act3=new Atom("move2",tt3);
	
	Rule x3=env.resolv(act3);
	m.irale(x3);
	
	Term[] tt4=new Term[2];
	tt4[0]=env.objs.get("a");
	tt4[1]=env.objs.get("floor");
	Atom act4=new Atom("move2",tt4);

	Rule x4=env.resolv(act4);
	m.irale(x4);
	
	Term[] tt5=new Term[2];
	tt5[0]=env.objs.get("b");
	tt5[1]=env.objs.get("a");
	Atom act5=new Atom("move2",tt5);

	Rule x5=env.resolv(act5);
	m.irale(x5);
	
	Term A=env.objs.get("c");
	Term B=env.objs.get("b");
	Term Y=env.objs.get("y");
	Term[] ay={A,Y};
	Atom colAY =new Atom("color2",ay);
	Term[] bx={B,Y};
	Atom colBX =new Atom("color2",bx);

	
	AtomSet g_add=new AtomSet("g_add");
	g_add.addAtom(colAY);
	AtomSet g_del=new AtomSet("g_del");
	//g_del.addAtom(colAX);
	Effect goal=new Effect(g_add,g_del);
	env.etat.print();
	m.pl(env.etat,goal);
	
}


public static void testIrale(){
	Envi env=new Envi();
	env.etat1();
	Model m=new Model(env.objs);
	
	Term[] tt1=new Term[2];
	tt1[0]=env.objs.get("a");
	tt1[1]=env.objs.get("floor");
	Atom act1=new Atom("move2",tt1);
	
	System.out.println("Etat Actuel:");
	env.etat.print();
	System.out.println("");	
	Rule x1=env.resolv(act1);
	x1.print();
	System.out.println("");
	System.out.print("Irale:");
	m.irale(x1);
	m.print();
	
	Term[] tt2=new Term[2];
	tt2[0]=env.objs.get("a");
	tt2[1]=env.objs.get("b");
	Atom act2=new Atom("move2",tt2);
	
	System.out.println("Etat Actuel:");
	env.etat.print();
	System.out.println("");	
	Rule x2=env.resolv(act2);
	x2.print();
	System.out.println("");
	System.out.print("Irale:");
	m.irale(x2);
	m.print();
	
	Term[] tt3=new Term[2];
	tt3[0]=env.objs.get("a");
	tt3[1]=env.objs.get("b");
	Atom act3=new Atom("move2",tt3);
	
	System.out.println("Etat Actuel:");
	env.etat.print();
	System.out.println("");	
	Rule x3=env.resolv(act3);
	x3.print();
	System.out.println("");
	System.out.print("Irale:");
	m.irale(x3);
	m.print();
	
	Term[] tt4=new Term[2];
	tt4[0]=env.objs.get("c");
	tt4[1]=env.objs.get("b");
	Atom act4=new Atom("move2",tt4);
	
	System.out.println("Etat Actuel:");
	env.etat.print();
	System.out.println("");	
	Rule x4=env.resolv(act4);
	x4.print();
	System.out.println("");
	System.out.print("Irale:");
	m.irale(x4);
	m.print();
	
	Term[] tt5=new Term[2];
	tt5[0]=env.objs.get("a");
	tt5[1]=env.objs.get("floor");
	Atom act5=new Atom("move2",tt5);
	
	System.out.println("Etat Actuel:");
	env.etat.print();
	System.out.println("");	
	Rule x5=env.resolv(act5);
	x5.print();
	System.out.println("");
	System.out.print("Irale:");
	m.irale(x5);
	m.print();
	
	
	System.out.println("Etat Actuel:");
	env.etat.print();
}

public static void testProb(){
	Envi env=new Envi();
	env.makePb(7,7,2,2,10);
	env.etat.print();
}
	
public static void testEnv(){
	Envi env=new Envi();
	env.etat1();
	Term[] tt=new Term[2];
	tt[0]=env.objs.get("a");
	tt[1]=env.objs.get("floor");
	Atom act=new Atom("move2",tt);
	env.etat.print();
	System.out.println("");	
	env.resolv(act).print();
	System.out.println("");
	env.etat.print();
}
	
	
public static void testGen(){
	Term A=new Term("e1",true);
	Term B=new Term("e2",true);
	Term C=new Term("b1",true);
	Term X=new Term("b2",true);
	Term Y=new Term("b3",true);
	Term Z=new Term("b4",true);
	
	Term[] d={Y};
	Term[] e={Z};
	Atom cubeB3 =new Atom("cube1",d);
	Atom petitB3 =new Atom("petit1",d);
	Atom jauneB4 =new Atom("jaune1",e);
	Atom cubeB4 =new Atom("cube1",e);
	Atom grandB4 =new Atom("grand1",e);
	Atom rayureB3 =new Atom("rayure1",d);
	
	Term[] by={B,Y};
	Atom appartientBY =new Atom("appartient2",by);
	Term[] bz={B,Z};
	Atom appartientBZ =new Atom("appartient2",bz);

	Term[] a={C};
	Atom cubeB1 =new Atom("cube1",a);
	Atom petitB1 =new Atom("petit1",a);
	Atom jauneB1 =new Atom("jaune1",a);
	Term[] b={X};
	Atom cubeB2 =new Atom("cube1",b);
	Atom grandB2 =new Atom("grand1",b);
	Atom rayureB2 =new Atom("rayure1",b);
	Term[] ab={A,C};
	Atom appartientAB =new Atom("appartient2",ab);
	Term[] ac={A,X};
	Atom appartientAC =new Atom("appartient2",ac);
	
	AtomSet pileE2=new AtomSet("pileE2");
	pileE2.addAtom(cubeB3);pileE2.addAtom(petitB3);pileE2.addAtom(jauneB4);
	pileE2.addAtom(cubeB4);pileE2.addAtom(grandB4);pileE2.addAtom(rayureB3);
	pileE2.addAtom(appartientBY);pileE2.addAtom(appartientBZ);
	
	AtomSet pileE1=new AtomSet("pileE1");
	pileE1.addAtom(cubeB1);pileE1.addAtom(petitB1);pileE1.addAtom(jauneB1);
	pileE1.addAtom(cubeB2);pileE1.addAtom(grandB2);pileE1.addAtom(rayureB2);
	pileE1.addAtom(appartientAB);pileE1.addAtom(appartientAC);
	
	Subs sig=new Subs();
	System.out.print("Rule :");pileE1.print();
	System.out.println("");
	System.out.print("Example :");pileE2.print();
	System.out.println("");
	//sig.print();
	//System.out.println("");
	LinkedList<AtomSet> as=pileE1.gen(pileE2, sig);
	AtomSet.llprintS(as);

}
}
