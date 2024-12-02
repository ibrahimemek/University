import sys
file = sys.argv[1]

with open(file, "a", encoding="utf-8") as f:
    f.write("\n")  # The last line doesn't work because of lack of new line at the last line, so I appended it.
    f.close()

categories = {}
alphabet = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z']


def create_category():
    category_name = line_items[1]
    seat_numbers = line_items[2]
    x_location = seat_numbers.find("x")
    row_number = int(seat_numbers[0: x_location])
    column_number = int(seat_numbers[x_location + 1:])

    if category_name not in categories:
        categories[category_name] = []
        for x in range(row_number):
            a_list = []
            for y in range(column_number):
                a_list.append("X")
            a_list.append("\n")
            categories[category_name].append(a_list)
        print(f"The category '{category_name}' having {row_number * column_number} seats has been created")
        return f"The category '{category_name}' having {row_number * column_number} seats has been created\n"
    else:
        print(f"Warning: Cannot create the category for the second time. The stadium has already {category_name}")
        return f"Warning: Cannot create the category for the second time. The stadium has already {category_name}\n"


def sell_ticket():
    sell_output = ""
    for q in range(4, len(line_items)):  # the items correspond seat numbers start at 4, and it last until the end item.
        if "-" in line_items[q]:
            new_alphabet = alphabet[0: len(categories[line_items[3]])]
            dash_location = line_items[q].find("-")
            num1 = int(line_items[q][1: dash_location])
            num2 = int(line_items[q][dash_location + 1:])
            if num2 > len(categories[line_items[3]][q]) or line_items[q][0] not in new_alphabet:
                if num2 > len(categories[line_items[3]][q]) and line_items[q][0] not in new_alphabet:
                    print(f"Error: The category {line_items[3]} has less column and row than the specified index {line_items[q]}!")
                    sell_output += f"Error: The category {line_items[3]} has less column and row than the specified index {line_items[q]}!\n"
                elif num2 > len(categories[line_items[3]][q]):
                    print(f"Error: The category {line_items[3]} has less column than the specified index {line_items[q]}!")
                    sell_output += f"Error: The category {line_items[3]} has less column than the specified index {line_items[q]}!\n"
                else:
                    print(f"Error: The category {line_items[3]} has less row than the specified index {line_items[q]}!")
                    sell_output += f"Error: The category {line_items[3]} has less row than the specified index {line_items[q]}!\n"
            else:
                number1 = int(alphabet.index(line_items[q][0]))
                did_buy = True
                for p in range(num1, num2 + 1):
                    will_change = categories[line_items[3]][number1][p]
                    if will_change != "X":
                        did_buy = False

                if did_buy:
                    for p in range(num1, num2 + 1):
                        will_change = categories[line_items[3]][number1][p]
                        if will_change == "X":
                            if line_items[2] == "student":
                                categories[line_items[3]][number1][p] = "S"
                            elif line_items[2] == "full":
                                categories[line_items[3]][number1][p] = "F"
                            elif line_items[2] == "season":
                                categories[line_items[3]][number1][p] = "T"
                    print(f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}")
                    sell_output += f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}\n"
                else:  # it means something went wrong, and the ticket isn't sold.
                    print(f"Warning: The seats {line_items[4]} cannot be sold to {line_items[1]} "
                          f"due some them have already been sold")
                    sell_output += f"Warning: The seats {line_items[4]} cannot be sold to {line_items[1]} due some " \
                                   f"of them have already been sold\n"
        else:  # if - is not in seat number
            new_alphabet = alphabet[0: len(categories[line_items[3]])]
            if int(line_items[q][1:]) > len(categories[line_items[3]][q]) or line_items[q][0] not in new_alphabet:
                if int(line_items[q][1:]) > len(categories[line_items[3]][0]) and line_items[q][0] not in new_alphabet:
                    print(f"Error: The category {line_items[3]} has less column and row than the specified index {line_items[q]}!")
                    sell_output += f"Error: The category {line_items[3]} has less column and row than the specified index {line_items[q]}!\n"
                elif int(line_items[q][1:]) > len(categories[line_items[3]][0]):
                    print(f"Error: The category {line_items[3]} has less column than the specified index {line_items[q]}!")
                    sell_output += f"Error: The category {line_items[3]} has less column than the specified index {line_items[q]}!\n"
                else:
                    print(f"Error: The category {line_items[3]} has less row than the specified index {line_items[q]}!")
                    sell_output += f"Error: The category {line_items[3]} has less row than the specified index {line_items[q]}!\n"
            else:
                number1 = int(alphabet.index(line_items[q][0]))
                will_change = categories[line_items[3]][number1][int(line_items[q][1:])]
                if will_change == "X":
                    if line_items[2] == "student":
                        categories[line_items[3]][number1][int(line_items[q][1:])] = "S"
                        print(f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}")
                        sell_output += f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}\n"
                    elif line_items[2] == "full":
                        categories[line_items[3]][number1][int(line_items[q][1:])] = "F"
                        print(f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}")
                        sell_output += f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}\n"
                    elif line_items[2] == "season":
                        categories[line_items[3]][number1][int(line_items[q][1:])] = "T"
                        print(f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}")
                        sell_output += f"Success: {line_items[1]} has bought {line_items[q]} at {line_items[3]}\n"
                else:
                    print(f"Warning: The seats {line_items[4]} cannot be sold to {line_items[1]} since it was already "
                          f"sold")
                    sell_output += f"Warning: The seats {line_items[4]} cannot be sold to {line_items[1]} since it " \
                                   f"was already sold\n"
    return sell_output


def cancel_ticket():
    cancel_output = ""
    for c in range(2, len(line_items)):
        new_alphabet = alphabet[0: len(categories[line_items[1]])]
        if int(line_items[c][1:]) > len(categories[line_items[1]][c]) or line_items[c][0] not in new_alphabet:
            if int(line_items[c][1:]) > len(categories[line_items[1]][c]) and line_items[c][0] not in new_alphabet:
                print(f"Error: The category '{line_items[1]}' has less column and row than the specified index {line_items[c]}!")
                cancel_output += f"Error: The category '{line_items[1]}' has less column and row than the specified index {line_items[c]}!\n"
            elif int(line_items[c][1:]) > len(categories[line_items[1]][c]):
                print(f"Error: The category '{line_items[1]}' has less column than the specified index {line_items[c]}!")
                cancel_output += f"Error: The category '{line_items[1]}' has less column than the specified index {line_items[c]}!\n"
            else:
                print(f"Error: The category '{line_items[1]}' has less row than the specified index {line_items[c]}!")
                cancel_output += f"Error: The category '{line_items[1]}' has less row than the specified index {line_items[c]}!\n"

        else:
            number1 = int(alphabet.index(line_items[c][0]))
            will_change = categories[line_items[1]][number1][int(line_items[c][1:])]
            if will_change != "X":
                categories[line_items[1]][number1][int(line_items[c][1:])] = "X"
                print(f"Success: The seat {line_items[2]} at {line_items[1]} has been canceled and now ready to sell again")
                cancel_output += f"Success: The seat {line_items[2]} at {line_items[1]} has been canceled and now ready to sell again\n"
            else:
                print(f"Error: The seat {line_items[2]} at {line_items[1]} has already been free! Nothing to cancel")
                cancel_output += f"Error: The seat {line_items[2]} at {line_items[1]} has already been free! Nothing to cancel\n"
    return cancel_output


def balance():
    balance_category = categories[line_items[1]]
    student_number = 0
    full_number = 0
    season_number = 0
    revenue = 0
    for i in range(len(balance_category)):
        for h in range(len(balance_category[i])):
            if balance_category[i][h][0] == "S":
                student_number += 1
                revenue += 10
            elif balance_category[i][h][0] == "F":
                full_number += 1
                revenue += 20
            elif balance_category[i][h][0] == "T":
                season_number += 1
                revenue += 250
            else:
                pass
    print(f"Category report of '{line_items[1]}'\n--------------------------------")
    print(f"Sum of students = {student_number}, Sum of full pay = {full_number}, Sum of season ticket = {season_number}"
          f", and Revenues = {revenue} Dollars")
    return f"Category report of '{line_items[1]}'\n--------------------------------\nSum of students = " \
           f"{student_number}, Sum of full pay = {full_number}, Sum of season ticket = {season_number}, and Revenues" \
           f" = {revenue} Dollars\n"


def show_category():
    layout = ""
    layout_category = categories[line_items[1]]
    row_order = len(layout_category) - 1
    first_line = f"Printing category layout of {line_items[1]}\n"
    layout += first_line
    while row_order >= 0:
        layout += f"{alphabet[row_order]}"
        for each_seat in layout_category[row_order]:
            layout += f"  {each_seat}"
        row_order -= 1
    last_line = "  "
    for u in range(len(layout_category[1]) - 1):
        if u < 10:
            last_line += f" {u} "
        else:
            last_line += f"{u} "
    layout += last_line
    print(layout)
    layout += "\n"
    return layout


with open("output.txt", "w", encoding="utf-8") as output_file:
    with open(file, "r", encoding="utf-8") as input_file:
        while True:
            each_line = input_file.readline()
            if each_line == "" or each_line == "\n":
                break

            line_items = each_line[0: -1].split(" ")
            command = line_items[0]

            if command == "CREATECATEGORY":
                output_file.write(create_category())

            if command == "SELLTICKET":
                output_file.write(sell_ticket())

            if command == "CANCELTICKET":
                output_file.write(cancel_ticket())

            if command == "BALANCE":
                output_file.write(balance())

            if command == "SHOWCATEGORY":
                output_file.write(show_category())
        # İbrahim Emek 2210356032
