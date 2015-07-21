package pipeprt.dataLayer;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class TokenClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private Color tokenColour;
	private String id = "";
	private boolean enabled = false;
	private int lockCount = 0; // So that users cannot change this class while
	// places are marked with it

	public TokenClass(boolean enabled, String id, Color colour) {
		this.enabled = enabled;
		this.id = id;
		tokenColour = colour;
	}


	public Color getColour() {
		return tokenColour;
	}

	public void setColour(Color colour) {
		tokenColour = colour;
	}

	public void incrementLock() {
		lockCount++;
	}

	public void decrementLock() {
		lockCount--;
	}

	public boolean isLocked() {
		return lockCount > 0;
	}

	public int getLockCount() {
		return lockCount;
	}

	public void setLockCount(int newLockCount) {
		lockCount = newLockCount;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public boolean hasSameId(TokenClass otherTokenClass) {
		return otherTokenClass.getID().equals(this.getID());
	}


}
