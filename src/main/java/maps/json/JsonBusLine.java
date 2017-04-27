package maps.json;

import maps.interfaces.BusLine;

public class JsonBusLine {
	public String line;
	public String description;
	
	public JsonBusLine(BusLine busLine) {
		this.line = busLine.getLine();
		this.description = busLine.getDescription();
	}

	public JsonBusLine(String line, String description) {
		this.line = line;
		this.description = description;
	}
	
	
}
