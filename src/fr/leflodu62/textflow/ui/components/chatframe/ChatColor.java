package fr.leflodu62.textflow.ui.components.chatframe;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public enum ChatColor {

	BLACK('0', "#060608"),
	DARK_BLUE('1', "#0703A2"),
	DARK_GREEN('2', "#0AA30B"),
	DARK_AQUA('3', "#00AEA0"),
	DARK_RED('4', "#911106"),
	DARK_PURPLE('5', "#9C1498"),
	GOLD('6', "#FFA600"),
	GRAY('7', "#A6ABA7"),
	DARK_GRAY('8', "#575151"),
	BLUE('9', "#5B5DFF"),
	GREEN('a', "#48FF50"),
	AQUA('b', "#4AFFFA"),
	RED('c', "#FD4C46"),
	LIGHT_PURPLE('d', "#FF54FF"),
	YELLOW('e', "#FCFF4D"),
	WHITE('f', "#FFFFFF"),
	//MAGIC( 'k', "<span class=\"magic\">", "</span>"),
	BOLD('l', "<b>", "</b>"),
	STRIKETHROUGH('m', "<strike>", "</strike>"),
	UNDERLINE('n', "<u>", "</u>"),
	ITALIC('o', "<i>", "</i>"),
	RESET('r');
	public static final char COLOR_CHAR = '§';
	public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
	private static final Pattern STRIP_COLOR_PATTERN = Pattern
			.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");
	private static final Map<Character, ChatColor> BY_CHAR = new HashMap<Character, ChatColor>();
	private final char code;
	private final String toString;

	private final String starting;
	private final String ending;
	private final boolean isColor;
	private String color;

	static {
		for (final ChatColor colour : values()) {
			BY_CHAR.put(colour.code, colour);
		}
	}

	private ChatColor(char code) {
		this(code, null, null);
	}

	private ChatColor(char code, String color) {
		this(code, "<span color=\"" + color + "\">", "</span>", true);
		this.color = color;
	}

	private ChatColor(char code, String starting, String ending) {
		this(code, starting, ending, false);
	}

	private ChatColor(char code, String starting, String ending, boolean isColor) {
		this.code = code;
		this.starting = starting;
		this.ending = ending;
		this.isColor = isColor;
		this.toString = new String(new char[] { COLOR_CHAR, code });
	}

	public static String toHTML(String message) {
		final HashMap<Integer, ChatColor> openned = new HashMap<>();
		final StringBuilder out = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			final char c = message.charAt(i);
			if (c == COLOR_CHAR) {
				final ChatColor color = getByChar(message.charAt(++i));
				if (color != null) {
					if((color.isColor || color.equals(RESET)) && !openned.isEmpty()) {
						openned.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getKey(), e1.getKey())).forEach(co -> out.append(co.getValue().ending));
						openned.clear();
					}
					if(!color.equals(RESET)) {
						if(color.starting != null) {
							out.append(color.starting);
						}
						openned.put(i, color);
					}
				}
			} else {
				out.append(c);
			}
		}
		openned.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getKey(), e1.getKey())).forEach(co -> out.append(co.getValue().ending));
		openned.clear();
		
		return out.toString().replace(System.lineSeparator(), "<br>");
	}

	public static String stripColor(final String input) {
		if (input == null) {
			return null;
		}

		return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
	}
	
	public String getHexCode() {
		return color;
	}
	
	public int getColorRGB() {
		return Integer.parseInt(getHexCode().replaceFirst("#", ""), 16);
	}
	
	public Color getColor() {
		final int rgb = getColorRGB();
		final int red = (rgb >> 16) & 0xFF;
		final int green = (rgb >> 8) & 0xFF;
		final int blue = rgb & 0xFF;
		return new Color(red, green, blue);
	}
	
	public String getStarting() {
		return starting;
	}
	
	public String getEnding() {
		return ending;
	}
	
	public boolean isColor() {
		return isColor;
	}

	@Override
	public String toString() {
		return toString;
	}

	public static ChatColor getByChar(char code) {
		return BY_CHAR.get(code);
	}
}
