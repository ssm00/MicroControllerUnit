import java.util.Scanner;
import java.util.Vector;

public class Memory {
	private Vector<Integer> memory;
	
	private Cpu.Register MAR;
	private Cpu.Register MBR;
	public Memory() {
		this.memory = new Vector<Integer>();
		for(int i = 0; i < 500; i++) {
			this.memory.add(-1);
		}
	}
	public Vector<Integer> getMemory() {
		return memory;
	}
	public void associate(Cpu.Register MAR, Cpu.Register MBR) {
		this.MAR = MAR;
		this.MBR = MBR;
	}
	public void load() {
		int address = MAR.getValue();
		MBR.setValue(this.memory.get(address));
	}
	public void store() {
		int address = MAR.getValue();
		int value = MBR.getValue();
		this.memory.set(address, value);
	}
}
