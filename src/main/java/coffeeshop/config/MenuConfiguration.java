package coffeeshop.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MenuConfiguration {
	private static Map<String, String> MENU_URL_PATTERN_MAP = new HashMap<>();
	
	static
	{
		MENU_URL_PATTERN_MAP.put("/dashboard", "Dashboard");
		MENU_URL_PATTERN_MAP.put("/products", "Products");
		MENU_URL_PATTERN_MAP.put("/orders", "Orders");
	}
	
	public static Map<String, String> getMenuUrlPatternMap() {
		return Collections.unmodifiableMap(MENU_URL_PATTERN_MAP);
	}

	public static String getMatchingMenu(String uri) {
		Set<String> keySet = MENU_URL_PATTERN_MAP.keySet();
		for (String key : keySet) {
			if(uri.contains(key)){
				return MENU_URL_PATTERN_MAP.get(key);
			}
		}
		return "";
	}
}
