package jp.ka.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	String RemoteAddr;
	String RemoteUser;
	Long TimeLocal;

	// String Request;
	String Method;
	String URI;
	String Protocol;

	String Status;
	String BodyBytesSent;
	String HttpReferer;
	String HttpUserAgent;
	String HttpXForwardedFor;
	String SSLProtocol;
	String SSLCipher;
	String UpstreamAddr;
	String RequestTime;
	String UpstreamResponseTime;
}
