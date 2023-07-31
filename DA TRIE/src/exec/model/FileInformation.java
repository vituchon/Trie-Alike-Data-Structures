/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exec.model;

/**
 *
 * @author Victor
 */
public class FileInformation
{
    private long keyWords;
    private long size;
    private long totalWords;

    public FileInformation (long totalWords,long keyWords,long size)
    {
        this.totalWords = totalWords;
        this.keyWords   = keyWords;
        this.size       = size;
    }

    /**
     * @return the keyWords
     */
    public long getKeyWords()
    {
        return keyWords;
    }

    /**
     * @return the size
     */
    public long getSize()
    {
        return size;
    }

    /**
     * @return the totalWords
     */
    public long getTotalWords()
    {
        return totalWords;
    }

    /**
     * @param keyWords the keyWords to set
     */
    void setKeyWords(long keyWords)
    {
        this.keyWords = keyWords;
    }

    /**
     * @param size the size to set
     */
    void setSize(long size)
    {
        this.size = size;
    }

    /**
     * @param totalWords the totalWords to set
     */
    void setTotalWords(long totalWords)
    {
        this.totalWords = totalWords;
    }
}
