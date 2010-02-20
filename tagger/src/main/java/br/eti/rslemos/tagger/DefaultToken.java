package br.eti.rslemos.tagger;

public final class DefaultToken implements Token {

	private final String word;
	private String tag;

	public DefaultToken(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return word + "/" + tag;
	}

	
}