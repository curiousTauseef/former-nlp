/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Natural Language Processing"
 * Copyright 2011  Rodrigo Lemos
 * Copyright 2013  Rodrigo Lemos
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

import java.util.Collection;
import java.util.Collections;

import br.eti.rslemos.brill.Context;
import br.eti.rslemos.brill.Rule;

public abstract class AbstractSingleRuleFactory<R extends Rule> extends AbstractRuleFactory<R> {

	@Override
	protected Collection<R> create(Object from, Object to, Context context) {
		return Collections.singleton(createRule(from, to, context));
	}

	protected abstract R createRule(Object from, Object to, Context context);

}
