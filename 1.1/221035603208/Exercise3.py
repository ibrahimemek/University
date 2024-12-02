game_stages = {"A": "3",
               "B": "0",
               "C": "0", }
choice = " "
while not choice == "Quit":
    remove_towel = input("Which location do you want to take from? A or B or C: ")
    if remove_towel == "A":
        if game_stages["A"] == "0":
            print("You can't remove a towel, because there is none. ")
        else:
            game_stages["A"] = str(int(game_stages["A"]) - 1)
    if remove_towel == "B":
        if game_stages["B"] == "0":
            print("You can't remove a towel, because there is none. ")
        else:
            game_stages["B"] = str(int(game_stages["B"]) - 1)
    if remove_towel == "C":
        if game_stages["C"] == "0":
            print("You can't remove a towel, because there is none. ")
        else:
            game_stages["C"] = str(int(game_stages["C"]) - 1)

    add_towel = input("Which location do you want to add? A or B or C: ")
    if add_towel == "A":
        game_stages["A"] = str(int(game_stages["A"]) + 1)
    if add_towel == "B":
        game_stages["B"] = str(int(game_stages["B"]) + 1)
    if add_towel == "C":
        game_stages["C"] = str(int(game_stages["C"]) + 1)
    print(game_stages)
    choice = input("Do you wan to quit, if yes enter Quit: ")
