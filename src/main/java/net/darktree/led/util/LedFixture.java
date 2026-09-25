package net.darktree.led.util;

public enum LedFixture {

	BUTTON("button", null, false),
	SWITCH("switch", null, false),

	FULL("clear_full",       "BBB,BAB,BBB", true),
	SMALL("small_fixture",   " B , A ,CCC", true),
	MEDIUM("medium_fixture", " B ,BAB,CCC", true),
	LARGE("large_fixture",   "BBB,BAB,CCC", true),
	FLAT("flat_fixture",     "BBB, A ,CCC", true);

	final String id;
	final String pattern;
	final boolean variable;

	LedFixture(String id, String pattern, boolean variable) {
		this.id = id;
		this.pattern = pattern;
		this.variable = variable;
	}

	public String getId() {
		return id;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isVariable() {
		return variable;
	}

}
