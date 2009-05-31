package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXTBIGRAMRule extends AbstractRule implements SerializableAsBrillText {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word1 = context.getToken(1).getWord();
			String word2 = context.getToken(2).getWord();

			return new NEXTBIGRAMRule(from, to, word1, word2);
		}
		
	};

	private final String next1Word;
	private final String next2Word;

	public NEXTBIGRAMRule(String from, String to, String next1Word, String next2Word) {
		super(from, to);
		this.next1Word = next1Word;
		this.next2Word = next2Word;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word1 = context.getToken(1).getWord();
		String word2 = context.getToken(2).getWord();
		
		return (next1Word != null ? next1Word.equals(word1) : word1 == null) &&
			(next2Word != null ? next2Word.equals(word2) : word2 == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXTBIGRAMRule other = (NEXTBIGRAMRule) o;
		
		return (next1Word != null ? next1Word.equals(other.next1Word) : other.next1Word == null) &&
			(next2Word != null ? next2Word.equals(other.next2Word) : other.next2Word == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 11;
		hashCode += next1Word != null ? next1Word.hashCode() : 0;
		hashCode *= 5;
		hashCode += next2Word != null ? next2Word.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + next1Word + " " + next2Word;
	}
}
