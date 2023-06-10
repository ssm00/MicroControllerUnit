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
					case 3:
						statement = new SStatement(tokens[0], tokens[1], tokens[2]);
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
	 * 기계어 번역을 위한 2단계 파싱
	 * 주소값, 레지스터값, Label을 16진수로 변경
	 * @주소 -> symbolTable의 주소로 2자리 16진수로 변경
	 * 누산기는 ac1 -> 0xFE ac2 -> 0xFF로 정의.
	 * Label의 경우 line number로 변경
	 */
	public void secondPhaseParse() {
		for (SStatement statement : this.statements) {
			String operator = statement.getOperator();
			String operatorCode = getOperatorCode(operator);
			statement.setOperator(operatorCode);

			for (int i = 1; i <= statement.getOperandCount(); i++) {
				String operand = statement.getOperand(i);
				//주소
				if (operand.startsWith("@")) {
					SSymbolEntity symbol = this.symbolTable.get(operand.substring(1));
					statement.setToHexString(i,  String.format("%02X", symbol.getValue()));
				}
				//라벨
				else if (symbolTable.labelCheck(operand)) {
					SSymbolEntity symbol = this.symbolTable.get(operand);
					statement.setToHexString(i,  String.format("%02X", symbol.getValue()));
				}
				//누산기
				else if (operand.equals("ac1")) {
					statement.setToHexString(i, "FE");
				} else if (operand.equals("ac2")) {
					statement.setToHexString(i, "FF");
				}
				//그외 숫자는 16진수형태로
				else{
					statement.setToHexString(i, String.format("%02X", Integer.parseInt(operand)));
				}
			}
			//빈경우 00으로 채우기
			if (statement.getOperandCount() == 0) {
				statement.setToHexString(1, "00");
				statement.setToHexString(2, "00");
			}
			if (statement.getOperandCount() == 1) {
				statement.setToHexString(2, "00");
			}
		}
	}

	/**
	 * operator에 따른 16진수 코드 반환
	 */
	private String getOperatorCode(String operatorString) {
		switch (operatorString) {
			case "move":
				return "0x01";
			case "add":
				return "0x02";
			case "sub":
				return "0x03";
			case "mul":
				return "0x04";
			case "div":
				return "0x05";
			case "jump":
				return "0x06";
			case "gtj":
				return "0x07";
			case "gtjn":
				return "0x08";
			case "lda":
				return "0x09";
			case "sto":
				return "0x0A";
			case "halt":
				return "0x0B";
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
}