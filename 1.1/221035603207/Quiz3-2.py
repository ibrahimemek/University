import sys
string_lucky_numbers = sys.argv[1]
lucky_numbers_list = string_lucky_numbers.split(",")
at_number = 0
defined_number = int(lucky_numbers_list[at_number])
print("input -> ", end = "")
print(sys.argv[1].replace(",", " "))
print(f"output ->")
while len(lucky_numbers_list) > defined_number:
    if defined_number < 0:
        print("You should enter a positive number")
    elif defined_number == 1:
        del lucky_numbers_list[at_number + 1 :: defined_number + 1]
    else:
        del lucky_numbers_list[defined_number - 1 :: defined_number]

    try :
        defined_number = int(lucky_numbers_list[at_number + 1])
    except:
        pass
    output = ""
    for each_item in range(len(lucky_numbers_list)):
        output += lucky_numbers_list[each_item] + " "
    print(output)
    at_number += 1

# İbrahim Emek 2210356032