public class SStatement implements INode{
	private String operator;
	private String operand1;
	private String operand2;

	public SStatement(String operator) {
		this.operator = operator;
	}
	public SStatement(String operator, String operand1) {
		this.operator = operator;
		this.operand1 = operand1;
	}
	public SStatement(String operator, String operand1, String operand2) {
		this.operator = operator;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperand(int i) {
		if (i == 1) {
			return operand1;
		} else if (i == 2) {
			return operand2;
		} else {
			return null;
		}
	}

	public int getOperandCount() {
		if (operand1 != null && operand2 != null) {
			return 2;
		} else if (operand1 != null) {
			return 1;
		} else {
			return 0;
		}
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
		if (operand2 != null) {
			result += operand2;
		}
		return result;
	}


	public void setToHexString(int i, String toHexString) {
		if (i == 1) {
			this.operand1 = toHexString;
		} else if (i == 2) {
			this.operand2 = toHexString;
		}
	}


}