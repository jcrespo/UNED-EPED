package eped;
import interfaces.*;

public interface QueryDepot {
	
	/* Devuelve el n�mero de consultas diferentes (sin contar repeticiones) */
	/* que hay almacenadas en el dep�sito */
	/* @returns el n�mero de consultas diferentes almacenadas */
	public int numQueries();

	/* Consulta la frecuencia de una consulta en el dep�sito */
	/* @returns la frecuencia de la consulta. Si no est�, devolver� 0 */
	/* @param el texto de la consulta */
	public int getFreqQuery(String q);

	/* Dado un prefijo de consulta, devuelve una lista, ordenada por */
	/* frecuencias de mayor a menor, de todas las consultas almacenadas */
	/* en el dep�sito que comiencen por dicho prefijo */
	/* @returns la lista de consultas ordenada por frecuencias y orden */
	/* lexicogr�fico en caso de coincidencia de frecuencia */
	/* @param el prefijo */
	public ListIF<Query> listOfQueries(String prefix);

	/* Incrementa en uno la frecuencia de una consulta en el dep�sito */
	/* Si la consulta no exist�a en la estructura, la deber� a�adir */
	/* @param el texto de la consulta */
	public void incFreqQuery(String q);

	/* Decrementa en uno la frecuencia de una consulta en el dep�sito */
	/* Si la frecuencia decrementada resultase ser 0, deber� eliminar */
	/* la informaci�n referente a la consulta del dep�sito */
	/* @precondici�n la consulta debe estar ya en el dep�sito */
	/* @param el texto de la consulta */
	public void decFreqQuery(String q);
	
}