import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * This class contains several methods that I consider to be "utilities" for my
 * project to properly function. Everything is called by the Main class.
 */
public class MainUtility
{
    private HashTable hashTable = new HashTable();

    /**
     * Hash the file row by row, one word at a time.
     *
     * @param fileName  file read in
     */
    public void hashFile(String fileName)
    {
        try
        {
            String line;
            StringTokenizer tokenizer;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null)
            {
                tokenizer = new StringTokenizer(line, " \t\n\r\f1234567890,.!?$%&-\"");

                while(tokenizer.hasMoreTokens())
                {
                    hashTable.insert(tokenizer.nextToken());
                }
            }
        }
        catch (FileNotFoundException exception)
        {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
        catch (IOException exception)
        {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
    }

    /**
     * Output all the word occurrences in the text file.
     */
    public void displayResult()
    {
        hashTable.finalHash(); // One last hash to rid of any duplicates
        Word[] hashArray = hashTable.getTableArray();
        System.out.println("Words " + hashTable.getWordCount() + "\n");

        for (int i = 0; i < hashArray.length; i++)
        {
            if (hashArray[i] != null)
            {
                System.out.println(hashArray[i].toString());
            }
        }
    }
}

