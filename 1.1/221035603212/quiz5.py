import sys

can_open_files = True
try:
    operands = sys.argv[1]
    comparison_data = sys.argv[2]
    try:
        comparison_data_list = []
        with open(comparison_data, "r", encoding="utf-8") as file_two:
            while True:
                per_line = file_two.readline()
                per_line = per_line.strip()
                if per_line == "":
                    break
                comparison_data_list.append(per_line.split(" "))
    except IOError:
        can_open_files = False
        print(f"IOError: cannot open {comparison_data}.")
    except:
        print("kaBOOM: run for your life!")

    try:
        with open(operands, "r", encoding="utf-8") as file_one:
            i = 0
            while can_open_files:
                each_line = file_one.readline()
                each_line = each_line.strip()
                if each_line == "":
                    break
                try:
                    each_item = each_line.split(" ")
                    div_key = float(each_item[0])
                    dot_location = str(div_key).index(".")
                    if str(div_key)[dot_location:] >= "5":
                        div_key = int(div_key) + 1
                    else:
                        div_key = int(div_key)
                    nondiv_key = float(each_item[1])
                    dot_location = str(nondiv_key).index(".")
                    if str(nondiv_key)[dot_location:] >= "5":
                        nondiv_key = int(nondiv_key) + 1
                    else:
                        nondiv_key = int(nondiv_key)
                    from_key = float(each_item[2])
                    dot_location = str(from_key).index(".")
                    if str(from_key)[dot_location:] >= "5":
                        from_key = int(from_key) + 1
                    else:
                        from_key = int(from_key)
                    to_key = float(each_item[3])
                    dot_location = str(to_key).index(".")
                    if str(to_key)[dot_location:] >= "5":
                        to_key = int(to_key) + 1
                    else:
                        to_key = int(to_key)
                    try:
                        result_data = []
                        for numbers in range(from_key, to_key + 1):
                            if numbers % div_key == 0 and numbers % nondiv_key != 0:
                                result_data.append(f'{numbers}')
                    except ValueError:
                        print("------------")
                        print("ValueError: only numeric input is accepted.")
                        print(f"Given input: {each_line}")
                        i += 1
                    except ZeroDivisionError:
                        print("------------")
                        print("ZeroDivisionError: You can't divide by 0.")
                        print(f"Given input: {each_line}")
                        i += 1
                    except:
                        print("------------")
                        print("kaBOOM: run for your life!")
                        print(f"Given input: {each_line}")
                        i += 1
                    else:
                        try:
                            assert result_data == comparison_data_list[i]
                            print("------------")
                            result_string = " ".join(result_data)
                            print(f"My results:\t\t{result_string}")
                            comparison_data_string = " ".join(comparison_data_list[i])
                            print(f"Results to compare:\t{comparison_data_string}")
                            print("Goool!!!")
                        except AssertionError:
                            print("------------")
                            result_string = " ".join(result_data)
                            print(f"My results:\t\t{result_string}")
                            comparison_data_string = " ".join(comparison_data_list[i])
                            print(f"Results to compare:\t{comparison_data_string}")
                            print("Assertion Error: results don't match")
                        except:
                            print("------------")
                            print("kaBOOM: run for your life!")
                            print(f"Given input: {each_line}")
                        i += 1
                except IndexError:
                    print("------------")
                    print("IndexError: number of operands less than expected.")
                    print(f"Given input: {each_line}")
                    i += 1
                except ValueError:
                    print("------------")
                    print("ValueError: only numeric input is accepted.")
                    print(f"Given input: {each_line}")
                    i += 1
                except:
                    print("------------")
                    print("kaBOOM: run for your life!")
                    print(f"Given input: {each_line}")
                    i += 1
    except IOError:
        print(f"IOError: cannot open {operands}")
        can_open_files = False
    except:
        print("------------")
        print("kaBOOM: run for your life!")
        print(f"Given input: {each_line}")
except IndexError:
    print("IndexError: number of inputs files less than expected.")
except:
    print("kaBOOM: run for your life!")
finally:
    print("Game Over")

# İbrahim Emek 2210356032

