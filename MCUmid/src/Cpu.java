import java.util.Scanner;

public class Cpu {
	private enum EState{
		eStopped,
		eRunnig
	}
	public enum EOperator{
		eHalt,
		eLoada,
		eLoadc,
		eLoadr,
		eStoa,
		eSet,
		eAdda,
		eAddc,
		eAddr,
		eSuba,
		eJump,
		eJumpra,
		eGtj,
		eInput,
		ePrint,
		ePushh,
		ePushs,
		ePop,
		eDiv,
	}
	private Memory memory;
	private KeyBoard keyBoardInterrupt;
	public void associate(Memory memory,KeyBoard keyBoardInterrupt) {
		this.memory = memory;
		this.keyBoardInterrupt = keyBoardInterrupt;
	}	
	private EState eState;
	//register
	private IR ir;
	public Register mar, mbr;
	public Register cs,hs,ss,ds, sr, pc, ac1, hp,sp;
	
	public Cpu() {	
		ir = new IR();
		mar = new Register();
		mbr = new Register();
		cs = new Register();
		hs = new Register();
		ss = new Register();
		ds = new Register();
		sr = new Register();
		pc = new Register();
		ac1 = new Register();
		hp = new Register();
		sp = new Register();
	}
	private void fetch() {
		//System.out.println("Fetch");
		//MAR <- CS +PC
		mar.setValue(cs.getValue()+pc.getValue());
		//MBR = memory.load();
		memory.load();
		//IR = MBR
		ir.setValue(mbr.getValue());
	}
	private void decode() {
		//System.out.println("Decode");
		//System.out.println(ir.getOperator()+" "+ ir.getOperand1());
		//load operand
	}
	private void execute() {
		cs.setValue(0);
		pc.setValue(pc.getValue() + 1);
		switch (ir.getOperator()) {
			case eHalt: {
				this.halt();
				break;
			}
			case eLoada: {
				this.loada();
				break;
			}
			case eLoadc: {
				this.loadc();
				break;
			}
			case eLoadr: {
				this.loadr();
				break;
			}
			case eStoa: {
				this.stoa();
				break;
			}
			case eSet: {
				this.set();
				break;
			}
			case eAdda: {
				this.adda();
				break;
			}
			case eAddc: {
				this.addc();
				break;
			}
			case eSuba: {
				this.suba();
				break;
			}
			case eJump: {
				this.jump();
				break;
			}
			case eJumpra: {
				this.jumpra();
				break;
			}
			case eGtj: {
				this.gtj();
				break;
			}
			case eInput: {
				this.input();
				break;
			}
			case ePrint: {
				this.print();
				break;
			}
			case ePushh: {
				this.pushh();
				break;
			}
			case ePushs: {
				this.pushs();
				break;
			}
			case ePop: {
				this.pop();
				break;
			}
			case eDiv: {
				this.div();
				break;
			}
			case eAddr: {
				this.addr();
				break;
			}
			default:
				break;
		}
	}

	public void halt() {
		this.eState = EState.eStopped;
	}
	public void loada() {
		mar.setValue(sr.getValue()+ir.getOperand1());
		memory.load();
		ac1.setValue(mbr.getValue());
	}
	public void loadc() {
		ac1.setValue(ir.getOperand1());
	}

	/**
	 * 251 ds
	 * 252 hs
	 * 253 ss
	 * 254 hp
	 * 255 sp
	 */
	//loadr에서는 ds,hs,ss segment사용 x
	public void loadr() {
		if (ir.getOperand1() == 254) {
			ac1.setValue(hp.getValue());
		} else if (ir.getOperand1() == 255) {
			ac1.setValue(sp.getValue());
		}
	}
	
	//254 hp
	//255 sp
	public void stoa() {
		//동적할당의 경우
		if (ir.getOperand1() == 254) {
			mar.setValue(sr.getValue()+hp.getValue());
		} else if (ir.getOperand1() == 255) {
			mar.setValue(sr.getValue()+sp.getValue());
		}else {
			//그냥 주소인 경우
			mar.setValue(sr.getValue()+ir.getOperand1());
			int op = ir.getOperand1();
		}
		mbr.setValue(ac1.getValue());
		memory.store();
	}

	//segment 접근 설정
	//251 ds
	//252 hs
	//253 ss
	public void set() {
		if (ir.getOperand1() == 251) {
			sr.setValue(ds.getValue());
		} else if (ir.getOperand1() == 252) {
			sr.setValue(hs.getValue());
		} else if (ir.getOperand1() == 253) {
			sr.setValue(ss.getValue());
		}
	}

	public void adda() {
		mar.setValue(sr.getValue()+ir.getOperand1());
		memory.load();
		ac1.setValue(ac1.getValue()+mbr.getValue());
	}

	public void addc() {
		ac1.setValue(ac1.getValue()+ir.getOperand1());
	}

	public void suba() {
		mar.setValue(sr.getValue()+ir.getOperand1());
		memory.load();
		ac1.setValue(ac1.getValue()-mbr.getValue());
	}

	public void jump() {
		pc.setValue(ir.getOperand1());
	}

	public void jumpra() {
		pc.setValue(ac1.getValue());
	}

	public void gtj() {
		if (ac1.getValue() >= 0) {
			pc.setValue(ir.getOperand1());
		}
	}

	public void input() {
		int input = keyBoardInterrupt.input();
		ac1.setValue(input);
	}
	//251 ds
	//252 hs
	//253 ss
	//254 hp
	//255 sp
	public void addr() {
		if (ir.getOperand1() == 251) {
			ac1.setValue(ac1.getValue()+ds.getValue());
		} else if (ir.getOperand1() == 252) {
			ac1.setValue(ac1.getValue()+hs.getValue());
		} else if (ir.getOperand1() == 253) {
			ac1.setValue(ac1.getValue()+ss.getValue());
		} else if (ir.getOperand1() == 254) {
			ac1.setValue(ac1.getValue()+hp.getValue());
		} else if (ir.getOperand1() == 255) {
			ac1.setValue(ac1.getValue()+sp.getValue());
		}
	}
	public void print() {
		mar.setValue(sr.getValue()+ir.getOperand1());
		memory.load();
		keyBoardInterrupt.output(mbr.getValue());
	}
	public void pushh() {
		hp.setValue(hp.getValue()+4);
	}
	public void pushs() {
		sp.setValue(sp.getValue()+4);
	}
	public void pop() {
		mar.setValue(ss.getValue());
		memory.load();
		sp.setValue(mbr.getValue());
	}

	public void start() {
		this.eState = EState.eRunnig;
		this.run();
	}

	public void div(){
		ac1.setValue(ac1.getValue()/ir.getOperand1());
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
			int operator = value >> 8;
			EOperator eOperator = EOperator.values() [operator-1];
			return eOperator;
		}
		public int getOperand1() {
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
