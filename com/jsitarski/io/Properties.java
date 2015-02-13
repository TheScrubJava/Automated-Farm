package com.jsitarski.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.jsitarski.utils.JMap;

public class Properties {
	private final JMap<String, Object> MAP = new JMap<String, Object>();
	private String scriptName;

	private File DEFAULT_FOLDERS = null;
	private File PROPERTIES_FILE = null;

	public Properties(final String scriptName) {
		this.scriptName = scriptName;
		DEFAULT_FOLDERS = new File(System.getProperty("user.home") + File.separator + "ScrubScripting" + File.separator + scriptName);
		PROPERTIES_FILE = new File(System.getProperty("user.home") + File.separator + "ScrubScripting" + File.separator + scriptName + File.separator + "properties.ini");
		loadMap();
	}

	public String getScriptName() {
		return scriptName;
	}

	public JMap<String, Object> getMap() {
		loadMap();
		return MAP;
	}

	public Object getProperty(String key) {
		loadMap();
		return MAP.getValue(key);
	}

	public void addProperty(String key, Object object) {
		if (key != null && object != null) {
			if (!MAP.containsKey(key) && !fileContains(key)) {
				String propertyLine = key + ":" + object.toString();
				addProperty(propertyLine);
				MAP.add(key, object.toString());
			}
		}
	}

	public void updateProperty(String key, Object object) {
		if (key != null) {
			if (!MAP.containsKey(key)) {
				addProperty(key, object.toString());
			} else {
				removeProperty(key);
				addProperty(key, object.toString());
			}
		}
	}

	public void removeProperty(String key) {
		if (key != null) {
			if (MAP.containsKey(key)) {
				removeLine(key);
			}
		}
	}

	private boolean fileContains(String key) {
		if (validatePath()) {
			BufferedReader f = null;
			try {
				// append true
				f = new BufferedReader(new FileReader(PROPERTIES_FILE));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (f != null) {
				String line = null;
				try {
					line = f.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (line != null) {
					if (line.equalsIgnoreCase(key))
						return true;
					try {
						line = f.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}

	private void removeLine(String key) {
		if (key != null) {
			MAP.remove(key);
			replaceSelected("", key);
		}
	}

	private void replaceSelected(String replacement, String old) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(PROPERTIES_FILE));
			String line;
			String input = "";
			while ((line = file.readLine()) != null)
				input += line + '\n';

			if (input.contains(old + ":")) {
				input = replacement;
			}
			FileOutputStream File = new FileOutputStream(PROPERTIES_FILE);
			File.write(input.getBytes());
			File.close();
			file.close();
		} catch (Exception e) {
			System.out.println("Problem reading file.");
		}
	}

	private void loadMap() {
		if (validatePath()) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(new File(PROPERTIES_FILE.getAbsolutePath())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (br != null) {
				String line;
				try {
					while ((line = br.readLine()) != null) {
						if (line != null && line.contains(":")) {
							String[] tokens = line.split(":");
							if (tokens.length > 1)
								MAP.add(tokens[0], tokens[1]);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean addProperty(String... settings) {
		if (validatePath()) {
			FileWriter f = null;
			try {
				f = new FileWriter(PROPERTIES_FILE.getAbsolutePath(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (f != null) {
				for (String setting : settings) {
					try {
						f.append(setting + System.getProperty("line.separator"));
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
				try {
					f.flush();
					f.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

	private boolean validatePath() {
		if (DEFAULT_FOLDERS != null) {
			if (DEFAULT_FOLDERS.exists()) {
				if (!PROPERTIES_FILE.canRead())
					try {
						new FileWriter(PROPERTIES_FILE, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				return true;

			} else {
				DEFAULT_FOLDERS.mkdirs();
			}

			if (!PROPERTIES_FILE.canRead())
				try {
					new FileWriter(PROPERTIES_FILE, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return DEFAULT_FOLDERS.exists();
		}
		return false;
	}

	public static void main(String[] a) {
		Properties p = new Properties("Test");
		p.getMap().printMap();
		System.out.println(p.getProperty("1"));
	}
}
