/**
 * The HashTable class has a dynamic array that will contain Word objects. There are methods
 * for inserting, hashing, computing a hash code, and resizing when necessary. It will also
 * keep track of the total word count.
 */

public class HashTable
{
    private Word[] table;
    private int size;
    private int wordCount;

    public HashTable()
    {
        table = new Word[1];
        size = 0;
    }

    /**
     * insert method for hashing words from the text file.
     * We attempt to resize our hash table before inserting a word.
     *
     * @param word  word to be hashed and inserted
     */
    public void insert(String word)
    {
        int index = 0;
        resizeCheck(size + 1);

        for (int i = 0; i < table.length; i++)
        {
            index = (hash(word) + (i * i)) % table.length;

            // If index is already occupied...
            if (table[index] != null)
            {
                // If we're attempting to insert a word that already has been inserted...
                if (table[index].getLowerCase().equals(word.toLowerCase()))
                {
                    // If the word occurrence hasn't been accounted for...
                    if (!table[index].find(word))
                    {
                        // Add the word to our Word linked list and return.
                        table[index].add(word);
                        return;
                    }
                }
            }
            else
            {
                Word wordList = new Word();
                wordList.add(word);
                table[index] = wordList;

                ++size;
                return;
            }
        }

        System.out.println("Failed to insert value into hash table");
        System.exit(1);
    }

    /**
     * insert method used for rehashing. We are inserting Word objects here, not strings.
     *
     * @param word  Word object to be hashed (by its lowercase) and inserted.
     */
    public void insert(Word word)
    {
        int index;

        for (int i = 0; i < table.length; i++)
        {
            index = (hash(word.getLowerCase()) + (i * i)) % table.length;

            // Index is null, insert word.
            if (table[index] == null)
            {
                table[index] = word;
                wordCount++;
            }

            // Index isn't null.
            // If the two words don't match, probe once again.
            // If the two words do match, increment the word count of the word object already in place.
            else
            {
                boolean wordMatch = table[index].getLowerCase().equals(word.getLowerCase());
                if (!wordMatch) continue;
                else table[index].incrementWordCount(word.getWordCount());
            }

            return;
        }

        System.out.println("Failed to insert value into hash table");
        System.exit(1);
    }

    /**
     * This hash method uses the multiplication method found in the CLRS book, page 263-264.
     * It'll calculate us an index to be used for inserting the word we're hashing.
     *
     * @param word  the word to be hashed
     * @return      the calculated index for insertion
     */
    private int hash(String word)
    {
        int hashCode = computeHashCode(word);
        double goldenRatio = (1 + Math.sqrt(5)) / 2; // I think 20 decimal places is plenty
        int index = (int) Math.floor(Math.abs(table.length * ((hashCode * goldenRatio) % 1)));

        return index;
    }

    /**
     * finalHash() is called before displaying our output. Resizing can cause our hash function to
     * return a wider range of (null) indexes, causing us to insert a word that's already been
     * accounted for. Now that we have our final size, we can rehash and eliminate any duplicates.
     */
    public void finalHash()
    {
        wordCount = 0;
        Word[] temp = table;
        table = new Word[table.length];

        for (int i = 0; i < temp.length; i++)
        {
            if (temp[i] != null)
            {
                insert(temp[i]);
            }
        }
    }

    /**
     * Method for computing the hash code that we will use in our multiplication method
     * to calculate an index. We use the lower case of the word to cause same-word collisions.
     *
     * Similar to the built-in hashCode() method, however I used 37 instead of 31.
     * I believe any prime number will do - it simply ensures that the computed hash
     * code will be unique.
     *
     * @param word  the word we are to derive a hash code from
     * @return      the hash code
     */
    private int computeHashCode(String word)
    {
        int hashCode = 0;
        String lowerCase = word.toLowerCase();

        if (lowerCase.length() > 0)
        {
            char[] charArr = lowerCase.toCharArray();

            for (int i = 0; i < lowerCase.length(); ++i)
            {
                hashCode = (37 * hashCode) + charArr[i];
            }
        }

        return hashCode;
    }

    /**
     * Check to see if the next word inserted will give our hash table a load factor >= .5.
     * If it does, the size of the hash table will be doubled and everything will be rehashed.
     *
     * @param neededSize  needed table capacity which will be used to calculate our load factor
     */
    private void resizeCheck(int neededSize)
    {
        Word[] temp = table;
        int tableSize = table.length;
        double loadFactor = (double) neededSize / tableSize;

        if (loadFactor >= 0.5)
        {
            table = new Word[tableSize * 2];

            for (int i = 0; i < temp.length; i++)
            {
                if (temp[i] != null) insert(temp[i]);
            }
        }
    }

    public Word[] getTableArray()
    {
        return table;
    }

    public int getWordCount()
    {
        return wordCount;
    }
}