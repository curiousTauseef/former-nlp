package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.*;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT2TAGRuleBehavior {
	private boolean matches(String next2Tag) {
		Context<String> context = buildContext();
		
		Rule<String> rule = new NEXT2TAGRule<String>(THIS_TAG, TO_TAG, next2Tag);
		return rule.matches(context);
	}

	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT1_TAG));
		assertFalse(matches(NEXT3_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT2_TAG));
	}

	@Test
	public void shouldDependOnFromTag() {
		createAndTestBasicDependency(NEXT2TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldDependOnContextTag() {
		testDependency(new NEXT2TAGRule<String>(THIS_TAG, THIS_TAG, NEXT2_TAG), F, F, F, F, F, T, F, F);
	}
	
	@Test
	public void shouldCreateRule() {
		createAndTestMatchability(NEXT2TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		createAndTestObjectSemantics(NEXT2TAGRule.<String>FACTORY());
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		createAndTestBrillText(NEXT2TAGRule.<String>FACTORY(), 
				THIS_TAG + " " + TO_TAG + " NEXT2TAG " + NEXT2_TAG);
	}
}
