user_input = input("Enter a list: ")
n = int(input("What is the biggest number you want: "))
user_list = user_input.split(" ")
for each_num in range (len(user_list)):
    user_list[each_num] = int(user_list[each_num])
print(sorted(user_list)[n - 1])


