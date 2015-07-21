package pipeprt.dataLayer;

import java.io.Serializable;


/**
 * @author Alex Charalambous (June 2010): Created this
 * class to add support for marking an arbitrary number
 * of token classes. Apart from the actual marking,
 * the type of token to be marked is defined.
 */

public class PipeMarking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TokenClass tokenClass;
	private String currentMarking;
	
	public PipeMarking(TokenClass tokenClass){
		this.tokenClass = tokenClass;
		currentMarking = "";
	}
	
	public PipeMarking(TokenClass tokenClass, String marking){
		this.tokenClass = tokenClass;
		currentMarking = marking;
	}

	public TokenClass getTokenClass() {
		return tokenClass;
	}

	public void setTokenClass(TokenClass tokenClass) {
		this.tokenClass = tokenClass;
	}

	public void setCurrentMarking(String marking) {
		currentMarking = marking;
	}
	public String getCurrentMarking() {
		return currentMarking;
	}
}
