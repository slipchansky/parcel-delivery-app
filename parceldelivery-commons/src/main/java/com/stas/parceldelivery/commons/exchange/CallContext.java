package com.stas.parceldelivery.commons.exchange;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;

import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CallContext {
	private final static InheritableThreadLocal<CallContext> CONTEXT_HOLDER = new InheritableThreadLocal<>();
	private String userId;
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
		if(context == null) {
			context = new CallContext();
		}
		CONTEXT_HOLDER.set(context);
		return context;
	}

	private CallContext() {
		CONTEXT_HOLDER.set(this);
	}

	public static CallContext get() {
		return CONTEXT_HOLDER.get();
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
        ThreadContext.put(ParceldeliveryHeaders.CORRELATION_ID, correlationId);
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
        ThreadContext.put(ParceldeliveryHeaders.REQUEST_ID, requestId);
	}

	public void updateContext(String correlationId, String requestId) {
		setCorrelationId(correlationId);
		setRequestId(requestId);
		updateCallTrace();
	}

	private void updateCallTrace() {
		ThreadContext.put(ParceldeliveryHeaders.CALL_TRACE, "requestId="+this.requestId+", correlationId="+this.correlationId);
	}

}
