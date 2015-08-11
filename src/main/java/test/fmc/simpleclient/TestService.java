package test.fmc.simpleclient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.fmc.simpleclient.protocol.ClientData;
import test.fmc.simpleclient.protocol.impl.ClientDataHandler;
import test.fmc.simpleclient.protocol.impl.SecureService;

public class TestService {

	public static void main(String[] args) {
		/*String info = null;
		String responseString = "\\clientdata&5&Иванов Иван Иванович&-&+7 XXX XXX-XX-XX&-&105&/";
		String regex = "\\\\clientdata&(.*?)&/";
		Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(responseString);
		while (matcher.find()) {
			info = matcher.group(1);
		}
		//return info;
		*/
		
		SecureService service = new SecureService();
		service.getClientData(new ClientDataHandler() {
			
			public void onSuccess(ClientData object) {
				System.out.println(object.toString());
				
			}
			
			public void onFault(Throwable t) {
				t.printStackTrace();
			}
		});
	}
}
