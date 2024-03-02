public class Token {
	private String typeValue;
	private String value;

	public Token(String typeValue, String value) {
		this.typeValue = typeValue;
		this.value = value;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return typeValue + value;
	}

}
