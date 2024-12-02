import sys
file_name = sys.argv[1]
names = []
universities = []

with open(file_name, "r", encoding="utf-8") as input_file:
    while True:
        each_line = input_file.readline()
        if each_line == "" or each_line == "\n":
            break
        item_list = each_line.split(":")
        names.append(item_list[0])
        universities.append(item_list[1])

user_input = sys.argv[2]
input_list = user_input.split(",")

for each_input in input_list:
    try:
        location = names.index(each_input)
        print(f"Name: {each_input}, University: {universities[location]}", end="")

    except:
        print(f"No record of {each_input} was found.")
#  İbrahim Emek 2210356032


