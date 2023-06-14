import java.util.Vector;


public class SCodeSegment implements INode {
	private Vector<SStatement> statements;
	private SSymbolTable symbolTable;

	public SCodeSegment(SSymbolTable symbolTable) {
		this.statements = new Vector<SStatement>();
		this.symbolTable = symbolTable;
	}

	/**
	 * codeSegment 1단계 파싱
	 * 공백 및 주석 삭제, codeSegment Label 인식
	 */
	@Override
	public String parse(SLex lex) {
		String[] tokens = lex.getTokens();
		String operator = tokens[0];
		while (operator.compareTo(".end") != 0) {
			if ((operator.startsWith("//")) || (operator.length() == 0)) {
				continue;
			} else if (operator.contains(":")) {
				//symboltable
				SSymbolEntity entity = new SSymbolEntity();
				entity.setVariableName(operator.replace(":", ""));
				entity.setValue(this.statements.size());
				this.symbolTable.add(entity);
			} else {
				SStatement statement = null;
				switch (tokens.length) {
					case 1:
						statement = new SStatement(tokens[0]);
						break;
					case 2:
						statement = new SStatement(tokens[0], tokens[1]);
						break;
					default:
						break;
				}
				this.statements.add(statement);
			}

			tokens = lex.getTokens();
			operator = tokens[0];
		}
		return operator;
	}

	/**
	 * 16진수 기계어 번역을 위한 2단계 파싱
	 * 주소값, 레지스터값, Label을 16진수로 변경
	 * 주소 -> symbolTable의 주소로 2자리 16진수로 변경
	 * 레지스터들은 0xFF부터 하나씩 작은 숫자로정의.
	 * Label의 경우 line number로 변경
	 */
	public void secondPhaseParse() {
		for (SStatement statement : this.statements) {
			String operator = statement.getOperator();
			String operatorCode = getOperatorCode(operator);
			statement.setOperator(operatorCode);

			//operand가 있는 경우
			if (statement.getOperandCount() == 1) {
				String operand = statement.getOperand();
				//주소
				if (operand.equals("loada") || operand.equals("stoa")|| operand.equals("adda")|| operand.equals("suba")|| operand.equals("print")) {
					SSymbolEntity symbol = this.symbolTable.get(operand);
					statement.setToHexString(String.format("%02X", symbol.getValue()));
				}
				//라벨
				else if (symbolTable.labelCheck(operand)) {
					SSymbolEntity symbol = this.symbolTable.get(operand);
					statement.setToHexString(String.format("%02X", symbol.getValue()));
				}
				//레지스터들 16진수로 설정
				else if (operand.equals("ds")) {
					statement.setToHexString("FB");
				} else if (operand.equals("hs")) {
					statement.setToHexString("FC");
				} else if (operand.equals("ss")) {
					statement.setToHexString("FD");
				} else if (operand.equals("hp")) {
					statement.setToHexString("FE");
				} else if (operand.equals("sp")) {
					statement.setToHexString("FF");
				}
				//그외 상수들 16진수형태로 변경
				else{
					statement.setToHexString(String.format("%02X", Integer.parseInt(operand)));
				}
			}
			//operand가 없는 경우 00으로 채우기 (00으로 채워놔야 int로 맞게 변환가능)
			else if (statement.getOperandCount() == 0) {
				statement.setToHexString("00");
			}
		}
	}

	/**
	 * operator에 따른 16진수 코드 반환
	 */
	private String getOperatorCode(String operatorString) {
		switch (operatorString) {
			case "halt":
				return "0x01";
			case "loada":
				return "0x02";
			case "loadc":
				return "0x03";
			case "loadr":
				return "0x04";
			case "stoa":
				return "0x05";
			case "set":
				return "0x06";
			case "adda":
				return "0x07";
			case "addc":
				return "0x08";
			case "addr":
				return "0x09";
			case "suba":
				return "0x0A";
			case "jump":
				return "0x0B";
			case "jumpra":
				return "0x0C";
			case "gtj":
				return "0x0D";
			case "input":
				return "0x0E";
			case "print":
				return "0x0F";
			case "pushh":
				return "0x10";
			case "pushs":
				return "0x11";
			case "pop":
				return "0x12";
			case "div":
				return "0x13";
			default:
				throw new IllegalArgumentException("Invalid operator");
		}
	}

	/**
	 * exe파일 생성을 위한 toString() 생성
	 */
	@Override
	public String toString() {
		String result = "";
		for (SStatement statement : this.statements) {
			result += statement.toString() + "\n";
		}
		return result;
	}

	public String getCs() {
		String result = "CS ";
		int size = this.statements.size();
		result += size;
		return result;
	}
}