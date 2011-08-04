/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.brill.events;

import static br.eti.rslemos.brill.events.BrillCustomMatchers.anyContext;
import static br.eti.rslemos.brill.events.BrillCustomMatchers.tokenExternallyEquals;
import static br.eti.rslemos.brill.events.BrillTaggerCustomMatchers.anyEvent;
import static br.eti.rslemos.brill.events.BrillTaggerCustomMatchers.isBrillTaggerEvent;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.eti.rslemos.brill.BrillTagger;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.brill.events.BrillTaggerCustomMatchers.BrillTaggerEventMatcher;
import br.eti.rslemos.tagger.DefaultSentence;
import br.eti.rslemos.tagger.Sentence;
import br.eti.rslemos.tagger.Token;

public class BrillTaggerListenerBehavior {
	@Mock private BrillTaggerListener listener;

	private BrillTagger tagger;;

	@Mock private Rule rule1;
	@Mock private Rule rule2;

	@Mock private Token token1;
	@Mock private Token token2;
	private Sentence sentence;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		// stubbing por causa do tokenExternallyEquals() 
		when(token1.getFeature(Token.WORD)).thenReturn("token1");
		when(token1.getFeature(Token.POS)).thenReturn(new Object());
		when(token2.getFeature(Token.WORD)).thenReturn("token2");
		when(token2.getFeature(Token.POS)).thenReturn(new Object());
		
		
		tagger = new BrillTagger();
		tagger.addBrillTaggerListener(listener);
		
		tagger.setRules(Arrays.asList(rule1, rule2));

		sentence = new DefaultSentence(Arrays.asList(token1, token2));
	}
	
	@Test
	public void shouldAcceptListeners() {
		//tagger.addBrillTaggerListener(listener);
		tagger.removeBrillTaggerListener(listener);
	}

	@Test
	public void shouldNotifyTaggingStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).taggingStart(argThat(isBasicBrillTaggerEvent()));
		order.verify(listener).taggingFinish(argThat(isBasicBrillTaggerEvent()));
	}

	@Test
	public void shouldNotifyRuleApplicationStartAndFinish() {
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).taggingStart(anyEvent());
		
		order.verify(listener).ruleApplicationStart(argThat(isBasicBrillTaggerEvent().underRuleOf(rule1)));
		order.verify(listener).ruleApplicationFinish(argThat(isBasicBrillTaggerEvent().underRuleOf(rule1)));
		
		order.verify(listener).ruleApplicationStart(argThat(isBasicBrillTaggerEvent().underRuleOf(rule2)));
		order.verify(listener).ruleApplicationFinish(argThat(isBasicBrillTaggerEvent().underRuleOf(rule2)));
		
		order.verify(listener).taggingFinish(anyEvent());
	}

	@Test
	public void shouldNotifyContextAdvanceAndCommit() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener);
		
		order.verify(listener).ruleApplicationStart(anyEvent());
		
		order.verify(listener).contextAdvanced(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()
					.withCurrentToken(is(tokenExternallyEquals(token1)))
					.inWhichRuleDidntApply()));
		
		order.verify(listener).contextAdvanced(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()
					.withCurrentToken(is(tokenExternallyEquals(token2)))
					.inWhichRuleDidntApply()));
		
		order.verify(listener).contextCommitted(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()));

		order.verify(listener).ruleApplicationFinish(anyEvent());
	}

	@Test
	public void shouldNotifyContextualRuleApplication() {
		// override ruleset
		tagger.setRules(Arrays.asList(rule1));
		
		tagger.tag(sentence);
		
		InOrder order = inOrder(listener, rule1);
		
		order.verify(listener).contextAdvanced(anyEvent());
		order.verify(rule1).matches(anyContext());
		order.verify(listener).ruleApplied(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()));

		order.verify(listener).contextAdvanced(anyEvent());
		order.verify(rule1).matches(anyContext());
		order.verify(listener).ruleApplied(argThat(
				isBasicBrillTaggerEvent()
					.underRuleOf(rule1)
					.atSomeContext()));
		
		order.verify(listener).contextCommitted(anyEvent());
		
		order.verify(listener, never()).ruleApplied(anyEvent());
		order.verify(rule1, never()).matches(anyContext());
	}

	@Test
	public void example() {
		listener = (BrillTaggerListener) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] {BrillTaggerListener.class}, new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.printf("%s\n\t%s\n", method.getName(), args[0]);
				
				return null;
			}
			
		});
		
		tagger.addBrillTaggerListener(listener);
		
		tagger.tag(sentence);
	}

	private BrillTaggerEventMatcher isBasicBrillTaggerEvent() {
		return isBrillTaggerEvent().from(tagger).onSentence(sentence);
	}
}
