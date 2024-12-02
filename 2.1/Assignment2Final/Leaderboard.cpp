#define _CRT_SECURE_NO_WARNINGS

#include "Leaderboard.h"
#include <fstream>
#include <iostream>

void Leaderboard::insert_new_entry(LeaderboardEntry* new_entry) {
    // TODO: Insert a new LeaderboardEntry instance into the leaderboard, such that the order of the high-scores
    //       is maintained, and the leaderboard size does not exceed 10 entries at any given time (only the
    //       top 10 all-time high-scores should be kept in descending order by the score).
    // TODO: Insert a new LeaderboardEntry instance into the leaderboard, maintaining order and limiting the size to 10 entries.


    if (head_leaderboard_entry == nullptr) {
        head_leaderboard_entry = new_entry;
        return;
    }


    LeaderboardEntry* current = head_leaderboard_entry;

    // if new entry has the highest score
    if (new_entry->score > head_leaderboard_entry->score) {
        new_entry->next_leaderboard_entry = head_leaderboard_entry;
        head_leaderboard_entry = new_entry;
    }
    else {
        while (current != nullptr && current->next_leaderboard_entry != nullptr) {
            if (new_entry->score <= current->score && new_entry->score > current->next_leaderboard_entry->score) {
                new_entry->next_leaderboard_entry = current->next_leaderboard_entry;
                current->next_leaderboard_entry = new_entry;
                break;
            }
            current = current->next_leaderboard_entry;
        }// current = last

        if (current->next_leaderboard_entry == nullptr) {
            /*current = head_leaderboard_entry;
            while (current->next_leaderboard_entry != nullptr){
                current = current->next_leaderboard_entry;
            }*/
            current->next_leaderboard_entry = new_entry;

        }
    }




    int count = 0;
    LeaderboardEntry* countCurrent = head_leaderboard_entry;
    while (countCurrent != nullptr) {
        count += 1;
        countCurrent = countCurrent->next_leaderboard_entry;
    }

    // add new entry to end of list


    if (count > 10) {
        current = head_leaderboard_entry;
        LeaderboardEntry* prev = nullptr;

        while (current->next_leaderboard_entry != nullptr) {
            prev = current;
            current = current->next_leaderboard_entry;
        }
        delete current;
        prev->next_leaderboard_entry = nullptr;


    }

}

void Leaderboard::write_to_file(const string& filename) {
    // TODO: Write the latest leaderboard status to the given file in the format specified in the PA instructions
    ofstream OutputFile(filename);
    LeaderboardEntry* CurrentEntry = head_leaderboard_entry;
    while (CurrentEntry != nullptr)
    {
        OutputFile << CurrentEntry->score << " " << CurrentEntry->last_played << " " << CurrentEntry->player_name << endl;
        CurrentEntry = CurrentEntry->next_leaderboard_entry;
    }

}

void Leaderboard::read_from_file(const string& filename) {
    // TODO: Read the   stored leaderboard status from the given file such that the "head_leaderboard_entry" member
    //       variable will point to the highest all-times score, and all other scores will be reachable from it
    //       via the "next_leaderboard_entry" member variable pointer.
    ifstream InputFile(filename);
    LeaderboardEntry* CurrentEntry = head_leaderboard_entry;
    while (CurrentEntry) 
    {
        LeaderboardEntry* NextEntry = CurrentEntry->next_leaderboard_entry;
        delete CurrentEntry;
        CurrentEntry = NextEntry;
    }
    unsigned long CurrentScore;
    time_t LastPlayed;
    string PlayerName;

    while (InputFile >> CurrentScore >> LastPlayed >> PlayerName)
    {
        LeaderboardEntry* NewEntry = new LeaderboardEntry(CurrentScore, LastPlayed, PlayerName);
        insert_new_entry(NewEntry);
    }
    
}


void Leaderboard::print_leaderboard() 
{
    // TODO: Print the current leaderboard status to the standard output in the format specified in the PA instructions
    cout << "Leaderboard" << endl;
    cout << "-----------" << endl;
    int EntryAt = 1;
    LeaderboardEntry* CurrentEntry = head_leaderboard_entry;
    while (CurrentEntry) {
        struct tm* Time = localtime(&CurrentEntry->last_played);
        char StringTime[20];
        strftime(StringTime, sizeof(StringTime), "%H:%M:%S/%d.%m.%Y", Time);

        cout << EntryAt << ". " << CurrentEntry->player_name << " " << CurrentEntry->score << " " << StringTime << endl;

        EntryAt++;
        CurrentEntry = CurrentEntry->next_leaderboard_entry;
        
    }
}

Leaderboard::~Leaderboard() {
    // TODO: Free dynamically allocated memory used for storing leaderboard entries
    LeaderboardEntry* CurrentEntry = head_leaderboard_entry;
    while (CurrentEntry)
    {
        LeaderboardEntry* NextEntry = CurrentEntry->next_leaderboard_entry;
        delete CurrentEntry;
        CurrentEntry = NextEntry;
    }
}