import sys

first_num = int(sys.argv[1])
second_num = int(sys.argv[2])
sum = first_num ** second_num

print(f"{first_num}^{second_num} = {sum}", end = "")


while len(str(sum)) > 1:
    sum_of_digits = 0
    output_string = ""
    for each_digit in str(sum):
        sum_of_digits += int(each_digit)
        sum = sum_of_digits
        output_string += each_digit + " + "
    output_string = output_string[:len(output_string) - 3]
    print(f" = {output_string} = {sum}", end = "")

#İbrahim Emek 2210356032