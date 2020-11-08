package com.uade.financialEntity.messages;

@lombok.Getter
public class MessageResponse implements Response {

	//ATTRIBUTES
	private java.util.Map<String, String> mapMessage;
	private java.util.Map<String, Object> mapObject;

	//BUILDERS
	public MessageResponse(com.uade.financialEntity.utils.Pair... args) {
		super();
		this.mapMessage = new java.util.HashMap<>();
		for (com.uade.financialEntity.utils.Pair arg : args) {
			mapMessage.put(arg.getKey(), arg.getValue());
		}
	}

	public MessageResponse(com.uade.financialEntity.utils.PairObject... args) {
		super();
		this.mapObject = new java.util.HashMap<>();
		for (com.uade.financialEntity.utils.PairObject arg : args) {
			mapObject.put(arg.getKey(), arg.getValue());
		}
	}

	public MessageResponse(String string) {
		super();
		this.mapMessage = new java.util.HashMap<>();
		mapMessage.put("message", string);
	}

	public MessageResponse() {
	}

}
