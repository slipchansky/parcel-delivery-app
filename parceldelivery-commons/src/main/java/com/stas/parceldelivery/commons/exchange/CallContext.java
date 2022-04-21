package com.stas.parceldelivery.commons.exchange;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CallContext {
	private final static InheritableThreadLocal<CallContext> CONTEXT_HOLDER = new InheritableThreadLocal<>();
	private String userId;
	private List<String> roles;
	private String correlationId;
	private String requestId;
	 
	public static CallContext getInstance() {
		CallContext context = CONTEXT_HOLDER.get();
		if(context == null)
			context = createContext();
		return context;
	}
	
	private static synchronized CallContext createContext() {
		CallContext context = CONTEXT_HOLDER.get();
		if(context == null)
			context = new CallContext();
		return context;
	}

	private CallContext() {
		CONTEXT_HOLDER.set(this);
	}

	public static CallContext get() {
		return CONTEXT_HOLDER.get();
	}



}
