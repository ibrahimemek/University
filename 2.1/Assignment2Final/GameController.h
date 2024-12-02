#ifndef PA2_GAMECONTROLLER_H
#define PA2_GAMECONTROLLER_H

#include "BlockFall.h"

using namespace std;

class GameController {
public:
    bool play(BlockFall& game, const string& commands_file); // Function that implements the gameplay
    void PrintHighScore(BlockFall& game);
    void PrintCurrentGrid(BlockFall& game);
    void CalculateHighScore(BlockFall& game, int& CurrentHighScore);
    void InsertShape(BlockFall& game, pair<int, int> TopLeftLocation);
    void RemoveShape(BlockFall& game);
    bool IsPossible(BlockFall& Game, int NewTopLeftCol, vector<vector<bool>> ShapeToInsert);
    int CalculateGridDistance(BlockFall& Game, pair<int, int>StartLocation);
    bool IsTherePowerUp(BlockFall& game);
    bool CompareToPowerUp(BlockFall& game, pair<int, int>StartLocation);
    int ClearedRowCount(BlockFall& Game);
    bool CanEnter(BlockFall& Game);
    void ActivateGravity(BlockFall& Game);
    void ProcessNewEntry(BlockFall& game);

    
};


#endif //PA2_GAMECONTROLLER_H
