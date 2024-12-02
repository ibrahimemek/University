dict_one = {
    "min_num": [],
    "max_num": [],
    "total": 0,
    "item_num": 0
}


def rec_funct(list1):
    print(list1)
    cur_item = list1[-1]
    print(cur_item)
    if len(dict_one["min_num"]) == 0:
        dict_one["min_num"].append(int(cur_item))
    elif int(cur_item) < dict_one["min_num"][0]:
        dict_one["min_num"][0] = int(cur_item)
    if len(dict["max_num"]) == 0:
        dict_one["max_num"].append(int(cur_item))
    elif int(cur_item) > dict_one["max_num"][0]:
        dict_one["max_num"][0] = int(cur_item)
    dict_one["total"] += int(cur_item)
    dict_one["item_num"] += 1
    if len(list1) != 1:
        return rec_funct(list1[0: -1])
    else:
        print(f'minimum number = {dict_one["min_num"]}')
        print(f'maximum number = {dict_one["max_num"]}')
        print(f'average = {dict_one["total"] / dict_one["item_num"]}')

# İbrahim Emek 2210356032
