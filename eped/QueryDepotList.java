package eped;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import interfaces.ComparatorIF;
import interfaces.IteratorIF;
import interfaces.ListIF;
import list.ListDynamic;

public class QueryDepotList implements QueryDepot {

    private ListIF<Query> queries;
    
    private int numQueries;

    public QueryDepotList() {

        queries = new ListDynamic<Query>();
    }
    
    public QueryDepotList(String file) {
        
        this();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            
            while (line != null) {
                incFreqQuery(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error de E/S.");
        }
    }

    /**
     * Devuelve el número de consultas diferentes (sin contar repeticiones) que
     * hay almacenadas en el depósito
     * 
     * @returns el número de consultas diferentes almacenadas
     */
    @Override
    public int numQueries() {

        return numQueries;
    }
    
    //Decartado por alto coste.
    /*
    public int numQueries() {

        return queries.getLength();
    }
    */

    /**
     * Consulta la frecuencia de una consulta en el depósito
     * 
     * @returns la frecuencia de la consulta. Si no existe, devolverá 0
     * @param el texto de la consulta
     */
    @Override
    public int getFreqQuery(String q) {
        
        Query newQ = new Query(q);
        Query existQ = findQueryText(newQ);
        
        if (existQ != null) return existQ.getFreq();
        else return 0;
    }

    /**
     * Dado un prefijo de consulta, devuelve una lista, ordenada por frecuencias
     * de mayor a menor, de todas las consultas almacenadas en el depósito que
     * comiencen por dicho prefijo
     * 
     * @returns la lista de consultas ordenada por frecuencias y orden
     * lexicográfico en caso de coincidencia de frecuencia
     * 
     * @param el prefijo
     */
    @Override
    public ListIF<Query> listOfQueries(String prefix) {
        
        ListIF<Query> list = new ListDynamic<Query>();
        IteratorIF<Query> it = queries.getIterator();
        Query element = null;
        while (it.hasNext()) {
            element = it.getNext();
            if (element.getText().startsWith(prefix)) list.insert(element);
        }
        
        if (!list.isEmpty()) list = list.sort(new ComparatorSuggestions());
                
        return list;
    }

    /**
     * Incrementa en uno la frecuencia de una consulta en el depósito Si la
     * consulta no existía en la estructura, la deberá añadir
     * 
     * @param el texto de la consulta
     */
    @Override   
    public void incFreqQuery(String q) {
        
        if (q.length() > 0) {

            Query newQuery      = new Query(q);
            Query existQuery    = findQueryText(newQuery);
    
            if (existQuery != null) existQuery.setFreq(existQuery.getFreq() + 1);
            else {
                newQuery.setFreq(1);
                queries = sortInsert(queries, newQuery, new ComparatorQuery());
                numQueries++;
            }
        }
    }
    
    /**
     * Decrementa en uno la frecuencia de una consulta en el depósito Si la
     * frecuencia decrementada resultase ser 0, deberá eliminar la información
     * referente a la consulta del depósito
     * 
     * @precondición la consulta debe estar ya en el depósito
     * 
     * @param el texto de la consulta
     */
    @Override
    public void decFreqQuery(String q) {
        
        Query newQuery      = new Query(q);
        Query existQuery    = findQueryText(newQuery);
        if (existQuery != null) { //Cumple la precondición
            if (existQuery.getFreq() == 1) {
                remove(queries, existQuery);
                numQueries--;
            }
            else {
                existQuery.setFreq(existQuery.getFreq() - 1);
            }
        } 
    }


    /**
     * 
     * @param query a buscar.
     * @return la query buscada o null si no existe en el depósito.
     */
    
    private Query findQueryText(Query q) {

        IteratorIF<Query> it = queries.getIterator();
        Query element = null;
        while (it.hasNext()) {
            element = it.getNext();
            if (element.getText().equals(q.getText()))
                return element;
        }
        return null;
    }

    
    /**
     * Elimina la query de la lista pasado como parámetro.
     * @param la lista sobre la que eliminar la query.
     * @param query a eliminar.
     * @return la lista sin la query o la misma lista si no existiese.
     */
    private ListIF<Query> remove(ListIF<Query> list, Query q) {
        
        if (list.isEmpty()) return list;
        else if (list.getFirst().equals(q)) return list.getTail();
        else return remove (list.getTail(), q).insert(list.getFirst());
    }
    
    /**
     * Realiza una inserción ordenada en la lista.
     * @param queries el depósito de consultas.
     * @param element el elemento a introducir.
     * @param comparator un comparador de queries.
     * @return la lista con el elemento insertado en su orden.
     */
    private ListIF<Query> sortInsert(ListIF<Query> queries, Query element, ComparatorIF<Query> comparator) {
        
        if (queries.isEmpty())
            return queries.insert(element);
        else if (comparator.isLess(element, queries.getFirst()))
            return queries.insert(element);
        else
            return ((ListDynamic<Query>) sortInsert(queries.getTail(), element, comparator)
                    .insert(queries.getFirst()));
    }
    
    /**
     * Representación de una lista de queries.
     */
    public String toString() {
        
        StringBuffer buff = new StringBuffer();
        buff.append("Queries [");
        IteratorIF<Query> listIt = queries.getIterator();
        while (listIt.hasNext()) {
            Query element = listIt.getNext();
            buff.append(element.toString());
            if (listIt.hasNext()) buff.append(", ");
        }

        buff.append("]");
        return buff.toString();

    }

}
