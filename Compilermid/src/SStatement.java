public class SStatement implements INode{
	private String operator;
	private String operand1;

	public SStatement(String operator) {
		this.operator = operator;
	}
	public SStatement(String operator, String operand1) {
		this.operator = operator;
		this.operand1 = operand1;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperand() {
		return operand1;
	}


	@Override
	public String parse(SLex lex) {
		return operator;
	}

	@Override
	public String toString() {
		String result = "";
		if (operator != null) {
			result += operator;
		}
		if (operand1 != null) {
			result += operand1;
		}
		return result;
	}

	public int getOperandCount() {
		if (this.operand1 != null) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setToHexString(String toHexString) {
		this.operand1 = toHexString;
	}

}