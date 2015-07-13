package net.virtela.config;

import net.virtela.utils.CommonHelper;
import net.virtela.utils.Constants;

public class ServerInfo {
	
	private static final String TEST_NCOP_URL = "http://dcoeng1-ncoptst-1/VirtelaAccessPricing";
	private static final String UAT_NCOP_URL = "http://dcoeng1-ncopsbx-1/VirtelaAccessPricing";
	
	private static final String MODE_TEST = "TST";
	private static final String MODE_UAT = "SBX";
	
	public static String getNcopUrl() {
		switch (CommonHelper.readConfig(Constants.CONFIG_KEY_MODE)) {
		case MODE_TEST:
			return TEST_NCOP_URL;
		case MODE_UAT:
			return UAT_NCOP_URL;
		default:
			return TEST_NCOP_URL;
		}
	}
	
}
