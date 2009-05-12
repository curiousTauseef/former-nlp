package br.eti.rslemos.brill;

public interface RuleFactory<R extends Rule> {
	public R create(Context context, Token target) throws RuleCreationException;
}
