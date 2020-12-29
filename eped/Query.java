package eped;
public class Query {
	
	private String 	text;
	private int 	freq;
	
	/* Construye una nueva query con el texto pasado como parámetro */
	public Query(String text) {
		this.text = text;
	}

	/* Modifica la frecuencia de la query */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/* Devuelve el texto de una query */
	public String getText() {
		return text;
	}

	/* Devuelve la frecuencia de una query */
	public int getFreq() {
		return freq;
	}
	
	//Eliminar
	public String toString() {
		return (text + " (" + freq + ")");
	}
	
}