package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class NEXT1OR2TAGRule extends AbstractRule {

	private final String next1or2Tag;

	public NEXT1OR2TAGRule(String from, String to, String next1or2Tag) {
		super(from, to);
		
		this.next1or2Tag = next1or2Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String tag1 = context.getToken(1).getTag();
		String tag2 = context.getToken(2).getTag();
		
		return next1or2Tag != null 
		? (next1or2Tag.equals(tag1) | next1or2Tag.equals(tag2)) 
		: (tag1 == null | tag2 == null);
	}
}