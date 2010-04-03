package br.eti.rslemos.brill.rules.lexical;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public abstract class AbstractRule extends br.eti.rslemos.brill.AbstractRule {

	protected AbstractRule() {
	}

	protected AbstractRule(Tag from, Tag to) {
		super(from, to);
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		return thisMatches(context.getToken(0).getWord());
	}

	protected abstract boolean thisMatches(String word0);

	@Override
	protected String toBrillText() {
		return getTo() + " " + getType();
	}
	
	
}
