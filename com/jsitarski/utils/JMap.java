package com.jsitarski.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author John
 * 
 * @param <K>
 *            - Key Type of the JMap
 * @param <V>
 *            - Value Type of the JMap
 */
public class JMap<K, V> {

	private List<JObject<K, V>> list;
	private final Object lock = new Object();

	public JMap() {
		list = new ArrayList<JObject<K, V>>();
	}

	/**
	 * 
	 * @param maxSize
	 *            The max size of the list.
	 */
	public JMap(int maxSize) {
		list = new ArrayList<JObject<K, V>>(maxSize);
	}

	/**
	 * 
	 * @param key
	 *            - Key of the entry.
	 * @param value
	 *            - Value of the entry.
	 */
	public void add(K key, V value) {
		synchronized (lock) {
			if (key != null && value != null) {
				if (!containsKey(key)) {
					list.add(new JObject<K, V>(key, value));
				}
			}
		}
	}

	public void remove(K key) {
		synchronized (lock) {
			if (key != null) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) != null) {
						K k = list.get(i).getKey();
						if (k != null) {
							if (key.equals(k)) {
								// index check...
								if (i <= list.size() - 1) {
									list.remove(i);
								}
							}
						}
					}
				}
			}
		}
	}

	public int getSize() {
		synchronized (lock) {
			return list.size();
		}
	}

	public V getValue(K key) {
		synchronized (lock) {
			if (key != null) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) != null) {
						K k = list.get(i).getKey();
						if (k != null) {
							if (key.equals(k)) {
								// index check...
								if (i <= list.size() - 1)
									return list.get(i).getValue();
							}
						}
					}
				}
			}
			return null;
		}
	}

	private int getIndex(K key) {
		if (key != null) {
			synchronized (lock) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) != null) {
						K k = list.get(i).getKey();
						if (k != null) {
							if (key.equals(k)) {
								return i;
							}
						}
					}
				}
			}
		}
		return -1;
	}

	public List<JObject<K, V>> getList() {
		return list;
	}

	public void printMap() {
		synchronized (lock) {
			for (JObject<K, V> j : list) {
				if (j != null)
					System.out.println(j);
			}
		}
	}

	public void update(K key, V value) {
		if (key != null && containsKey(key)) {
			synchronized (lock) {
				int index = getIndex(key);
				JObject<K, V> object = list.get(index);
				object.setValue(value);
				list.set(index, object);
			}
		}
	}

	public boolean containsKey(K key) {
		if (key != null) {
			for (JObject<K, V> o : list) {
				if (o != null) {
					K k = o.getKey();
					if (k != null) {
						if (key.equals(k)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public String toString() {
		String result = "";
		for (JObject<K, V> j : list) {
			if (j != null)
				result += (j + System.getProperty("line.separator"));
		}
		result += "Map Size: " + getSize();
		return result;
	}

	public static void main(String[] a) {
		final JMap<String, Integer> map = new JMap<String, Integer>();
		map.add("k", 5);
		map.add("K", 12);
		map.printMap();
		map.update("K", 7);
		map.printMap();
	}
}
