/*
 ** TrieList.java
 ** Login : <speedblue@happycoders.org>
 ** Started on  Wed Nov  1 19:34:21 2006 Julien Lemoine
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
 ** You should have received a copy of the GNU General Public License
 ** along with this program; if not, write to the Free Software
 ** Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package domain.TrieList;

import domain.Dictionary;

/**
 * @brief This class implement a trie in memory with the smallest memory 
 * structure as possible. This string associate an integer to a string and 
 * can be use very easilly with insert and getEntry method
 */
public class TrieList implements Dictionary
{
    /// factory to create nodes

    private TrieNodeFactory factory;

    /**
     * @brief create the trie with a specific number of node at the
     * begining.
     */
    public TrieList(int initialNbNodes)
    {
        factory = new TrieNodeFactory(initialNbNodes);
        factory.newNode(); // initial node
    }

    public TrieList()
    {
        this(2000);
    }

    /**
     * @brief add an String entry in trie. Value must be different than -1
     * (-1 is reserved when there is no match found)
     */
    public boolean insert (String str, int value)
    {
        boolean inserted = true;
        try
        {
            int currentNode = _addEntry(str);

            // Set the value on the last node
            if (currentNode >= 0 && getNodeValue(currentNode) != -1)
            {
                inserted = false;
            }
            else
            {
                _setNodeValue(currentNode, value);
            }
        }
        catch (Exception ex)
        {
            inserted = false;
        }

        return inserted;
    }

    /**
     * @brief set an String entry in trie. Value must be different than -1
     * (-1 is reserved when there is no match found)
     */
    public void setEntry(String str, int value) throws Exception
    {
        int currentNode = _addEntry(str);

        // Set the value on the last node
        _setNodeValue(currentNode, value);
    }

    /**
     * @brief get integer associef to a string, return -1 if entry is not found.
     */
    public int getEntry(String str)
    {
        int currentNode = 0, strPos = 0;
        boolean found = true;

        // Look for the part of the word which is in TrieList
        while (found && strPos < str.length())
        {
            found = false;
            currentNode = _lookingSubNode(currentNode, str.charAt(strPos));
            if (currentNode >= 0)
            {
                found = true;
                ++strPos;
            }
        }

        if (currentNode > 0 && strPos == str.length()) // The word is complet in the automaton
        {
            return getNodeValue(currentNode);
        }
        return -1;
    }

    public long space()
    {
        long bytes = factory.space();
        return bytes;
    }

    public long optimalSpace()
    {
        return factory.optimalSpace();
    }

    /**
     * display the whole content of trie
     */
    public void display()
    {
        _display(0, (char) 0, -2);
    }

    /**
     * accessor to iterator over the trie. the initial node is 0.
     * return -1 when there is no sub node
     */
    public int getFirstSubNode(int node)
    {
        return factory.getFirstSubNode(node);
    }

    /**
     * accessor to iterator over the trie. the initial node is 0.
     */
    public char getFirstSubNodeLabel(int node)
    {
        return factory.getFirstSubNodeLabel(node);
    }

    /**
     * accessor to iterator over the trie. the initial node is 0.
     * return -1 when there is no sub node
     */
    public int getBrother(int node)
    {
        return factory.getBrother(node);
    }

    /**
     * accessor to iterator over the trie. the initial node is 0.
     */
    public char getBrotherLabel(int node)
    {
        return factory.getBrotherLabel(node);
    }

    /**
     * accessor to iterator over the trie. the initial node is 0
     */
    public int getNodeValue(int node)
    {
        return factory.getValue(node);
    }

    /**
     * display content of a node and his sub nodes
     */
    protected void _display(int node, char label, int offset)
    {
        int firstSubNode = factory.getFirstSubNode(node);
        char firstSubNodeLabel = factory.getFirstSubNodeLabel(node);
        int brother = factory.getBrother(node);
        char brotherLabel = factory.getBrotherLabel(node);

        if (label != 0)
        {
            for (int i = 0; i < offset; ++i)
            {
                System.out.print(" ");
            }
            System.out.println("label[" + label + "]");
        }
        if (firstSubNode >= 0)
        {
            _display(firstSubNode, firstSubNodeLabel, offset + 2);
        }
        if (brother >= 0)
        {
            _display(brother, brotherLabel, offset);
        }
    }

    /**
     * add an element in sub nodes
     */
    protected void _addSubNode(int node, char chr, int newNode)
    {
        int firstSubNode = factory.getFirstSubNode(node);
        char firstSubNodeLabel = factory.getFirstSubNodeLabel(node);

        if (firstSubNode < 0 || firstSubNodeLabel > chr)
        {
            factory.setBrother(newNode, firstSubNode, firstSubNodeLabel);
            factory.setFirstSubNode(node, newNode, chr);
        }
        else
        {
            _addBrother(firstSubNode, chr, newNode);
        }
    }

    /**
     * add an element in list of brother
     */
    protected void _addBrother(int node, char chr, int newNode)
    {
        int brother = factory.getBrother(node);
        char brotherLabel = factory.getBrotherLabel(node);

        if (brother < 0 || brotherLabel > chr)
        {
            factory.setBrother(newNode, brother, brotherLabel);
            factory.setBrother(node, newNode, chr);
        }
        else
        {
            _addBrother(brother, chr, newNode);
        }
    }

    /**
     * Looking for a sub node of node accessible with chr
     */
    protected int _lookingSubNode(int node, char chr)
    {
        int firstSubNode = factory.getFirstSubNode(node);
        char firstSubNodeLabel = factory.getFirstSubNodeLabel(node);

        if (firstSubNode >= 0 && firstSubNodeLabel <= chr)
        {
            if (firstSubNodeLabel == chr)
            {
                return firstSubNode;
            }
            return _lookingBrother(firstSubNode, chr);
        }
        return -1;
    }

    /**
     * Looking for a node accessible with chr in list of brother
     * of node
     */
    protected int _lookingBrother(int node, char chr)
    {
        int brother = factory.getBrother(node);
        char brotherLabel = factory.getBrotherLabel(node);

        if (brother >= 0 && brotherLabel <= chr)
        {
            if (brotherLabel == chr)
            {
                return brother;
            }
            return _lookingBrother(brother, chr);
        }
        return -1;
    }

    /// set value of node
    protected void _setNodeValue(int node, int value)
    {
        factory.setValue(node, value);
    }

    /**
     * @brief add an String entry in trie.
     */
    protected int _addEntry(String str) throws Exception
    {
        int currentNode = 0, previousNode = 0, strPos = 0;
        boolean found = true;

        // Look for the part of the word which is in TrieList
        while (found && strPos < str.length())
        {
            found = false;
            previousNode = currentNode;
            currentNode = _lookingSubNode(currentNode, str.charAt(strPos));
            if (currentNode >= 0)
            {
                found = true;
                ++strPos;
            }
        }
        // Add part of the word which is not in TrieList
        if (currentNode < 0 || strPos != str.length())
        {
            currentNode = previousNode;
            while (strPos < str.length())
            {
                int newNode = factory.newNode();
                _addSubNode(currentNode, str.charAt(strPos), newNode);
                currentNode = newNode;
                ++strPos;
            }
        }
        if (currentNode < 0)
        {
            throw new Exception("Buggy Trie");
        }
        return currentNode;
    }

    public boolean delete(String str)
    {
        return false;
    }

    public boolean exists(String str)
    {
        return this.getEntry(str) != -1;
    }

    public String[] getStrings()
    {
        return null;
    }

    public boolean insert(String str)
    {
        return this.insert (str,0);
    }
}
