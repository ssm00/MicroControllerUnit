
public class Cpu {
	private enum EState{
		eStopped,
		eRunnig
	}
	public enum EOperator{
		eMove,
		eAdd,
		eSub,
		eMul,
		eDiv,
		eJump,
		eGtj,
		eGtjn,
		eLda,
		eSto,
		eHalt,
	}
	private Memory memory;
	public void associate(Memory memory) {
		this.memory = memory;
	}	
	private EState eState;
	//register
	private IR ir;
	public Register mar, mbr;
	public Register cs, pc, ac1, ac2;
	
	public Cpu() {	
		ir = new IR();
		mar = new Register();
		mbr = new Register();
		cs = new Register();
		pc = new Register();
		ac1 = new Register();
		ac2 = new Register();
	}
	private void fetch() {
		//System.out.println("Fetch");
		//ir.setOperator(EOperator.eHalt);
		//MAR <- CS +PC
		cs.setValue(0);
		mar.setValue(cs.getValue()+pc.getValue());
		//MBR = memory.load();
		memory.load();
		//IR = MBR
		ir.setValue(mbr.getValue());
	}
	private void decode() {
		//System.out.println("Decode");
		System.out.println(ir.getOperator()+" "+ ir.getOperand1() +" "+ ir.getOperand2());
		//load operand
	}
	private void execute() {
		cs.setValue(100);
		pc.setValue(pc.getValue() + 1);
		//System.out.println("Excute");
		switch (ir.getOperator()) {
		case eMove: {
			this.move();
			break;
		}
		case eAdd: {
			this.add();
			break;
		}
		case eSub: {
			this.sub();
			break;
		}
		case eMul: {
			this.mul();
			break;
		}
		case eDiv: {
			this.div();
			break;
		}
		case eJump: {
			this.jump();
			break;
		}
		case eGtj: {
			this.gtj();
			break;
		}
		case eGtjn: {
			this.gtjn();
			break;
		}
		case eLda: {
			this.lda();
			break;
		}
		case eSto: {
			this.sto();
			break;
		}
		case eHalt: {
			this.halt();
			break;
		}
		default:
			break;
		}
	}
	public void start() {
		this.eState = EState.eRunnig;
		this.run();
	}
	public void halt() {
		this.eState = EState.eStopped;
		System.out.println("memory.sum : " + memory.getMemory().get(104));
	}
	//254 : ac1, 255 : ac2
	public void move() {
		if (ir.getOperand1() == 254) {
			ac1.setValue(ir.getOperand2());
		}
		else if (ir.getOperand1() == 255) {
			ac2.setValue(ir.getOperand2());
		}
	}
	//add ac1+ac2
	public void add() {
		ac1.setValue(ac1.getValue() + ac2.getValue());
	}
	public void sub() {
		ac1.setValue(ac1.getValue() - ac2.getValue());
	}
	public void mul() {
		ac1.setValue(ac1.getValue() * ac2.getValue());
	}
	public void div() {
		ac1.setValue(ac1.getValue() / ac2.getValue());
	}
	public void jump() {
		pc.setValue(ir.getOperand1());
	}
	public void gtj() {
		if (ac1.getValue() >= 0) {
			pc.setValue(ir.getOperand1());
		}
	}
	public void gtjn() {
		if (ac1.getValue() <= 0) {
			pc.setValue(ir.getOperand1());
		}
	}
	//lda 주소, ac1,2
	public void lda() {
		mar.setValue(cs.getValue()+ir.getOperand1());
		memory.load();
		if(ir.getOperand2() == 254)
			ac1.setValue(mbr.getValue());
		else if(ir.getOperand2() == 255)
			ac2.setValue(mbr.getValue());
	}
	//sto 주소, 값 or register
	public void sto() {
		mar.setValue(cs.getValue()+ir.getOperand1());
		if(ir.getOperand2() == 254)
			mbr.setValue(ac1.getValue());
		else if(ir.getOperand2() == 255)
			mbr.setValue(ac2.getValue());
		else{
			mbr.setValue(ir.getOperand2());
		}
		memory.store();
	}
	public void run() {
		while(this.eState == EState.eRunnig) {
			this.fetch();
			this.decode();
			this.execute();
		}	
	}
	
	private class IR extends Register{
		//자바 10진수 앞에 0x0A000A인 경우 맨앞 0이 빠짐
		public EOperator getOperator() {
			int operator =value >> 16;
			EOperator eOperator = EOperator.values() [operator-1];
			return eOperator;
		}
		public int getOperand1() {
			int operand = (value >> 8) & 0xFF;
			return operand;
		}
		public int getOperand2() {
			int operand = value & 0xFF;
			return operand;
		}
		
	}
	public class Register{
		protected int value;
		public Register() {
			this.value = 0;
		}
		public int getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
	}
}
