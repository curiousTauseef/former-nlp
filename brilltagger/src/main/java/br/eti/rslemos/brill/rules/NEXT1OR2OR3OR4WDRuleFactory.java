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
package br.eti.rslemos.brill.rules;

import java.util.Arrays;
import java.util.Collection;

import br.eti.rslemos.brill.AbstractRule;
import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;
import br.eti.rslemos.tagger.Token;

public class NEXT1OR2OR3OR4WDRuleFactory extends AbstractRuleFactory {
	
	public static final NEXT1OR2OR3OR4WDRuleFactory INSTANCE = new NEXT1OR2OR3OR4WDRuleFactory();

	@Override
	public Collection<Rule> create(Object from, Object to, Context context) {
		String word1 = (String) context.getToken(1).get(Token.WORD);
		String word2 = (String) context.getToken(2).get(Token.WORD);
		String word3 = (String) context.getToken(3).get(Token.WORD);
		String word4 = (String) context.getToken(4).get(Token.WORD);

		return Arrays.<Rule> asList(
				createRule(from, to, word1), 
				createRule(from, to, word2),
				createRule(from, to, word3),
				createRule(from, to, word4)
		);
	}
	
	public Rule createRule(Object from, Object to, String next1or2or3or4Word) {
		return new NEXT1OR2OR3OR4WDRule(from, to, next1or2or3or4Word);
	}

	private static class NEXT1OR2OR3OR4WDRule extends AbstractRule {
		private final String next1or2or3or4Word;
	
		private NEXT1OR2OR3OR4WDRule(Object from, Object to, String next1or2or3or4Word) {
			super(from, to);
			
			this.next1or2or3or4Word = next1or2or3or4Word;
		}
	
		@Override
		public boolean matches(Context context) {
			return thisMatches(context) && super.matches(context);
		}
	
		private boolean thisMatches(Context context) {
			String word1 = (String) context.getToken(1).get(Token.WORD);
			String word2 = (String) context.getToken(2).get(Token.WORD);
			String word3 = (String) context.getToken(3).get(Token.WORD);
			String word4 = (String) context.getToken(4).get(Token.WORD);
			
			return next1or2or3or4Word != null 
			? (next1or2or3or4Word.equals(word1) | next1or2or3or4Word.equals(word2) | next1or2or3or4Word.equals(word3) | next1or2or3or4Word.equals(word4)) 
			: (word1 == null | word2 == null | word3 == null | word4 == null);
		}
	
		
		@Override
		public boolean equals(Object o) {
			if (!super.equals(o))
				return false;
			
			NEXT1OR2OR3OR4WDRule other = (NEXT1OR2OR3OR4WDRule) o;
			
			return next1or2or3or4Word != null ? next1or2or3or4Word.equals(other.next1or2or3or4Word) : other.next1or2or3or4Word == null;
		}
	
		@Override
		public int hashCode() {
			int hashCode = super.hashCode();
			
			hashCode *= 67;
			hashCode += next1or2or3or4Word != null ? next1or2or3or4Word.hashCode() : 0;
			
			return hashCode;
		}
	
		@Override
		public String toBrillString() {
			return super.toBrillString() + " " + next1or2or3or4Word;
		}
	}
}