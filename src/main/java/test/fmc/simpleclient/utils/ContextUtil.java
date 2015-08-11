package test.fmc.simpleclient.utils;

@Deprecated
/**
 * use @Resource annotation
 * @author Семенов Алексей
 *
 */
public class ContextUtil {
	
	public static String getRemoteUrl() {	
		return LookupUtil.lookup("SimpleClient/remote-url");
	}
	
	public static String getRemotePort() {	
		return LookupUtil.lookup("SimpleClient/remote-port");
	}
	
	public static String getRemoteKey() {	
		return LookupUtil.lookup("SimpleClient/remote-key");
	}
	
	public static String getRemoteLogin() {	
		return LookupUtil.lookup("SimpleClient/remote-login");
	}
	
	public static String getRemotePassword() {	
		return LookupUtil.lookup("SimpleClient/remote-password");
	}
	
	public static String getCommandPrefix() {	
		return LookupUtil.lookup("SimpleClient/command-prefix");
	}
}
