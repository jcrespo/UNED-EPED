Pr�ctica EPED 2015.

Francisco Javier Crespo Jim�nez. Agosto 2015.

Para ejecutar los archivos de prueba del equipo docente ejecutar el m�todo main de la clase Eped2015
pasando como par�metros respectivamente para lista o �rbol:

{"L", "JdP-consultas.txt", "JdP-operaciones.txt" }
{"T", "JdP-consultas.txt", "JdP-operaciones.txt" }

En caso de probar con los archivos proporcionados para el estudio emp�rico,
siguen la siguiente nomenclatura para su nombrado:

La primera letra es 'c' u 'o' dependiendo de si el contenido del archivo son las consultas en si mismas
o las operaciones a realizar en el dep�sito.

Los siguientes caracteres, es el tama�o del dep�sito. Si la cantidad tiene el sufijo 'k' 
se refiere a miles de consultas. �ste varia desde una �nica consulta hasta las 20.000.

Existe un archivo de operaciones llamado opeor.txt que busca el caso peor y obtener con el 
m�todo listOfQueries todo el dep�sito de consultas.

Los archivos tienen extensi�n .txt

De este modo el archivo de consultas m�s peque�o ser� c1.txt y el mayor c20k.txt; los respectivos 
archivos de operaciones son nombrados como: o1.txt y o20k.txt respectivamente.

Ejemplo BlueJ:

{"L", "c1k.txt", "o1k.txt" }

O bien desde la l�nea de comandos usando el .jar compilado:

java -jar eped2015.jar T c1k.txt o1k.txt