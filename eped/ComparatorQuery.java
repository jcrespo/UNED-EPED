package eped;

import tads.ComparatorBase;

public class ComparatorQuery extends ComparatorBase<Query> {

	@Override
	public int compare(Query e1, Query e2) {

		int value = EQUAL;
		//Los valores LESS y GREATER están alterados ya que se ha
		//comprobado que sortMerge ordena al revés que sortInsert
		if (e1.getText().compareTo(e2.getText()) < 0)  value = GREATER;
		if (e1.getText().compareTo(e2.getText()) == 0) value = EQUAL;
		if (e1.getText().compareTo(e2.getText()) > 0)  value = LESS;
		
		return value;
	}
	
}