import sys
input_text = sys.argv[1]
output_text = sys.argv[2]

with open(input_text, "a") as input_file:
    input_file.write("\n")  # If I don't add new line to the end of input file, it writes output wrong.

with open(output_text, "w", encoding="utf-8") as output_file:
    with open(input_text, "r", encoding="utf-8") as input_file:
        order = []
        message_list = []
        order_list = []
        sentence_list = []
        sorted_message_list = []
        all_greatest_numbers = []

        while True:  # Reads every line until the line is blank.
            each_line = input_file.readline()
            if each_line == "" or each_line == "\n":
                break

            each_item = each_line.split("\t")
            message_list.append(each_item[0])
            order_list.append(each_item[1])
            sentence_list.append(each_item[2])

        for each_number in message_list:
            if each_number not in sorted_message_list:  # Saves every number once.
                sorted_message_list.append(each_number)
        sorted_message_list = sorted(sorted_message_list, key=int)  # Sorts message IDs.

        for message_order in sorted_message_list:
            greatest_number = 0
            current_list = []
            for locations in range(len(message_list)):
                if message_list[locations] == message_order:
                    current_list.append(locations)  # Saves locations of specific message IDs
            for each_location in current_list:  # Founds the greatest number of packet IDs.
                if int(order_list[each_location]) > greatest_number:
                    greatest_number = int(order_list[each_location])
            all_greatest_numbers.append(greatest_number)

            control_num = 0
            while control_num <= greatest_number:  # It reads until the last packet ID found in specific message ID.
                for x in current_list:
                    if int(order_list[x]) == control_num:
                        order.append(x)
                control_num += 1

        y = 0  # Order is not specific to a message ID, it is common to every message ID, so it must read until the end.
        for message_num in range(len(sorted_message_list)):
            output_file.write(f"Message\t{message_num + 1}\n")
            for m in range(all_greatest_numbers[message_num] + 1):
                output_file.write(f"{message_list[order[y]]}\t{order_list[order[y]]}\t{sentence_list[order[y]]}")
                y += 1

# İbrahim Emek 2210356032
