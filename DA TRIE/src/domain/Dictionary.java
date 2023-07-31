/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author Victor
 */
public interface Dictionary
{
    /**
     * Borra una palabra en este trie, si existía previamente.
     *
     * @param str Palabra a insertar
     * @return  true si fue borrado, sino retorna false.
     */
    boolean delete(String str);

    /**
     * Verifica si existe una palabra en este trie
     *
     * @param str Palabra a buscar
     * @return true si existe, false sino
     */
    boolean exists(String str);

    /**
     * Devuelve todos las cadenas insertadas.
     *
     * @return Arreglo de cadenas insertadas
     */
    String[] getStrings();

    /**
     * Inserta una palabra en este trie, si no existe previamente.
     *
     * @param str Palabra a insertar
     * @return true si fue insertado, sino retorna false.
     */
    boolean insert(String str);

    /**
     * Calcula en forma aproximada el espacio minimo necesario en bytes de la estructura con los datos actuales
     */
    long optimalSpace();

    /**
     * Calcula en forma aproximada el espacio ocupado en bytes de la estructura
     */
    long space();
}
