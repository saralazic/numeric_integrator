package integrator;


public class PriorityTable {

	class Info{
		public String operator;
		public int ipr; //input prior
		public int spr; //stack prior
		public int R; //rang elem
		
		public Info(String o, int i, int s, int r){
			operator=o;
			ipr=i;
			spr=s;
			this.R=r;
		}
	}
	
	public Info[] tbl;
	
	public PriorityTable() {
		tbl=new Info[7];
		
		tbl[0]=new Info("+", 2, 2, -1);
		tbl[1]=new Info("-", 2, 2, -1);
		tbl[2]=new Info("*", 3, 3, -1);
		tbl[3]=new Info("/", 3, 3, -1);
		tbl[4]=new Info("^", 5, 4, -1);
		tbl[5]=new Info("(", 6, 0, 0); 
		tbl[6]=new Info(")", 1, 0, 0);//ne ide na stek
		
	}
	
	public int IPR(Elem e) {
		for(int i=0; i<7; i++) {
			if (tbl[i].operator==e.content)
				return tbl[i].ipr;
		}
		return 0;
	}
	
	public int SPR(Elem e) {
		for(int i=0; i<7; i++) {
			if (tbl[i].operator==e.content)
				return tbl[i].spr;
		}
		return 0;
	}
	
	public int R(Elem e) {
		for(int i=0; i<7; i++) {
			if (tbl[i].operator==e.content)
				return tbl[i].R;
		}
		return 0;
	}
}
