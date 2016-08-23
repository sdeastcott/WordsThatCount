//******************************
// Project 2 - Words That Count
//
// Author: Steven Eastcott
// CWID:   11491519
// Course: CS 360
//******************************

public class Main
{
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("I'm expecting one argument only - the file name.");
            System.exit(1);
        }

        MainUtility utility = new MainUtility();
        utility.hashFile(args[0]);
        utility.displayResult();
    }
}
