package br.eti.rslemos.brill.rules;

import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.F;
import static br.eti.rslemos.brill.rules.RuleBehaviorUtils.T;
import static br.eti.rslemos.brill.rules.RuleContextMother.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public class NEXT1OR2OR3TAGRuleBehavior {
	private boolean matches(String next1or2or3Tag) {
		Context context = buildContext();
		
		Rule rule = new NEXT1OR2OR3TAGRule(THIS_TAG, TO_TAG, next1or2or3Tag);
		return rule.matches(context);
	}
	
	@Test
	public void shouldNotFire() {
		assertFalse(matches(PREV4_TAG));
		assertFalse(matches(PREV3_TAG));
		assertFalse(matches(PREV2_TAG));
		assertFalse(matches(PREV1_TAG));
		assertFalse(matches(THIS_TAG));
		assertFalse(matches(NEXT4_TAG));
	}

	@Test
	public void shouldFire() {
		assertTrue(matches(NEXT1_TAG));
		assertTrue(matches(NEXT2_TAG));
		assertTrue(matches(NEXT3_TAG));
	}

	@Test
	public void shouldDependOnFromTag() {
		RuleBehaviorUtils.createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY1);
		RuleBehaviorUtils.createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY2);
		RuleBehaviorUtils.createAndTestBasicDependency(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldDependOnContextTag() {
		RuleBehaviorUtils.createAndTestContextDependency(NEXT1OR2OR3TAGRule.FACTORY1, F, F, F, F, T, F, F, F);
		RuleBehaviorUtils.createAndTestContextDependency(NEXT1OR2OR3TAGRule.FACTORY2, F, F, F, F, F, T, F, F);
		RuleBehaviorUtils.createAndTestContextDependency(NEXT1OR2OR3TAGRule.FACTORY3, F, F, F, F, F, F, T, F);
	}
	
	@Test
	public void shouldCreateRule() {
		RuleBehaviorUtils.createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY1);
		RuleBehaviorUtils.createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY2);
		RuleBehaviorUtils.createAndTestMatchability(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldHaveObjectSemantics() {
		RuleBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY1);
		RuleBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY2);
		RuleBehaviorUtils.createAndTestObjectSemantics(NEXT1OR2OR3TAGRule.FACTORY3);
	}
	
	@Test
	public void shouldBeSerializableAsBrillText() {
		RuleBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY1, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT1_TAG);
		RuleBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY2, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT2_TAG);
		RuleBehaviorUtils.createAndTestBrillText(NEXT1OR2OR3TAGRule.FACTORY3, 
				THIS_TAG + " " + TO_TAG + " NEXT1OR2OR3TAG " + NEXT3_TAG);
	}
}
