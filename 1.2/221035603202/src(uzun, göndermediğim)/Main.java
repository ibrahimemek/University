import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.String;

public class Main
{
    static String allMoves = "";
    public static void main(String[] args)
    {
        try
        {
            String boardArg = args[0];
            String moveArg = args[1];
            FileWriter outputWriter = new FileWriter("output.txt");
            File boardFile = new File(boardArg);
            Scanner boardReader = new Scanner(boardFile);
            ArrayList<String[]> allBoardList = new ArrayList<>();
            while (boardReader.hasNextLine()) {
                String line = boardReader.nextLine();
                String[] currentRow = line.split(" ");
                allBoardList.add(currentRow);
            }
            outputWriter.write("Game board:\n");
            for (String[] eachArray : allBoardList)
            {
                outputWriter.append(Arrays.toString(eachArray).substring(1, Arrays.toString(eachArray).length() - 1).replace(",", "") + "\n");
            }

            String loopOutput = loop(allBoardList, moveArg);
            outputWriter.append("\nYour movement is:\n" + allMoves + "\n\n" + loopOutput);
            outputWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Wrong file");
        }
    }
    static int gameScore = 0;
    public static String loop(ArrayList<String[]> allBoardList, String moveArg)
    {
        try
        {
            int characterRowAt = 0;
            int characterColumnAt = 0;
            int rowAt = -1;
            int columnLength = allBoardList.get(0).length;
            int rowLength = allBoardList.size();
            for (String[] eachRow : allBoardList)
            {
                rowAt += 1;
                for (int columnAt = 0; columnAt < eachRow.length; columnAt++)
                {
                    if (eachRow[columnAt].equals("*"))
                    {
                        characterRowAt += rowAt;
                        characterColumnAt += columnAt;
                    }
                }
            }

            File moveFile = new File(moveArg);
            Scanner moveReader = new Scanner(moveFile);
            allMoves += moveReader.nextLine();

            for (char eachChar : allMoves.toCharArray())
            {
                switch (eachChar)
                {
                    case 'U':
                        if (characterRowAt == 0)
                        {
                            String colour = allBoardList.get(rowLength - 1)[characterColumnAt];
                            switch (colour)
                            {
                                case ("W"):
                                    characterRowAt += 1;
                                    allBoardList.get(0)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterRowAt = rowLength - 1;
                                    allBoardList.get(0)[characterColumnAt] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterRowAt = rowLength - 1;
                                    allBoardList.get(0)[characterColumnAt] = allBoardList.get(rowLength - 1)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        else
                        {
                            String colour = allBoardList.get(characterRowAt - 1)[characterColumnAt];
                            switch (colour)
                            {
                                case ("W"):
                                    if (characterRowAt == rowLength - 1)
                                    {
                                        characterRowAt = 0;
                                        allBoardList.get(rowLength - 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    else
                                    {
                                        characterRowAt += 1;
                                        allBoardList.get(characterRowAt - 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";

                                    }
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterRowAt = characterRowAt - 1;
                                    allBoardList.get(characterRowAt + 1)[characterColumnAt] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterRowAt = characterRowAt - 1;
                                    allBoardList.get(characterRowAt + 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        break;

                    case 'D':
                        if (characterRowAt == rowLength - 1)
                        {
                            String colour = allBoardList.get(0)[characterColumnAt];
                            switch (colour)
                            {
                                case ("W"):
                                    characterRowAt -= 1;
                                    allBoardList.get(characterRowAt + 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterRowAt = 0;
                                    allBoardList.get(rowLength - 1)[characterColumnAt] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterRowAt = 0;
                                    allBoardList.get(rowLength - 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        else
                        {
                            String colour = allBoardList.get(characterRowAt + 1)[characterColumnAt];
                            switch (colour)
                            {
                                case ("W"):
                                    if (characterRowAt == 0)
                                    {
                                        characterRowAt = rowLength - 1;
                                        allBoardList.get(0)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    else
                                    {
                                        characterRowAt -= 1;
                                        allBoardList.get(characterRowAt + 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterRowAt += 1;
                                    allBoardList.get(characterRowAt - 1)[characterColumnAt] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterRowAt += 1;
                                    allBoardList.get(characterRowAt - 1)[characterColumnAt] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        break;
                    case 'R':
                        if (characterColumnAt == columnLength - 1)
                        {
                            String colour = allBoardList.get(characterRowAt)[0];
                            switch (colour)
                            {
                                case ("W"):
                                    characterColumnAt -= 1;
                                    allBoardList.get(characterRowAt)[characterColumnAt + 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterColumnAt = 0;
                                    allBoardList.get(characterRowAt)[columnLength - 1] = "X";
                                    allBoardList.get(characterRowAt)[0] = "*";
                                    break;
                                default:
                                    characterColumnAt = 0;
                                    allBoardList.get(characterRowAt)[columnLength - 1] = allBoardList.get(characterRowAt)[0];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        else
                        {
                            String colour = allBoardList.get(characterRowAt)[characterColumnAt + 1];
                            switch (colour)
                            {
                                case ("W"):
                                    if (characterColumnAt == 1)
                                    {
                                        characterColumnAt = columnLength - 1;
                                        allBoardList.get(characterRowAt)[0] = allBoardList.get(characterRowAt)[columnLength - 1];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    else
                                    {
                                        characterColumnAt -= 1;
                                        allBoardList.get(characterRowAt)[characterColumnAt + 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterColumnAt += 1;
                                    allBoardList.get(characterRowAt)[characterColumnAt - 1] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterColumnAt += 1;
                                    allBoardList.get(characterRowAt)[characterColumnAt - 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        break;
                    case 'L':
                        if (characterColumnAt == 0) {
                            String colour = allBoardList.get(characterRowAt)[columnLength - 1];
                            switch (colour)
                            {
                                case ("W"):
                                    characterColumnAt = 1;
                                    allBoardList.get(characterRowAt)[characterColumnAt - 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterColumnAt = columnLength - 1;
                                    allBoardList.get(characterRowAt)[0] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterColumnAt = columnLength - 1;
                                    allBoardList.get(characterRowAt)[0] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        else
                        {
                            String colour = allBoardList.get(characterRowAt)[characterColumnAt - 1];
                            switch (colour) {
                                case ("W"):
                                    if (characterColumnAt == columnLength - 2)
                                    {
                                        characterColumnAt = 0;
                                        allBoardList.get(characterRowAt)[columnLength - 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    else
                                    {
                                        characterColumnAt += 1;
                                        allBoardList.get(characterRowAt)[characterColumnAt - 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                        allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    }
                                    break;
                                case ("H"):
                                    allBoardList.get(characterRowAt)[characterColumnAt] = " ";
                                    return finalOutput(allBoardList);
                                case ("B"):
                                    gameScore -= 10;
                                case ("Y"):
                                    gameScore -= 5;
                                case ("R"):
                                    gameScore += 10;
                                    characterColumnAt -= 1;
                                    allBoardList.get(characterRowAt)[characterColumnAt + 1] = "X";
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                                default:
                                    characterColumnAt -= 1;
                                    allBoardList.get(characterRowAt)[characterColumnAt + 1] = allBoardList.get(characterRowAt)[characterColumnAt];
                                    allBoardList.get(characterRowAt)[characterColumnAt] = "*";
                                    break;
                            }
                        }
                        break;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Wrong file");

        }
        return finalOutput(allBoardList);
    }
    public static String finalOutput(ArrayList<String[]>allBoardList)
    {
        String output = "Your output is:\n";
        for (String[] arrays : allBoardList)
        {
            output += Arrays.toString(arrays).substring(1, Arrays.toString(arrays).length() - 1).replace(",", "") + "\n";
        }
        output += String.format("\nGame Over!\nScore: %d",gameScore);
        return output;
    }
}