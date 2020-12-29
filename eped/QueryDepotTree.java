package eped;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import list.ListDynamic;
import interfaces.IteratorIF;
import interfaces.ListIF;
import interfaces.TreeIF;
import tree.TreeDynamic;

public class QueryDepotTree implements QueryDepot {

    private TreeIF<Object> nodes;
    
    private int numQueries;

    public QueryDepotTree() {

        nodes = new TreeDynamic<Object>(new Object());
    }
    
    public QueryDepotTree(String file) {
        
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
     * Devuelve el n�mero de consultas diferentes (sin contar repeticiones)
     * que hay almacenadas en el dep�sito
     * @returns el n�mero de consultas diferentes almacenadas
     */
    @Override
    public int numQueries() {
        
        return numQueries;
    }

    
    /**
     * Consulta la frecuencia de una consulta en el dep�sito
     * @returns la frecuencia de la consulta. Si no est�, devolver� 0
     * @param el texto de la consulta
     */
    @Override
    public int getFreqQuery(String q) {
        
        TreeIF<Object> aux = nodes;
        
        for (int i = 0; i < q.length(); i++)
            aux = find(aux, q.substring(i));
       
       /*     
        if (aux != null)
            return (int) getFreqTree(aux).getRoot();
       else return 0;
       */
      try {
          return (int) getFreqTree(aux).getRoot();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    
    /**
     * Dado un prefijo de consulta, devuelve una lista, ordenada por
     * frecuencias de mayor a menor, de todas las consultas almacenadas
     * en el dep�sito que comiencen por dicho prefijo
     * @returns la lista de consultas ordenada por frecuencias y orden
     * lexicogr�fico en caso de coincidencia de frecuencia
     * @param el prefijo
     */
    @Override
    public ListIF<Query> listOfQueries(String prefix) {
        
        TreeIF<Object> aux = nodes;
        for (int i = 0; i < prefix.length(); i++)
            aux = find(aux, prefix.substring(i));
        
        if (aux != null) {
            ListIF<Query> list = new ListDynamic<Query>();
             
            list = buildListOfQueries(aux, new String(), prefix, new ListDynamic<Query>());

            if (!list.isEmpty()) {
                list = list.sort(new ComparatorSuggestions());
            }
            return list;
            
        } else return null; 
    }
    
    /**
     * Incrementa en uno la frecuencia de una consulta en el dep�sito
     * Si la consulta no exist�a en la estructura, la deber� a�adir
     * @param el texto de la consulta
     */
    @Override
    public void incFreqQuery(String str) {
        
        if (str.length() > 0)
            findStringTree(nodes, str);
    }
    
    /**
     * Decrementa en uno la frecuencia de una consulta en el dep�sito
     * Si la frecuencia decrementada resultase ser 0, deber� eliminar
     * la informaci�n referente a la consulta del dep�sito
     * @precondici�n la consulta debe estar ya en el dep�sito
     * @param el texto de la consulta
     */
    @Override
    public void decFreqQuery(String q) {

        decFreqTree(nodes, q);

    }
    
    /**
     * Representaci�n de un �rbol de queries.
     * Utiliza el toString() de los �rboles din�micos donde se
     * ha elegido el recorrido en preorden para mostrar los nodos 
     * del �rbol.
     */
    @Override
    public String toString() {
        
        return nodes.toString();
    }
    
    
    /**
     * Construye una lista de queries que se utilizar� como salida del
     * m�todo listOfQueries.
     * 
     * @param tree �rbol hijo eliminando la ra�z com�n correspondiente al prefijo.
     * @param str
     * @param prefix prefijo de b�squeda
     * @param list lista con las sugerencias obtenidas.
     * @return
     */
    private ListIF<Query> buildListOfQueries (TreeIF<Object> tree, String str, String prefix, ListIF<Query> list) {

        if (tree.isLeaf()) {
            
            Query q = new Query(prefix.substring(0, prefix.length() - 1) + str);
            q.setFreq((int) tree.getRoot());
            list.insert(q);
        } else {
            
            IteratorIF<TreeIF<Object>> tIt = tree.getChildren().getIterator();
            while (tIt.hasNext()) buildListOfQueries(tIt.getNext(), str + tree.getRoot(), prefix, list);
        }
        return list;
    }
    

    /**
     * Busca recursivamente una cadena en un �rbol. Cuando deja de encontrarla
     * llama al m�todo buildTree para seguir construyendo el �rbol con la
     * subcadena restante
     * 
     * @param tree
     * @param str
     */
    private void findStringTree(TreeIF<Object> tree, String str) {
        
        Object object = new Object();
        object = str.charAt(0);
        if (childrenContains(tree, object)) {
            
            IteratorIF<TreeIF<Object>> it = tree.getChildren().getIterator();
            while (it.hasNext()) {
                
                TreeIF<Object> subArbol = it.getNext();
                if (str.length() > 1 && subArbol.getRoot().equals(object))
                    findStringTree(subArbol, str.substring(1));
                if (str.length() == 1 && subArbol.getRoot().equals(object)) {
                    incFreq(subArbol);
                }
            }

        } else
            buildTree(tree, str);
    }

    /**
     * Incrementa la frecuencia del nodo hoja.
     * Si no encuentra una hoja, delega en el m�todo buildTree
     * la construcci�n de un nuevo nodo hoja, con frecuencia 1.
     * @param tree El �rbol sobre el que se buscar� el nodo frecuencia.
     */
    private void incFreq(TreeIF<Object> tree) {
        
        IteratorIF<TreeIF<Object>> it = tree.getChildren().getIterator();
        boolean found = false;
        while (it.hasNext()) {
            TreeIF<Object> childTree = it.getNext();
            if (childTree.isLeaf() && (childTree.getRoot() instanceof Integer)) {
                
                Object nodeFreq = childTree.getRoot();
                nodeFreq = (int) nodeFreq + 1;
                childTree.setRoot(nodeFreq);
                found = true;
            }
        }
        if (!found) buildTree(tree, "");
    }

    /**
     * Busca en los hijos de un �rbol dado, el primer car�cter de la cadena
     * Si es encontrado, devuelve el hijo
     * En caso contrario, retorna null
     * @param tree
     * @param str
     * @return
     */
    private TreeIF<Object> find(TreeIF<Object> tree, String str) {
        
        if (tree != null && str != null) {
            Object object = new Object();
            object = str.charAt(0);
            IteratorIF<TreeIF<Object>> it = tree.getChildren().getIterator();
            while (it.hasNext()) {
                
                TreeIF<Object> subArbol = it.getNext();
                if (subArbol.getRoot().equals(object)) {
                    
                    return subArbol;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene el �rbol hoja que contiene la frecuencia.
     * 
     * @param �rbol con el �ltimo caracter de la cadena. Entre sus hijos tiene que
     * encontrarse uno que sea hoja y que sea una instancia de Integer
     * @return �rbol con el nodo de frecuencia como raiz del mismo.
     */
    private TreeIF<Object> getFreqTree(TreeIF<Object> tree) {
        
        ListIF<TreeIF<Object>> children = tree.getChildren();
        IteratorIF<TreeIF<Object>> it = children.getIterator();
        while (it.hasNext()) {
            
            TreeIF<Object> childTree = (TreeIF<Object>) it.getNext();
            if (childTree.isLeaf() && (childTree.getRoot() instanceof Integer)) {
                
                return childTree;
            }
        }
        return null;
    }


    /**
     * Construye un �rbol de consultas con la estructura indicada
     * en el enunciado con la cadena proporcionada. Si la cadena
     * tiene una longitud 0, crea �nicamente el nodo de la frecuencia
     * con el valor inicial 1.
     * 
     * @param tree el �rbol padre al que a�adir el resto.
     * @param str la cadena a insertar en el nuevo �rbol.
     * @return el �rbol con los nuevos nodos.
     */
    private TreeIF<Object> buildTree(TreeIF<Object> tree, String str) {
        
        Object object = new Object();
        if (str.length() > 0) {
            
            object = str.charAt(0);
            tree.addChild(buildTree(new TreeDynamic<Object>(object),
                    str.substring(1)));
        } else if (str.length() == 0) {
            
            object = 1;
            tree.addChild(new TreeDynamic<Object>(object));
            numQueries++;
        }
        return tree;
    }
    
    
    
    /**
     * Realiza la operaci�n de decrementar la frecuencia de una consulta.
     * Su funcionamiento se basa en ir recorriendo por completo la cadena
     * de la consulta y tratar de llegar al �rbol que contiene el �ltimo
     * car�cter. Una vez llegados, seg�n los requerimientos del enunciado
     * se eval�a si la frecuencia es mayor que 1 de modo que se resta 1 al
     * valor obtenido o, en caso contrario, se elimina toda la rama haciendo
     * uso de las variables auxiliares auxTree y numchild que indican que
     * �rbol e hijo de este, deben ser eliminados. 
     * @param tree el �rbol que contiene la consulta a decrementar.
     * @param str texto de la consulta a decrementar.
     */
    private void decFreqTree(TreeIF<Object> tree, String str) {
        
        TreeIF<Object> auxTree = new TreeDynamic<Object>();
        int numChild = 0;
        
        //Fase de recorrido de la cadena proporcionada.
        while (str.length() > 0 && (tree != null)) {
            if (hasMultipleChild(tree)) {
                
                auxTree = tree;
                numChild = getNumberChild(tree, str.charAt(0));
            }
            tree = find(tree, str);
            str = str.substring(1);
        }
        
        //Fase de decisi�n de restar frecuencia o eliminar rama.
        if ((tree != null) && (tree.getChildren().getLength() == 1)
         && (tree.getChildren().getFirst().isLeaf())) {
            Object obj = tree.getChildren().getFirst().getRoot();
            if ((obj instanceof Integer) && (int) obj > 1) 
                 tree.getChildren().getFirst().setRoot((int) obj - 1);
            else {
                auxTree.removeChild(numChild);
                numQueries--;
            }
        }
    }
    
    /**
     * Comprueba si un �rbol tiene m�s de un hijo
     * @param tree el �rbol a evaluar.
     * @return la comprobaci�n de si tiene varios hijos.
     */
    private boolean hasMultipleChild(TreeIF<Object> tree) {
        
        if (!(tree.isLeaf()) && tree.getChildren().getLength() > 1) return true;
        else return false;
    }
    
    
    /**
     * Obtiene el n�mero de hijo que corresponde con un car�cter.
     * @param tree El padre sobre el que realizar la b�squeda.
     * @param c El caracter a buscar.
     * @return El n�mero de hijo, -1 si no es encontrado.
     */
    private int getNumberChild(TreeIF<Object> tree, Character c) {
        
        if (tree != null && c != null) {
            int count = 0;
            Object object = new Object();
            object = c;
            IteratorIF<TreeIF<Object>> it = tree.getChildren().getIterator();
            while (it.hasNext()) {
                
                TreeIF<Object> subArbol = it.getNext();
                if (subArbol.getRoot().equals(object)) {
                    
                    return count;
                }
                count++;
            }
        }
        return -1;
    }
    
    /**
     * Devuelve verdadero o falso si el elemento a buscar est� entre
     * el primer nivel de hijos del �rbol.
     * 
     * @param tree El arbol donde buscar
     * @param element El objeto a buscar
     * @return el resultado de la b�squeda
     */
    private boolean childrenContains(TreeIF<Object> tree, Object element) {
        
        IteratorIF<TreeIF<Object>> childrenIt = tree.getChildren().getIterator();
        boolean found = false;
        while (!found && childrenIt.hasNext()) {
            TreeIF<Object> aChild = (TreeIF<Object>) childrenIt.getNext();
            found = aChild.getRoot().equals(element);
        }
        return found;
    }
    
}