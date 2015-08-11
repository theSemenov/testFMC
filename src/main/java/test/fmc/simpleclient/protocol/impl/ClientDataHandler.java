package test.fmc.simpleclient.protocol.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;

import test.fmc.simpleclient.protocol.ClientData;
import test.fmc.simpleclient.protocol.ResponseHandler;
import test.fmc.simpleclient.ui.IndexPage;

public abstract class ClientDataHandler implements ResponseHandler<ClientData> {

	private static Log _log = LogFactory.getLog(ClientDataHandler.class);

	public ClientData processResponse(Object o) throws Throwable {
		if (!(o instanceof HttpResponse)) {
			throw new ClassCastException("only HttpResponse class accepted");
		}
		HttpResponse response = (HttpResponse) o;
		String info = extractInfo(response);
		if (info == null || info.isEmpty()) {
			return null;
		}
		String[] data = info.split("&");
		Integer contParams = Integer.parseInt(data[0]);
		DefaultClientData clientData = new DefaultClientData();
		clientData.setFio(data[1]);
		clientData.setWorkPhone(data[2]);
		clientData.setMobilePhone(data[3]);
		clientData.setEmail(data[4]);
		return clientData;
	}

	private String extractInfo(HttpResponse response) throws Exception {
		String info = null;
		try {
			String responseString = IOUtils.toString(response.getEntity()
					.getContent(), "UTF-8");
			String regex = "\\\\clientdata&(.*?)&/";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(responseString);
			if (matcher.find()) {
				info = matcher.group(1);
			}
			return info;
		} catch (UnsupportedOperationException e) {
			_log.error(e);
			throw e;
		} catch (IOException e) {
			_log.error(e);
			throw e;
		}
	}
}
