package com.hd.base;

public interface Selectable {
	public void Select(int idx);
	public void Select(String key);
	public void Select(Object idx);
	public int Select();
	public int SelectPriv();
	public int SelectNext();
}
