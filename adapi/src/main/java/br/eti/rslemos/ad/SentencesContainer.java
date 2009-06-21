package br.eti.rslemos.ad;

import java.util.Iterator;

public abstract class SentencesContainer {

	private final Iterator<Sentence> sentences;

	public SentencesContainer(final ADCorpus corpus) {
		sentences = new Iterator<Sentence>() {
			private ADCorpus corpus0 = corpus;
			
			public boolean hasNext() {
				if (corpus0 == null)
					return false;

				if (corpus0.line.startsWith("<s")) {
					return true;
				} else {
					sentencesTail(corpus0);

					corpus0 = null;
					
					return false;
				}
			}

			public Sentence next() {
				return new Sentence(corpus0);
			}

			public void remove() {
			}
		};
	}

	public Iterator<Sentence> sentences() {
		return sentences;
	}

	protected abstract void sentencesTail(ADCorpus corpus);

	void readAll() {
		while(sentences.hasNext())
			sentences.next().readAll();
	}

}