#include "GameController.h"
#include <fstream>
#include <iostream>


bool GameController::play(BlockFall& game, const string& commands_file) 
{

    // TODO: Implement the gameplay here while reading the commands from the input file given as the 3rd command-line
    //       argument. The return value represents if the gameplay was successful or not: false if game over,
    //       true otherwise.
    time_t Timer = std::time(nullptr);
    game.CurrentTopLeftCol = 0;
    ifstream CommandsFile(commands_file);
    string CurrentLine;
    while (getline(CommandsFile, CurrentLine))
    {
        while (CurrentLine.at(CurrentLine.length() - 1) == ' ')
        {
            CurrentLine.erase(CurrentLine.length() - 1);
        }
        
        if (CurrentLine == "PRINT_GRID")
        {
            InsertShape(game, make_pair(0, game.CurrentTopLeftCol));
            PrintHighScore(game);
            PrintCurrentGrid(game);
            RemoveShape(game);
        }
        else if (CurrentLine == "ROTATE_RIGHT")
        {
            if (IsPossible(game, game.CurrentTopLeftCol, game.active_rotation->right_rotation->shape))
            {
                game.active_rotation = game.active_rotation->right_rotation;
            }
        }
        else if (CurrentLine == "ROTATE_LEFT")
        {
            if (IsPossible(game, game.CurrentTopLeftCol, game.active_rotation->left_rotation->shape))
            {
                game.active_rotation = game.active_rotation->left_rotation;
            }
        }
        else if (CurrentLine == "MOVE_RIGHT")
        {
            if (IsPossible(game, game.CurrentTopLeftCol + 1, game.active_rotation->shape))
            {
                game.CurrentTopLeftCol += 1;
            }
        }
        else if (CurrentLine == "MOVE_LEFT")
        {
            if (IsPossible(game, game.CurrentTopLeftCol - 1, game.active_rotation->shape))
            {
                game.CurrentTopLeftCol -= 1;
            }
        }
        else if (CurrentLine == "DROP")
        {
            int MinDropDistance = game.rows;
            for (size_t AtCol = 0; AtCol < game.active_rotation->shape[0].size(); AtCol++)
            {
                for (size_t AtRow = game.active_rotation->shape.size(); AtRow > 0; AtRow--)
                {
                    if (game.active_rotation->shape[AtRow - 1][AtCol])
                    {
                        int CurrentDistance = CalculateGridDistance(game, make_pair(AtRow, game.CurrentTopLeftCol + AtCol));
                        if (CurrentDistance < MinDropDistance) MinDropDistance = CurrentDistance;
                        break;
                    }
                }
            }

            
            int BlockCount = 0;
            for (size_t AtRow = 0; AtRow < game.active_rotation->shape.size(); AtRow++)
            {
                for (size_t AtCol = 0; AtCol < game.active_rotation->shape[0].size(); AtCol++)
                {
                    if (game.active_rotation->shape[AtRow][AtCol])
                        BlockCount++;
                }
            }
            game.current_score += BlockCount * MinDropDistance;

            if (game.gravity_mode_on)
            {
                for (size_t AtCol = 0; AtCol < game.active_rotation->shape[0].size(); AtCol++)
                {
                    int BlockNumber = 0;
                    for (size_t AtRow = 0; AtRow < game.active_rotation->shape.size(); AtRow++)
                    {
                        if (game.active_rotation->shape[AtRow][AtCol])
                            BlockNumber++;
                    }
                    for (size_t AtRow = game.grid.size(); AtRow > 0; AtRow--)
                    {
                        if (!game.grid[AtRow - 1][game.CurrentTopLeftCol + AtCol])
                        {
                            if (AtRow - BlockNumber < 0) 
                            {
                                // game over
                                ProcessNewEntry(game);
                                cout << "GAME OVER!" << endl << "Next block that couldn't fit:" << endl;
                                for (vector<bool> AllRows : game.active_rotation->shape)
                                {
                                    for (bool AllCols : AllRows)
                                    {
                                        cout << AllCols << " ";
                                    }
                                    cout << endl;
                                }
                                cout << endl << "Final grid and score:" << endl << endl;
                                PrintHighScore(game);

                                PrintCurrentGrid(game);
                                game.leaderboard.print_leaderboard();
                                game.leaderboard.write_to_file(game.leaderboard_file_name);

                                return false;
                            }
                            for (int x = 0; x < BlockNumber; x++)
                            {
                                game.grid[AtRow - x - 1][game.CurrentTopLeftCol + AtCol] = 1;
                            }
                            break;
                        }
                    }

                }


            }
            else
            { 
                InsertShape(game, make_pair(MinDropDistance, game.CurrentTopLeftCol));
                
            }

            if (IsTherePowerUp(game))
            {
                cout << "Before clearing:" << endl;
                PrintCurrentGrid(game);
                for (size_t AtRow = 0; AtRow < game.grid.size(); AtRow++)
                {
                    for (size_t AtCol = 0; AtCol < game.grid.size(); AtCol++)
                    {
                        game.grid[AtRow][AtCol] = 0;
                    }
                }
                game.current_score += 1000;
            }

            if (int ClearedNum = ClearedRowCount(game))
            {
                game.current_score += game.cols * ClearedNum;
            }
            if (game.active_rotation->next_block != nullptr)
            {
                game.active_rotation = game.active_rotation->next_block;
                if (!CanEnter(game)) 
                {
                    // game over
                    ProcessNewEntry(game);
                    cout << "GAME OVER!" << endl << "Next block that couldn't fit:" << endl;
                    for (vector<bool> AllRows : game.active_rotation->shape)
                    {
                        for (bool AllCols : AllRows)
                        {
                            cout << AllCols << " ";
                        }
                        cout << endl;
                    }
                    cout << endl << "Final grid and score:" << endl << endl;
                    PrintHighScore(game);
                    
                    PrintCurrentGrid(game);
                    game.leaderboard.print_leaderboard();
                    game.leaderboard.write_to_file(game.leaderboard_file_name);

                    return false;
                }
                game.CurrentTopLeftCol = 0;
            }
            else 
            {
                ProcessNewEntry(game);
                cout << "YOU WIN!" << endl << "No more blocks." << endl;
                cout << "Final grid and score:" << endl << endl;
                for (vector<bool> AllRows : game.active_rotation->shape)
                {
                    for (bool AllCols : AllRows)
                    {
                        cout << AllCols << " ";
                    }
                    cout << endl;
                }
                PrintCurrentGrid(game);
                game.leaderboard.print_leaderboard();
                game.leaderboard.write_to_file(game.leaderboard_file_name);

                return true;
            }

        }
        else if (CurrentLine == "GRAVITY_SWITCH")
        {
            if (game.gravity_mode_on) game.gravity_mode_on = false;
            else
            {
                game.gravity_mode_on = true;
                ActivateGravity(game);
            }
        }
        else
        {

        }
    }
    ProcessNewEntry(game);
    cout << "GAME FINISHED!" << endl << "No more commands." << endl;
    cout << "Final grid and score:" << endl << endl;
    PrintHighScore(game);
    PrintCurrentGrid(game);
    cout << endl;
    game.leaderboard.print_leaderboard();
    game.leaderboard.write_to_file(game.leaderboard_file_name);
    return true;
}


void GameController::PrintHighScore(BlockFall& game)
{
    cout << "Score: " << game.current_score << endl;
    int CurrentHighScore;
    CalculateHighScore(game, CurrentHighScore);
    cout << "High Score: " << CurrentHighScore << endl;
}

void GameController::PrintCurrentGrid(BlockFall& game)
{
    for (int AtRow = 0; AtRow < game.rows; AtRow++)
    {
        for (int AtCol = 0; AtCol < game.cols; AtCol++)
        {
            if (game.grid[AtRow][AtCol])
                cout << occupiedCellChar;
            else
                cout << unoccupiedCellChar;
        }
        std::cout << std::endl;
    }
    std::cout << std::endl;
    std::cout << std::endl;
}

void GameController::CalculateHighScore(BlockFall& game, int& CurrentHighScore)
{
    if (!game.leaderboard.head_leaderboard_entry) return;
    if (game.leaderboard.head_leaderboard_entry->score)
    {
        if (game.leaderboard.head_leaderboard_entry->score > game.current_score)
        {
            CurrentHighScore = game.leaderboard.head_leaderboard_entry->score;
        }
        else
        {
            CurrentHighScore = game.current_score;
        }
    }
    else
    {
        CurrentHighScore = game.current_score;
    }
}

void GameController::InsertShape(BlockFall& game, pair<int, int> TopLeftLocation)
{
    for (int AtRow = 0; AtRow < game.active_rotation->shape.size(); AtRow++)
    {
        for (int AtCol = 0; AtCol < game.active_rotation->shape[0].size(); AtCol++)
        {
            if (game.active_rotation->shape[AtRow][AtCol])
                game.grid[AtRow + TopLeftLocation.first][AtCol + TopLeftLocation.second] = game.active_rotation->shape[AtRow][AtCol];
        }
    }
}

void GameController::RemoveShape(BlockFall& game)
{
    for (int AtRow = 0; AtRow < game.active_rotation->shape.size(); AtRow++)
    {
        for (int AtCol = 0; AtCol < game.active_rotation->shape[0].size(); AtCol++)
        {
            if (game.active_rotation->shape[AtRow][AtCol])
            {
                if (game.grid[AtRow][AtCol + game.CurrentTopLeftCol]) game.grid[AtRow][AtCol + game.CurrentTopLeftCol] = false;
                else cout << "error grid and shape is not the same";
            }
            //game.grid[AtRow][AtCol + game.CurrentTopLeftCol] = game.active_rotation->shape[AtRow][AtCol];
        }
    }
    /*cout << "removed" << endl;
    PrintGrid(game);*/
}


bool GameController::IsPossible(BlockFall& Game, int NewTopLeftCol, vector<vector<bool>> ShapeToInsert)
{
    if (NewTopLeftCol + ShapeToInsert[0].size() > Game.cols) return false;
    if (NewTopLeftCol < 0) return false;
    for (int AtRow = 0; AtRow < ShapeToInsert.size(); AtRow++)
    {
        for (int AtCol = 0; AtCol < ShapeToInsert[0].size(); AtCol++)
        {
            if (ShapeToInsert[AtRow][AtCol] == true && Game.grid[AtRow][NewTopLeftCol + AtCol] == true)
                return false;
        }
    }
    return true;
}

int GameController::CalculateGridDistance(BlockFall& Game, pair<int, int> StartLocation)
{
    for (int AtRow = StartLocation.first; AtRow < Game.grid.size(); AtRow++)
    {
        if (Game.grid[AtRow][StartLocation.second]) return AtRow - StartLocation.first;
    }
    return Game.grid.size() - StartLocation.first;
}

bool GameController::IsTherePowerUp(BlockFall& game)
{
    for (int AtRow = 0; AtRow < game.active_rotation->shape.size(); AtRow++)
    {
        for (int AtCol = 0; AtCol < game.active_rotation->shape[0].size(); AtCol++)
        {
            if (CompareToPowerUp(game, make_pair(AtRow, AtCol))) return true;
        }
    }
    return false;
}

bool GameController::CompareToPowerUp(BlockFall& game, pair<int, int>StartLocation)
{
    if (game.power_up.size() + StartLocation.first > game.grid.size()) return false;
    if (game.power_up[0].size() + StartLocation.second > game.grid[0].size()) return false;
    for (int AtRow = 0; AtRow < game.power_up.size(); AtRow++)
    {
        for (int AtCol = 0; AtCol < game.power_up[0].size(); AtCol++)
        {
            if (game.power_up[AtRow][AtCol] != game.grid[AtRow + StartLocation.first][AtCol + StartLocation.second]) return false;
        }
    }
    return true;
}

int GameController::ClearedRowCount(BlockFall& Game)
{
    int FilledRowCount = 0;
    for (size_t AtRow = 0; AtRow < Game.grid.size(); AtRow++)
    {
        
        bool bShouldClear = true;
        for (size_t AtCol = 0; AtCol < Game.grid[0].size(); AtCol++)
        {
            if (Game.grid[AtRow][AtCol] == false)
            {
                bShouldClear = false;
                break;
            }
        }
        if (bShouldClear)
        {
            FilledRowCount++;
            if (FilledRowCount == 1)
            {
                cout << "Before clearing:" << endl;
                PrintCurrentGrid(Game);
            }
            Game.grid.erase(Game.grid.begin() + AtRow);
            vector<int> EmptyRow;

            for (int x = 0; x < Game.grid[0].size(); x++)
            {
                EmptyRow.push_back(0);
            }
            vector<vector<int>> NewGrid;
            NewGrid.push_back(EmptyRow);
            copy(Game.grid.begin(), Game.grid.end(), back_inserter(NewGrid));
            Game.grid = NewGrid;
        }
    }
    return FilledRowCount;
}

bool GameController::CanEnter(BlockFall& Game)
{
    for (size_t AtRow = 0; AtRow < Game.active_rotation->shape.size(); AtRow++)
    {
        for (size_t AtCol = 0; AtCol < Game.active_rotation->shape[0].size(); AtCol++)
        {
            if (Game.active_rotation->shape[AtRow][AtCol] == 1 && Game.grid[AtRow][AtCol] == 1)
                return false;
        }
    }
    return true;
}


void GameController::ActivateGravity(BlockFall& Game)
{
    for (size_t AtCol = Game.grid[0].size(); AtCol > 0; AtCol--)
    {
        for (size_t AtRow = Game.grid.size(); AtRow > 0; AtRow--)
        {
            if (!Game.grid[AtRow - 1][AtCol - 1])
            {
                for (size_t CheckAtRow = AtRow; CheckAtRow > 0; CheckAtRow--)
                {
                    if (Game.grid[CheckAtRow - 1][AtCol - 1])
                    {
                        Game.grid[CheckAtRow - 1][AtCol - 1] = 0;
                        Game.grid[AtRow - 1][AtCol - 1] = 1;
                        break;
                    }
                }

            }
        }
    }
    if (int ClearedNum = ClearedRowCount(Game))
    {
        Game.current_score += Game.cols * ClearedNum;
    }
}

void GameController::ProcessNewEntry(BlockFall& game)
{
    time_t CurrentTime = time(nullptr);
    LeaderboardEntry* NewEntry = new LeaderboardEntry(game.current_score, CurrentTime, game.player_name);
    game.leaderboard.insert_new_entry(NewEntry);
}



