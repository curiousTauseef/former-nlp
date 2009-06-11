package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class WDNEXTTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final RuleFactory FACTORY = new AbstractRuleFactory() {

		public Rule create(String from, String to, Context context) throws RuleCreationException {
			String word0 = context.getToken(0).getWord();
			String tag1 = context.getToken(1).getTag();
			
			return new WDNEXTTAGRule(from, to, word0, tag1);
		}
		
	};

	private final String word;
	private final String next1Tag;

	public WDNEXTTAGRule(String from, String to, String word, String next1Tag) {
		super(from, to);
		this.word = word;
		this.next1Tag = next1Tag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		String word0 = context.getToken(0).getWord();
		String tag1 = context.getToken(1).getTag();
		
		return (word != null ? word.equals(word0) : word0 == null) &&
			(next1Tag != null ? next1Tag.equals(tag1) : tag1 == null);
	}
	
	@Override
	public boolean firingDependsOnTag(String tag) {
		return super.firingDependsOnTag(tag) || 
			(next1Tag != null ? next1Tag.equals(tag) : tag == null);
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		WDNEXTTAGRule other = (WDNEXTTAGRule) o;
		
		return (word != null ? word.equals(other.word) : other.word == null) &&
			(next1Tag != null ? next1Tag.equals(other.next1Tag) : other.next1Tag == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 19;
		hashCode += word != null ? word.hashCode() : 0;
		hashCode *= 13;
		hashCode += next1Tag != null ? next1Tag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + word + " " + next1Tag;
	}
}
