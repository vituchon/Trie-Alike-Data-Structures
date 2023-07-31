/*
** TrieNodeFactory.java
** Login : <speedblue@happycoders.org>
** Started on  Wed Nov  1 16:36:48 2006 Julien Lemoine
** $Id$
** 
** Copyright (C) 2006 Julien Lemoine
** This program is free software; you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation; either version 2 of the License, or
** (at your option) any later version.
** 
** This program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
** 
~** You should have received a copy of the GNU General Public License
** along with this program; if not, write to the Free Software
** Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package domain.TrieList;

/**
 * @brief this class represent The creation of trie Node.
 * Each node contains 5 elements :
 * <ul>
 * <li>a link to his first subnode with a label (firstSubNode and firstSubNodeLabel)</li>
 * <li>a link to his brother with a label (brother and brotherLabel), brother is a
 * node which has the same father as current node</li>
 * <li>a integer value</li>
 * </ul>
 * Append of trie node by Factory is done in amortized constant time. Since 
 * this will sometimes cause a memory reallocation, this implementation 
 * allocate twice as much memory every time the vector is resized.
 */
public class TrieNodeFactory
{
  /// store the link to the first subnode for each node
  private int firstSubNode[];
  /// store the label to go to first subnode for each node
  private char firstSubNodeLabel[];
  /// store the link to a brother
  private int brother[];
  /// store the label to go to brother
  private char brotherLabel[];
  /// store the value associed to each trie node
  private int values[];
  /// store the number of node allocated
  private int size;
  /// store the number of node used
  private int nbNodes;

  /**
   * @brief create an instance of this factory and start allocating 
   * nbNodeToAllocate nodes
   */
  public TrieNodeFactory(int nbNodeToAllocate)
  {
    size = nbNodeToAllocate;
    firstSubNode = new int[size];
    firstSubNodeLabel = new char[size];
    brother = new int[size];
    brotherLabel = new char[size];
    values = new int[size];
    nbNodes = 0;
  }

  public long space ()
  {
      // tres arreglos de enteros, dos arreglos de chars
      return this.values.length * 12 + this.brotherLabel.length * 2;
  }
  
  public long optimalSpace ()
  {
      return this.nbNodes * 12 + this.nbNodes * 2;
  }  
  
  /**
   * @brief resize a array of int. existing elements are copied using 
   * System.arraycopy().
   */
  protected int[] resizeIntArray(int orig[], int newSize)
  {
    int newIntArray[] = new int[newSize];
    System.arraycopy(orig, 0, newIntArray, 0, orig.length);
    return newIntArray;
  }

  /**
   * @brief resize a array of char. existing elements are copied using 
   * System.arraycopy().
   */
  protected char[] resizeCharArray(char orig[], int newSize)
  {
    char newCharArray[] = new char[newSize];
    System.arraycopy(orig, 0, newCharArray, 0, orig.length);
    return newCharArray;
  }

  /**
   * @brief allocate a new node in trie and return the index used. If there
   * is no allocated node available, existing arrays with twice as much size as
   * previous size. return value are between range [1..n], 0 is reserved 
   * for sentinel.
   */
  public int newNode()
  {
    if (nbNodes == size)
      {
	size *= 1.5;
	// resize all existing array
	firstSubNode = resizeIntArray(firstSubNode, size);
	firstSubNodeLabel = resizeCharArray(firstSubNodeLabel, size);
	brother = resizeIntArray(brother, size * 2);
	brotherLabel = resizeCharArray(brotherLabel, size);
	values = resizeIntArray(values, size);
      }
    /// initial new node
    firstSubNode[nbNodes] = -1;
    brother[nbNodes] = -1;
    values[nbNodes] = -1;
    ++nbNodes;
    return nbNodes - 1;
  }
  
  /**
   * @brief return the first sub node for a specific node. Node must be
   * in the range [0..n-1], the return value is into range [0..n-1] and is
   * equal to -1 if there is no subnode.
   */
  public int getFirstSubNode(int node) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    return firstSubNode[node];
  }

  /**
   * @brief return the label for first sub node for a specific node. Node must be
   * in the range [0..n-1].
   */
  public char getFirstSubNodeLabel(int node) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    return firstSubNodeLabel[node];
  }

  /**
   * @brief return the first brother for a specific node. Node must be
   * in the range [0..n-1], the return value is into range [0..n-1] and is
   * equal to -1 if there is no subnode.
   */
  public int getBrother(int node) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    return brother[node];
  }

  /**
   * @brief return the label for brother for a specific node. Node must be
   * in the range [0..n-1].
   */
  public char getBrotherLabel(int node) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    return brotherLabel[node];
  }

  /**
   * @brief return the value for a specific node. Node must be
   * in the range [0..n-1].
   */
  public int getValue(int node) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    return values[node];
  }

  /**
   * @brief set the value for a specific node. Node must be
   * in the range [0..n-1].
   */
  public void setValue(int node, int value) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    values[node] = value;
  }

  /**
   * @brief set the value of first sub node for a specific node. Node must be
   * in the range [0..n-1].
   */
  public void setFirstSubNode(int node, int dest, char destLabel) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    firstSubNode[node] = dest;
    firstSubNodeLabel[node] = destLabel;
  }

  /**
   * @brief set the value of brother for a specific node. Node must be
   * in the range [0..n-1].
   */
  public void setBrother(int node, int dest, char destLabel) throws IndexOutOfBoundsException
  {
    if (node < 0 || node >= nbNodes)
      throw new IndexOutOfBoundsException("invalid index for first sub node accessor");
    brother[node] = dest;
    brotherLabel[node] = destLabel;
  }

}
