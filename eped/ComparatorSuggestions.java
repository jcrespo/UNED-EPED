package eped;

import interfaces.ComparatorIF;
import tads.ComparatorBase;

public class ComparatorSuggestions extends ComparatorBase<Query> {

	@Override
	public int compare(Query e1, Query e2) {

		int value = EQUAL;
		
		//Los valores LESS y GREATER est�n alterados ya que se ha
		//comprobado que sortMerge ordena al rev�s que sortInsert
		if (e1.getFreq() < e2.getFreq())  value = LESS;
		if (e1.getFreq() == e2.getFreq()) { 
			//En caso de igualdad de frecuencias, comprobar el orden lexicogr�fico.
			ComparatorIF<Query> comparator = new ComparatorQuery();
			return comparator.compare(e1, e2);
		}
		if (e1.getFreq() > e2.getFreq())  value = GREATER;
		
		return value;
	}
	
}