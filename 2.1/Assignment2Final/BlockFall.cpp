#include "BlockFall.h"
#include <fstream>
#include <sstream>
#include <iostream>

BlockFall::BlockFall(string grid_file_name, string blocks_file_name, bool gravity_mode_on, const string& leaderboard_file_name, const string& player_name) : gravity_mode_on(
    gravity_mode_on), leaderboard_file_name(leaderboard_file_name), player_name(player_name) {
    initialize_grid(grid_file_name);
    read_blocks(blocks_file_name);
    leaderboard.read_from_file(leaderboard_file_name);
}

void BlockFall::read_blocks(const string& input_file)
{
    // TODO: Read the blocks from the input file and initialize "initial_block" and "active_rotation" member variables
    // TODO: For every block, generate its rotations and properly implement the multilevel linked list structure
    //       that represents the game blocks, as explained in the PA instructions.
    // TODO: Initialize the "power_up" member variable as the last block from the input file (do not add it to the linked list!)
    vector<vector<vector<bool>>> AllBlocks;
    vector<vector<bool>> CurrentBlock;
    ifstream BlocksFile(input_file);
    string CurrentLine;
    while (getline(BlocksFile, CurrentLine))
    {
        vector<bool> CurrentRow;
        char CurrentTruth;
        std::istringstream ss(CurrentLine);
        while (ss >> CurrentTruth)
        {
            if (CurrentTruth != '1' && CurrentTruth != '0') continue;
            if (CurrentTruth == '1') CurrentRow.push_back(true);
            else CurrentRow.push_back(false);
                        
        }
        if (!CurrentRow.empty()) CurrentBlock.push_back(CurrentRow);
        if (CurrentLine.find(']') < CurrentLine.size())
        {
            AllBlocks.push_back(CurrentBlock);
            CurrentBlock.clear();
        }
    }
    BlocksFile.close();
    Block* PreviousBlock;
    power_up = AllBlocks.back();
    AllBlocks.pop_back();
    Block* FirstBlock = new Block();
    FirstBlock->shape = AllBlocks[0];
    CreateRotations(FirstBlock);
    initial_block = FirstBlock;
    active_rotation = FirstBlock;
    PreviousBlock = FirstBlock;

    for (size_t BlockAt = 1; BlockAt < AllBlocks.size(); BlockAt++)
    {
        Block* NewBlock = new Block();
        NewBlock->shape = AllBlocks[BlockAt];
        Block* RotationAt = PreviousBlock;
        for (int m = 0; m < PreviousBlock->RotationCount; m++)
        {
            RotationAt->next_block = NewBlock;
            RotationAt = RotationAt->left_rotation;
        }

        CreateRotations(NewBlock);

        PreviousBlock = NewBlock;
    }

}

void BlockFall::CreateRotations(Block* NewBlock)
{
    if (NewBlock->shape == rotateToLeft(NewBlock->shape)) NewBlock->RotationCount = 1;
    else if (NewBlock->shape == rotateToLeft(rotateToLeft(NewBlock->shape))) NewBlock->RotationCount = 2;


    if (NewBlock->RotationCount == 1)
    {
        NewBlock->left_rotation = NewBlock;
        NewBlock->right_rotation = NewBlock;
    }
    Block* RotationAtBlock = NewBlock;
    for (int n = 0; n < NewBlock->RotationCount - 1; n++)
    {
        Block* RotatedBlock = new Block();
        RotatedBlock->shape = rotateToLeft(RotationAtBlock->shape);
        RotatedBlock->right_rotation = RotationAtBlock;
        RotationAtBlock->left_rotation = RotatedBlock;
        if (n == NewBlock->RotationCount - 2)
        {
            NewBlock->right_rotation = RotatedBlock;
            RotatedBlock->left_rotation = NewBlock;

        }

        RotationAtBlock = RotatedBlock;

    }
}

void BlockFall::initialize_grid(const string& input_file)
{
    // TODO: Initialize "rows" and "cols" member variables
    // TODO: Initialize "grid" member variable using the command-line argument 1 in main

    string GridLine;
    vector<vector<int>> CurrentGrid;
    ifstream GridFile(input_file);
    while (getline(GridFile, GridLine))
    {
        istringstream ss(GridLine);
        char c;
        vector<int> CurrentRow;
        while (ss >> c)
        {
            if (c == '0') CurrentRow.push_back(0);
            else if (c == '1') CurrentRow.push_back(1);
            else continue;
        }
        CurrentGrid.push_back(CurrentRow);
    }
    GridFile.close();

    grid = CurrentGrid;
    rows = CurrentGrid.size();
    cols = CurrentGrid[0].size();
}

vector<vector<bool>> BlockFall::rotateToLeft(vector<vector<bool>> shapeToRotate)
{
    if (shapeToRotate.size() == 0) return shapeToRotate;
    vector<vector<bool>> rotatedShape;

    for (size_t i = 0; i < shapeToRotate[0].size(); i++)
    {
        vector<bool> currentRow(shapeToRotate.size());
        rotatedShape.push_back(currentRow);
    }

    for (size_t k = 0; k < shapeToRotate.size(); k++)
    {
        for (size_t m = 0; m < shapeToRotate[0].size(); m++)
        {
            int colAt = shapeToRotate[0].size() - m - 1;
            rotatedShape[m][k] = shapeToRotate[k][colAt];
        }
    }

    return rotatedShape;
}


BlockFall::~BlockFall()
{
    // TODO: Free dynamically allocated memory used for storing game blocks
    Block* PreviousBlock = initial_block;
    while (PreviousBlock != nullptr)
    {
        Block* CurrentBlock = PreviousBlock->next_block;
        Block* RotationAt = PreviousBlock;
        int LoopCount = PreviousBlock->RotationCount;
        for (int i = 0; i < LoopCount; i++)
        {
            if (i != LoopCount - 1) RotationAt = PreviousBlock->left_rotation;
            delete PreviousBlock;
            PreviousBlock = RotationAt;
        }
        PreviousBlock = CurrentBlock;
    }

}
