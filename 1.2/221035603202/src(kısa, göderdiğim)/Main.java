import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    static int playerRowAt = 0;
    static int playerColumnAt= 0;
    static ArrayList <String[]> allBoard = new ArrayList<>();
    static int gameScore = 0;
    static boolean isFinished = false;

    public static void main(String[] args)
    {
        try
        {
            FileWriter outputWriter = new FileWriter("output.txt");
            outputWriter.write("Game board:\n");
            String boardArg = args[0];
            File boardFile = new File(boardArg);
            Scanner boardReader = new Scanner(boardFile);
            while (boardReader.hasNextLine())
            {
                String eachStringLine = boardReader.nextLine().trim();
                allBoard.add(eachStringLine.split(" "));
                outputWriter.append(eachStringLine + "\n");
            }

            int rowAt = -1;
            for (String[] eachRow : allBoard)
            {
                rowAt += 1;
                for (int columnAt = 0; columnAt < eachRow.length; columnAt++)
                {
                    if (eachRow[columnAt].equals("*"))
                    {
                        playerRowAt += rowAt;
                        playerColumnAt += columnAt;
                    }
                }
            }

            String moveArg = args[1];
            File moveFile = new File(moveArg);
            Scanner moveReader = new Scanner(moveFile);
            String allMoves = moveReader.nextLine().trim();
            String playedMoves = "";
            for (char eachMove : allMoves.toCharArray())
            {
                if (!isFinished)
                {
                    playedMoves += eachMove;
                    switch (eachMove)
                    {
                        case 'U':
                            gameProgress(0,-1);
                            break;
                        case 'D':
                            gameProgress(0,1);
                            break;
                        case 'R':
                            gameProgress(1,0);
                            break;
                        case 'L':
                            gameProgress(-1,0);
                            break;
                    }
                }
            }
            outputWriter.append("\nYour movement is:\n"+ playedMoves + "\n\nYour output is:\n");
            for (String[] boardRows : allBoard) outputWriter.append(Arrays.toString(boardRows).substring(1, Arrays.toString(boardRows).length() - 1).replace(",", "") + "\n");
            if (isFinished) outputWriter.append("\nGame over!");
            outputWriter.append("\nScore: " + gameScore);
            outputWriter.close();
        } catch (IOException e)
        {
            System.out.println("Wrong file");
        }
    }
    public static void gameProgress(int xChange, int yChange)
    {
        int columnLength = allBoard.get(0).length;
        int rowLength = allBoard.size();
        int oldRow = playerRowAt;
        int oldCol = playerColumnAt;
        playerRowAt += yChange;
        playerColumnAt += xChange;
        if (playerRowAt < 0) playerRowAt += rowLength;
        if (playerRowAt > rowLength - 1) playerRowAt -= rowLength;
        if (playerColumnAt < 0) playerColumnAt += columnLength;
        if (playerColumnAt > columnLength - 1) playerColumnAt -= columnLength;

        switch (allBoard.get(playerRowAt)[playerColumnAt])
        {
            case ("W"):
                playerRowAt = oldRow;
                playerColumnAt = oldCol;
                gameProgress(-xChange, -yChange);
                break;
            case ("H"):
                allBoard.get(oldRow)[oldCol] = " ";
                isFinished = true;
                return;
            case ("B"):
                gameScore -= 10;
            case ("Y"):
                gameScore -= 5;
            case ("R"):
                gameScore += 10;
                allBoard.get(oldRow)[oldCol] = "X";
                allBoard.get(playerRowAt)[playerColumnAt] = "*";
                break;
            default:
                allBoard.get(oldRow)[oldCol] = allBoard.get(playerRowAt)[playerColumnAt];
                allBoard.get(playerRowAt)[playerColumnAt] = "*";
                break;
        }
    }
}