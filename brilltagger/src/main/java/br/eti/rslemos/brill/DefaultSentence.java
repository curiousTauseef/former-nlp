package br.eti.rslemos.brill;

import java.util.Iterator;

public class DefaultSentence implements Sentence {
	private final Token[] sentence;

	public DefaultSentence(Token... sentence) {
		this.sentence = sentence;
	}

	public Iterator<Token> iterator() {
		return new SentenceContext(this);
	}

	public int size() {
		return sentence.length;
	}

	public Token get(int i) {
		try {
			return sentence[i];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SentenceIndexOutOfBoundsException(i);
		}
	}
	
	
}