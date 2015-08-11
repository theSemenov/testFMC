package test.fmc.simpleclient.protocol.impl;

import test.fmc.simpleclient.protocol.ClientData;
import test.fmc.simpleclient.protocol.Command;

public class CommandBuilder {

	public static String defaultDelimeter = "|";
	
	public static String asString(Command c) {
		String commandString = c.getName() + c.getDelimiter();
		for(String p : c.getParam()) {
			commandString += p + c.getDelimiter();
		}
		return commandString;
		
	}
	
	public static Command createGetDataCommand() {
		DefultCommand c = new DefultCommand("getclientdata", defaultDelimeter);
		return c;
		
	}
	
	public static Command createSetDataCommand(ClientData data) {
		DefultCommand c = new DefultCommand("setclientdata", defaultDelimeter, data.getFio(), data.getWorkPhone(), data.getMobilePhone(), data.getEmail());
		return c;
		
	}
}
