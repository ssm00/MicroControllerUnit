import java.util.Vector;

public class SSymbolTable extends Vector<SSymbolEntity>{

    public SSymbolEntity get(String variableName) {
        for (SSymbolEntity entity : this) {
            if (entity.getVariableName().equals(variableName)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "";
        for (SSymbolEntity entity : this) {
            result += entity.getVariableName() + " " + entity.getValue() + "\n";
        }
        return result;
    }

    public boolean labelCheck(String label) {
        for (SSymbolEntity entity : this) {
            if (entity.getVariableName().equals(label)) {
                return true;
            }
        }
        return false;
    }

}
