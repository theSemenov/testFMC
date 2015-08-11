package test.fmc.simpleclient.protocol;

import java.util.ArrayList;

public interface Command {

	String getName();
	String getDelimiter();
	ArrayList<String> getParam();
}
