package jp.ka.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	String RemoteAddr;
	String HttpXForwardedFor;
	String TimeLocal;
	String Status;
	String SSLProtocol;
	String SSLCipher;
	String UpstreamAddr;
	String UpstreamResponseTime;
	String BodyBytesSent;
	String RequestTime;
	String RemoteUser;

	// String Request;
	String Method;
	String URI;
	String Protocol;

	String HttpReferer;
	String HttpUserAgent;
}
