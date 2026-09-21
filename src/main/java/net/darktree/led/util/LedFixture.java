package net.darktree.led.util;

public enum LedFixture {

	BUTTON("button", null),
	SWITCH("switch", null),

	FULL("clear_full",       "BBB,BAB,BBB"),
	SMALL("small_fixture",   " B , A ,CCC"),
	MEDIUM("medium_fixture", " B ,BAB,CCC"),
	LARGE("large_fixture",   "BBB,BAB,CCC"),
	FLAT("flat_fixture",     "BBB, A ,CCC");

	final String id;
	final String pattern;

	LedFixture(String id, String pattern) {
		this.id = id;
		this.pattern = pattern;
	}

	public String getId() {
		return id;
	}

	public String getPattern() {
		return pattern;
	}

}
