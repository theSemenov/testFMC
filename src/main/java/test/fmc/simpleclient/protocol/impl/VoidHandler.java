package test.fmc.simpleclient.protocol.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import test.fmc.simpleclient.protocol.ClientData;
import test.fmc.simpleclient.protocol.ResponseHandler;

public class VoidHandler implements ResponseHandler<Void> {

	public Void processResponse(Object o) throws Throwable {
		return null;
		
	}

	public void onSuccess(Void object) {
		
		
	}

	public void onFault(Throwable t) {
		
	}


}
