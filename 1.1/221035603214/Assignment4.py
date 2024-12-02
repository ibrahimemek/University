import sys
import string

alphabet = list(string.ascii_uppercase)

ship_locations = {
    "first_player": {},
    "second_player": {},
}  # first_player dictionary indicates first player's board's ship locations.(first player hidden board)
first_board_remains = {
    "C": 1,
    "B": 2,
    "D": 1,
    "S": 1,
    "P": 4,
}  # It indicates how many ship left in the first board.
second_board_remains = {
    "C": 1,
    "B": 2,
    "D": 1,
    "S": 1,
    "P": 4,
}  # It indicates how many ship left in the second board.


def reading_locations(file_name):
    """This function looks if ship locating done successfully."""
    all_items = []
    while True:
        each_line = file_name.readline()
        if each_line == "":
            break
        each_line = each_line.strip()
        each_line_items = each_line.split(";")
        all_items.append(each_line_items)  # all_items contain player.text information.

    is_true_index = True
    at_row = -1
    for each_list in all_items:
        at_row += 1
        at_column = -1
        for each_thing in each_list:
            at_column += 1

            if each_thing == "":
                pass
            else:
                if file_name == first_player_locating:
                    player_turn = "first_player"
                else:
                    player_turn = "second_player"
                delete_to_check(player_turn, each_thing, at_row, at_column, all_items)

        if at_column != 9:
            is_true_index = False
    if at_row != 9:
        is_true_index = False

    if not is_true_index:
        raise IndexError
    else:  # Looks if there is anything left. If there is something left, it means the way of ship locating is false.
        for each_list in all_items:
            for each_thing in each_list:
                if each_thing != "":
                    raise ValueError


def delete_to_check(player_turn, letter, at_row, at_column, all_items):
    """This function deletes each ship until the end, and looks if there is any ship left. If there is no ship left, ship locating done successfully."""
    ships_length = {
        "C": 5,
        "B": 4,
        "D": 3,
        "S": 3,
        "P": 2,
    }
    at_same_row = True  # I am looking if there is a ship in a row.
    try:
        for x in range(at_column, at_column + ships_length[letter]):
            if all_items[at_row][x] != letter:
                at_same_row = False
                break
    except:
        at_same_row = False

    at_same_column = True  # I am looking if there is a ship in a column.
    try:
        for y in range(at_row, at_row + ships_length[letter]):
            if all_items[y][at_column] != letter:
                at_same_column = False
                break
    except:
        at_same_column = False

    if at_same_column or at_same_row:

        if at_same_column and at_same_row:
            if all_items[at_column + ships_length[letter]] == letter:  # If there is same letter after the current
                # locations, it means first letter is belong to vertical ship, the rest is horizontal.
                for y in range(at_column + 1, at_column + ships_length[letter] + 1):
                    all_items[at_row][y] = ""
                    save_ship_locations(player_turn, letter, at_row, y)
                for p in range(at_row, at_row + ships_length[letter]):
                    all_items[p][at_column] = ""
                    save_ship_locations(player_turn, letter, p, at_column)
            else:
                for x in range(at_column, at_column + ships_length[letter]):
                    all_items[at_row][x] = ""
                    save_ship_locations(player_turn, letter, at_row, x)

        elif at_same_row:
            for k in range(at_column, at_column + ships_length[letter]):
                all_items[at_row][k] = ""
                save_ship_locations(player_turn, letter, at_row, k)

        elif at_same_column:
            for m in range(at_row, at_row + ships_length[letter]):
                all_items[m][at_column] = ""
                save_ship_locations(player_turn, letter, m, at_column)


def save_ship_locations(player_turn, letter, at_row, at_column):
    """This function saves the ship locations to use them later."""
    ships_length = {
        "C": 5,
        "B": 4,
        "D": 3,
        "S": 3,
        "P": 2,
    }
    x = "0"
    did_fail = True
    try:
        a = ship_locations[player_turn][letter + x]
        did_fail = False
    except:
        pass
    if did_fail:  # It means there is no specified key in the dictionary.
        ship_locations[player_turn][letter + x] = []
        ship_locations[player_turn][letter + x].append([at_row, at_column])
    else:
        while not len(ship_locations[player_turn][letter + x]) < int(ships_length[letter]):  # If there is already a
            # ship which all of its locations if found, it passes to next key(if P0 is full, it passes to P1)
            x = str(int(x) + 1)
            did_fail = True
            try:
                b = ship_locations[player_turn][letter + x]
                did_fail = False
            except:
                pass
            if did_fail:  # It means there is no specified key in the dictionary.
                ship_locations[player_turn][letter + x] = []
        ship_locations[player_turn][letter + x].append([at_row, at_column])


def create_board(file_name, player_num):
    """Creates a board according to the file."""
    with open(file_name, "r", encoding="utf-8") as player_input:
        hidden_board_one = []
        hidden_board_two = []
        while True:
            each_line = player_input.readline()
            each_line = each_line.strip()
            if each_line == "":
                break
            each_item = each_line.split(";")
            if player_num == "Player1":
                hidden_board_one.append(each_item)
            elif player_num == "Player2":
                hidden_board_two.append(each_item)
        if player_num == "Player1":
            return hidden_board_one
        elif player_num == "Player2":
            return hidden_board_two


def create_empty_board():
    """Creates an empty 10x10 board."""
    board_name = []
    for x in range(10):
        a_list = []
        for y in range(10):
            a_list.append("-")
        board_name.append(a_list)
    return board_name


def read_moves():
    """Reads every move, and process it."""
    first_file = open(player_one_input, "r", encoding="utf-8")
    second_file = open(player_two_input, "r", encoding="utf-8")
    first_move_inputs = first_file.readline()
    second_move_inputs = second_file.readline()
    first_player_moves = first_move_inputs[:-1].split(";")
    second_player_moves = second_move_inputs[:-1].split(";")

    first_at_num = 0  # indicates first player's index in first_player_moves.
    second_at_num = 0  # indicates second player's index in second_player_moves.
    global round_num
    round_num = 0
    first_player_turn = True
    print("Battle of Ships Game")
    output_file.write("Battle of Ships Game\n")

    while first_at_num < len(first_player_moves) or second_at_num < len(second_player_moves):
        if first_player_turn:
            if check_if_valid(first_player_moves, first_at_num):
                round_num += 1
                show_output("first")
                check_location("first", int(each_move[0]), each_move[1])
                first_at_num += 1
                ship_counts("first_player")
                first_player_turn = False
            else:  # It means the move is not valid.
                first_at_num += 1
        else:  # It is second player's turn.
            if check_if_valid(second_player_moves, second_at_num):
                show_output("second")
                check_location("second", int(each_move[0]), each_move[1])
                second_at_num += 1
                ship_counts("second_player")
                first_player_turn = True

                if len(ship_locations["first_player"]) == 0 or len(ship_locations["second_player"]) == 0:  # It means the game is over.

                    if len(ship_locations["first_player"]) == 0 and len(ship_locations["second_player"]) == 0:
                        print("\nIt is a Draw")
                        output_file.write("\nIt is a Draw\n")
                        show_output("final")
                        break
                    elif len(ship_locations["first_player"]) == 0:
                        convert_to_final_board("player_two_board")
                        print("\nPlayer2 wins!")
                        output_file.write("\nPlayer2 wins!\n")
                        show_output("final")
                        break
                    elif len(ship_locations["second_player"]) == 0:
                        convert_to_final_board("player_one_board")
                        print("\nPlayer1 wins!")
                        output_file.write("\nPlayer1 wins!\n")
                        show_output("final")
                        break

            else:  # It means the move is not valid.
                second_at_num += 1


def check_if_valid(player_moves, at_num):
    """Checks if move is valid."""
    did_success = False
    try:
        if "," not in player_moves[at_num]:
            raise IndexError
    except IndexError:
        print("IndexError: Your move is not valid because of lack of ','.")
        output_file.write("IndexError: Your move is not valid because of lack of ','.\n")

    else:
        global each_move
        each_move = player_moves[at_num].split(",")

        try:
            a = int(each_move[0]) + 1
        except TypeError:
            print("ValueError: Your first input should be an integer.")
            output_file.write("ValueError: Your first input should be an integer.\n")

        else:
            try:
                if each_move[1] not in alphabet:
                    raise ValueError
                else:
                    try:
                        if int(each_move[0]) > 10:
                            raise AssertionError
                        if alphabet.index(each_move[1]) > 9:
                            raise AssertionError
                        did_success = True
                    except AssertionError:
                        print(f"AssertionError: Invalid Operation")
                        output_file.write(f"AssertionError: Invalid Operation\n")
                    else:
                        did_success = True
            except ValueError:
                print("ValueError: Your second input should be a letter.")
                output_file.write("ValueError: Your second input should be a letter.\n")

    return did_success


def show_output(player_turn):
    """Shows the output."""
    if player_turn == "first":
        print("\nPlayer1's move\n")
        print(f"Round : {round_num}\t\t\t\t\tGrid Size: 10x10\n")
        output_file.write(f"\nPlayer1's move\n\nRound : {round_num}\t\t\t\t\tGrid Size: 10x10\n\n")
        current_output = current_boards()
        print(current_output)
        output_file.write(f"{current_output}\n")
        remaining_ship_table(False)

    elif player_turn == "second":
        print("\nPlayer2's move\n")
        print(f"Round : {round_num}\t\t\t\t\tGrid Size: 10x10\n")
        output_file.write(f"\nPlayer2's move\n\nRound : {round_num}\t\t\t\t\tGrid Size: 10x10\n\n")
        current_output = current_boards()
        print(current_output)
        output_file.write(f"{current_output}\n")
        remaining_ship_table(False)

    elif player_turn == "final":
        print("\nFinal information\n")
        output_file.write("\nFinal information\n")
        current_output = current_boards()
        print(current_output)
        output_file.write(f"\n{current_output}\n")
        remaining_ship_table(True)


def current_boards():
    """Shows the current boards."""
    current_output = ""
    current_output += "Player1's Hidden Board\t\tPlayer2's Hidden Board\n"
    current_output += " "
    for x in range(10):
        current_output += " " + alphabet[x]
    current_output += "\t  "
    for x in range(10):
        current_output += " " + alphabet[x]
    current_output += "\n"
    for y in range(10):
        first_per_line = " ".join(first_empty_board[y])
        second_per_line = " ".join(second_empty_board[y])
        current_output += f"{y + 1:<2}{first_per_line}\t {y + 1:<2}{second_per_line}\n"
    return current_output


def remaining_ship_table(is_final):
    """Shows the remained ship table."""
    all_ships = {
        "Carrier": 1,
        "Battleship": 2,
        "Destroyer": 1,
        "Submarine": 1,
        "Patrol Boat": 4,
    }
    for each_ship in all_ships:
        first_board_minus = first_board_remains[each_ship[0]]
        second_board_minus = second_board_remains[each_ship[0]]
        first_board_x = all_ships[each_ship] - first_board_remains[each_ship[0]]
        second_board_x = all_ships[each_ship] - second_board_remains[each_ship[0]]

        if each_ship == "Carrier":
            print(f"{each_ship}\t\t{first_board_x * 'X '}{first_board_minus * '- '}\t\t\t\t"
                  f"{each_ship}\t{second_board_x * 'X '}{second_board_minus * '- '}")
            output_file.write(f"{each_ship}\t\t{first_board_x * 'X '}{first_board_minus * '-' }\t\t\t\t{each_ship}\t\t"
                              f"{second_board_x * 'X'}{second_board_minus * '-'}\n")
        elif each_ship == "Patrol Boat":
            print(f"{each_ship}\t{first_board_x * 'X '}{first_board_minus * '- '}\t\t\t"
                  f"{each_ship}\t{second_board_x * 'X '}{second_board_minus * '- '}")
            output_file.write(f"{each_ship}\t{first_board_x * 'X '}{first_board_minus * '- '}\t\t\t"
                              f"{each_ship}\t{second_board_x * 'X '}{second_board_minus * '- '}\n")
        else:
            print(f"{each_ship}\t{first_board_x * 'X '}{first_board_minus * '- '}\t\t\t\t"
                  f"{each_ship}\t{second_board_x * 'X '}{second_board_minus * '- '}")
            output_file.write(f"{each_ship}\t{first_board_x * 'X '}{first_board_minus * '- '}\t\t\t\t"
                              f"{each_ship}\t{second_board_x * 'X '}{second_board_minus * '- '}\n")
    if not is_final:  # we don't add this output if this output belongs to final output.
        print(f"Enter your move: {each_move[0]},{each_move[1]}")
        output_file.write(f"\nEnter your move: {each_move[0]},{each_move[1]}\n")


def check_location(player_turn, row, column_alphabet):
    """Shows if there is a ship part in the specified location."""
    if player_turn == "first":
        if second_players_hidden_board[row - 1][alphabet.index(column_alphabet)] != "":
            second_empty_board[row - 1][alphabet.index(column_alphabet)] = "X"
        else:
            second_empty_board[row - 1][alphabet.index(column_alphabet)] = "O"
    elif player_turn == "second":
        if first_players_hidden_board[row - 1][alphabet.index(column_alphabet)] != "":
            first_empty_board[row - 1][alphabet.index(column_alphabet)] = "X"
        else:
            first_empty_board[row - 1][alphabet.index(column_alphabet)] = "O"


def ship_counts(player_turn):
    """Looks if a ship sunk, and keeps the number of remained ship."""
    will_deleted = []
    if player_turn == "first_player":
        for each_key in ship_locations["second_player"]:  # each key indicates C0, B0, B1 etc.
            is_found = True
            for each_location in ship_locations["second_player"][each_key]:  # each_location indicates each_key's location.
                if second_empty_board[each_location[0]][each_location[1]] != "X":
                    is_found = False

            if is_found:  # It means all parts of a ship is sunk.
                second_board_remains[each_key[0]] -= 1
                will_deleted.append(each_key)

        if len(will_deleted) != 0:  # If a ship is sunk, I am deleting it from the dictionary which saves all ship locations.
            for x in range(len(will_deleted)):
                ship_locations["second_player"].pop(will_deleted[x])

    elif player_turn == "second_player":
        for each_key in ship_locations["first_player"]:  # each_key indicates C0, B0, B1 etc.
            is_found = True
            for each_location in ship_locations["first_player"][each_key]:  # each_location indicates each_key's location.-
                if first_empty_board[each_location[0]][each_location[1]] != "X":
                    is_found = False

            if is_found:  # It means all parts of a ship is sunk.
                first_board_remains[each_key[0]] -= 1
                will_deleted.append(each_key)

        if len(will_deleted) != 0:  # If a ship is sunk, I am deleting it from the dictionary which saves all ship locations.
            ship_locations["first_player"].pop(will_deleted[0])


def convert_to_final_board(board_name):
    """This functions reveals hidden ship parts."""
    if board_name == "player_two_board":
        for each_ship_name in ship_locations["second_player"]:
            for each_location in ship_locations["second_player"][each_ship_name]:
                if second_empty_board[each_location[0]][each_location[1]] == "-":
                    second_empty_board[each_location[0]][each_location[1]] = each_ship_name[0]

    elif board_name == "player_one_board":
        for each_ship_name in ship_locations["first_player"]:
            for each_location in ship_locations["first_player"][each_ship_name]:
                if first_empty_board[each_location[0]][each_location[1]] == "-":
                    first_empty_board[each_location[0]][each_location[1]] = each_ship_name[0]


with open("Battleship.out.txt", "w", encoding="utf-8") as output_file:
    try:
        player_one_location = sys.argv[1]
        player_two_location = sys.argv[2]
        player_one_input = sys.argv[3]
        player_two_input = sys.argv[4]
    except IndexError:
        print(f"IndexError: You entered less argument than expected.")
        output_file.write(f"IndexError: You entered less argument than expected.\n")

    else:
        unopened_files = []
        try:
            first_player_locating = open(player_one_location, "r", encoding="utf-8")
        except:
            unopened_files.append(f"{player_one_location}")
        try:
            second_player_locating = open(player_two_location, "r", encoding="utf-8")
        except:
            unopened_files.append(f"{player_two_location}")
        try:
            first_player_index = open(player_one_input, "r", encoding="utf-8")
        except:
            unopened_files.append(f"{player_one_input}")
        try:
            second_player_index = open(player_two_input, "r", encoding="utf-8")
        except:
            unopened_files.append(f"{player_two_input}")

        try:
            if len(unopened_files) == 1:
                raise IOError
        except IOError:
            print(f"IOError: input file {unopened_files[0]} is not reachable.")
            output_file.write(f"IOError: input file {unopened_files[0]} is not reachable.\n")

        output = ""
        try:
            if len(unopened_files) > 1:
                output = ", ".join(unopened_files)
                raise IOError
        except IOError:
            print(f"IOError: input files {output} are not reachable.")
            output_file.write(f"IOError: input files {output} are not reachable.\n")

        if len(unopened_files) == 0:  # It means every file opened properly.
            try:
                reading_locations(first_player_locating)
                reading_locations(second_player_locating)
            except IndexError:
                print("You entered less index than expected.")
                output_file.write("You entered less index than expected.\n")
            except ValueError:
                print("You didn't enter valid input")
                output_file.write("You didn't enter valid input\n")

            else:
                first_player_locating.close()
                second_player_locating.close()
                first_players_hidden_board = create_board(player_one_location, "Player1")
                second_players_hidden_board = create_board(player_two_location, "Player2")
                first_empty_board = create_empty_board()
                second_empty_board = create_empty_board()
                read_moves()
# İbrahim Emek 2210356032
