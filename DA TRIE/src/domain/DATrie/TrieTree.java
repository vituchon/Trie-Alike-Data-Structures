/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.DATrie;

import domain.Dictionary;

/**
 *
 * @author Victor
 */
public interface TrieTree extends Dictionary
{
    public String list_nodes ();
    @Override
    String toString();

}
