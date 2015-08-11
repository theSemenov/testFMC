package test.fmc.simpleclient.protocol.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import test.fmc.simpleclient.protocol.Command;

public class DefultCommand implements Command, Serializable {

	private String name;
	private String delimeter;
	private ArrayList<String> param;
	
	public DefultCommand() {
		
	}
	
	public DefultCommand(String name, String delimeter, String... param) {
		super();
		this.name = name;
		this.delimeter = delimeter;
		this.param = new ArrayList<String>();
		this.param.addAll(Arrays.asList(param));
	}

	public String getName() {
		return name;
	}

	public String getDelimiter() {
		return delimeter;
	}

	public ArrayList<String> getParam() {
		return param;
	}

	public String getDelimeter() {
		return delimeter;
	}

	public void setDelimeter(String delimeter) {
		this.delimeter = delimeter;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParam(ArrayList<String> param) {
		this.param = param;
	}

}
