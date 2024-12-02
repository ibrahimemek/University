dict = {}
n = int(input("Enter a number: "))
for each_iteration in range(n):
    dict[str(each_iteration)] = each_iteration * "*"
print(dict)
