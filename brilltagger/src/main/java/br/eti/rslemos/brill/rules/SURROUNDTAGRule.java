package br.eti.rslemos.brill.rules;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;

public class SURROUNDTAGRule extends AbstractRule {
	public static final RuleFactory FACTORY = SURROUNDTAGRuleFactory.INSTANCE;
	
	private final Object prev1Object;
	private final Object next1Object;

	public SURROUNDTAGRule(Object from, Object to, Object prev1Object, Object next1Object) {
		super(from, to);
		this.prev1Object = prev1Object;
		this.next1Object = next1Object;
	}

	@Override
	public boolean matches(Context context) {
		return thisMatches(context) && super.matches(context);
	}

	private boolean thisMatches(Context context) {
		Object tag_1 = context.getToken(-1).getTag();
		Object tag1 = context.getToken(1).getTag();
		
		return (prev1Object != null ? prev1Object.equals(tag_1) : tag_1 == null) &&
			(next1Object != null ? next1Object.equals(tag1) : tag1 == null);
	}
	
	@Override
	public boolean firingDependsOnObject(Object tag) {
		return super.firingDependsOnObject(tag) || 
			(prev1Object != null ? prev1Object.equals(tag) : tag == null) ||
			(next1Object != null ? next1Object.equals(tag) : tag == null);
	}

	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		
		SURROUNDTAGRule other = (SURROUNDTAGRule) o;
		
		return (prev1Object != null ? prev1Object.equals(other.prev1Object) : other.prev1Object == null) &&
			(next1Object != null ? next1Object.equals(other.next1Object) : other.next1Object == null);
	}

	@Override
	public int hashCode() {
		int hashCode = super.hashCode();
		
		hashCode *= 13;
		hashCode += prev1Object != null ? prev1Object.hashCode() : 0;
		hashCode *= 7;
		hashCode += next1Object != null ? next1Object.hashCode() : 0;
		
		return hashCode;
	}

	@Override
	public String toBrillString() {
		return super.toBrillString() + " " + prev1Object + " " + next1Object;
	}
}
