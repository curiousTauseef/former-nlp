package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.tagger.Tag;

public class NEXTTAGRule extends AbstractRule implements SerializableAsBrillText  {
	public static final  RuleFactory FACTORY() {
		return new NEXTTAGRuleFactory();
	}
	
	private final Tag nextTag;

	public NEXTTAGRule(Tag from, Tag to, Tag nextTag) {
		super(from, to);
		
		this.nextTag = nextTag;
	}

	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Tag tag1 = context.getToken(1).getTag();
		
		return nextTag != null ? nextTag.equals(tag1) : tag1 == null;
	}
	
	@Override
	public boolean firingDependsOnTag(Tag tag) {
		return super.firingDependsOnTag(tag) || 
			(nextTag != null ? nextTag.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		NEXTTAGRule other = (NEXTTAGRule) o;
		
		return nextTag != null ? nextTag.equals(other.nextTag) : other.nextTag == null;
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 23;
		hashCode += nextTag != null ? nextTag.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillText() {
		return super.toBrillText() + " " + nextTag;
	}
}
