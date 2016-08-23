/**
 * The Word class is an implementation of a linked list that will contain every word occurrence.
 * Has methods for inserting, hashing, computing a hash code, and resizing when necessary.
 * It will also keep track of the total word count.
 */

public class Word
{
    private String lowerCase;
    private int wordCount;
    private Node head;
    private Node tail;

    /**
     * @param word  word to be added to the linked list
     */
    public void add(String word)
    {
        if (head == null)
        {
            head = new Node();
            lowerCase = word.toLowerCase();
            head.word = word;
            tail = head;
        }

        else
        {
            tail.next = new Node();
            tail = tail.next;
            tail.word = word;
        }

        ++wordCount;
    }

    /**
     * Search the linked list for the specified key (word).
     * If found, return true. Else, return false.
     *
     * @param key  the key being searched for
     * @return     true or false, indicating if it was found.
     */
    public boolean find(String key)
    {
        Node current = head;

        while (current != null)
        {
            if (current.word.equals(key)) return true;
            current = current.next;
        }

        return false;
    }

    /**
     * Formats the linked list like so: lowercase (occurrence, occurrence) - frequency
     * Example: cat (cat CAT CaT CAt) - 4
     *
     * @return  formatted string output
     */
    @Override
    public String toString()
    {
        Node current = head;
        String output = lowerCase;

        if (wordCount == 1)
        {
            if (lowerCase.equals(head.word))
            {
                output += " - " + wordCount;
                return output;
            }
        }

        output += " (";

        while (current != null)
        {
            output += current.word + " ";
            current = current.next;
        }

        output = output.trim();
        output += ")" + " - " + wordCount;
        return output;
    }

    public String getLowerCase()
    {
        return lowerCase;
    }

    public int getWordCount()
    {
        return wordCount;
    }

    public void incrementWordCount(int count)
    {
        wordCount += count;
    }
}

class Node
{
    String word;
    Node next;
}