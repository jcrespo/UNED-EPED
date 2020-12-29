Práctica EPED 2015.

Francisco Javier Crespo Jiménez. Agosto 2015.

Para ejecutar los archivos de prueba del equipo docente ejecutar el método main de la clase Eped2015
pasando como parámetros respectivamente para lista o árbol:

{"L", "JdP-consultas.txt", "JdP-operaciones.txt" }
{"T", "JdP-consultas.txt", "JdP-operaciones.txt" }

En caso de probar con los archivos proporcionados para el estudio empírico,
siguen la siguiente nomenclatura para su nombrado:

La primera letra es 'c' u 'o' dependiendo de si el contenido del archivo son las consultas en si mismas
o las operaciones a realizar en el depósito.

Los siguientes caracteres, es el tamaño del depósito. Si la cantidad tiene el sufijo 'k' 
se refiere a miles de consultas. Éste varia desde una única consulta hasta las 20.000.

Existe un archivo de operaciones llamado opeor.txt que busca el caso peor y obtener con el 
método listOfQueries todo el depósito de consultas.

Los archivos tienen extensión .txt

De este modo el archivo de consultas más pequeño será c1.txt y el mayor c20k.txt; los respectivos 
archivos de operaciones son nombrados como: o1.txt y o20k.txt respectivamente.

Ejemplo BlueJ:

{"L", "c1k.txt", "o1k.txt" }

O bien desde la línea de comandos usando el .jar compilado:

java -jar eped2015.jar T c1k.txt o1k.txt