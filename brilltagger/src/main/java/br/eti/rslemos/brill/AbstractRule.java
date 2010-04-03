package br.eti.rslemos.brill;

import br.eti.rslemos.tagger.Tag;

public abstract class AbstractRule implements Rule {
	private Tag from;
	private Tag to;

	public AbstractRule () {
	}

	public AbstractRule (Tag from, Tag to) {
		this.from = from;
		this.to = to;
	}
	
	public final Tag getFrom() {
		return from;
	}

	public final Tag getTo() {
		return to;
	}

	public boolean matches(Context context) {
		Tag tag0 = context.getToken(0).getTag();
		
		return from != null ? from.equals(tag0) : tag0 == null;
	}

	public final boolean apply(Context context) {
		if (matches(context)) {
			context.getToken(0).setTag(to);
			return true;
		} else
			return false;
	}

	public boolean firingDependsOnTag(Tag tag) {
		return from != null ? from.equals(tag) : tag == null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;

		AbstractRule other = (AbstractRule)o;

		return (to != null ? to.equals(other.to) : other.to == null) &&
			(from != null ? from.equals(other.from) : other.from == null);
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode += this.getClass().hashCode();
		hashCode *= 3;
		hashCode += to != null ? to.hashCode() : 0;
		hashCode *= 3;
		hashCode += from != null ? from.hashCode() : 0;

		return hashCode;
	}

	@Override
	public String toString() {
		return toBrillText();
	}
	
	protected String toBrillText() {
		return from + " " + to + " " + getType();
	}

	protected final String getType() {
		String simpleName = getClass().getSimpleName();
		if (simpleName.endsWith("Rule"))
			return simpleName.substring(0, simpleName.length() - 4);
		else
			return simpleName;
	}
	
	
}
