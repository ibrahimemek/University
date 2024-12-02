patients_info = []

with open("doctors_aid_inputs.txt", "a", encoding="utf-8") as file:  # The program can't read the last line properly.
    # because of lack of new line at the end, so I added a new line to last line.
    file.write("\n")


def write_output_file():
    if command == "create":  # Command is first word which tells us what to do.
        create_result = create()
        output_file.write(create_result)

    elif command == "remove":
        delete_result = delete()
        output_file.write(delete_result)

    elif command == "list":
        list_result = list_of_data()  # list is built-in name, so I changed it.
        output_file.write(list_result)

    elif command == "probability":
        probability_result = probability()
        output_file.write(probability_result)

    elif command == "recommendation":
        recommendation_result = recommendation()
        output_file.write(recommendation_result)


def read_input_file():
    while True:
        each_line = file.readline()
        if each_line == "" or each_line == "\n":  # Reads until an empty line
            break
        blank_location = each_line.find(" ")
        global command
        command = each_line[0: blank_location]  # First word until first whitespace is command
        rest_sentence = each_line[blank_location + 1: -1]
        global list_of_items  # we will use this list everywhere, so I made it global
        list_of_items = rest_sentence.split(",")
        write_output_file()


def create():
    patient_name = list_of_items[0]
    diagnosis_accuracy = list_of_items[1]
    disease_name = list_of_items[2]
    disease_incidence = list_of_items[3]
    treatment_name = list_of_items[4]
    treatment_risk = list_of_items[5]
    person = [patient_name, diagnosis_accuracy, disease_name, disease_incidence, treatment_name, treatment_risk]
    # I assigned them to variables in order to make the code more readable.
    if (len(patients_info)) > 1:
        for x in range(len(patients_info)):
            name = patients_info[x][0]
            if name == patient_name:
                return f"Patient {patient_name} cannot be recorded due to duplication.\n"
        patients_info.append(person)
        return f"Patient {patient_name} is recorded.\n"
    else:
        patients_info.append(person)
        return f"Patient {patient_name} is recorded.\n"


def delete():
    name_to_delete = list_of_items[0]
    for x in range(len(patients_info)):
        a = patients_info[x][0]
        if a == name_to_delete:
            del patients_info[x]
            return f"Patient {name_to_delete} is removed.\n"
    return f"Patient {name_to_delete} cannot be removed due to absence.\n"


def probability():
    name_to_calculate = list_of_items[0]
    for x in range(len(patients_info)):
        a = patients_info[x][0]
        if a == name_to_calculate:
            disease_name = patients_info[x][2]
            disease_incidence = patients_info[x][3]
            diagnosis_accuracy = patients_info[x][1]
            divide_location = disease_incidence.find("/")
            numerator = float(disease_incidence[0: divide_location])
            denominator = float(disease_incidence[divide_location + 1:])
            cancer_probability = 100 * numerator / ((denominator - numerator) * (1 - float(diagnosis_accuracy)) + numerator)
            cancer_probability = round(cancer_probability, 2)
            try:  # I called this function in recommendation function, so I need to look why I called this function.
                if probability_for_recommendation:
                    return cancer_probability
                else:
                    return f"Patient {name_to_calculate} has a probability of {cancer_probability}% of having {disease_name}.\n"
            except:  # If it gives an error it means that probability_for_recommendation is undefined, so this function is not called for recommendation function.
                return f"Patient {name_to_calculate} has a probability of {cancer_probability}% of having {disease_name}.\n"
    return f"Probability for {name_to_calculate} cannot be calculated due to absence.\n"


def recommendation():
    global probability_for_recommendation
    probability_for_recommendation = True  # If I call the probability function for recommendation function, it only returns to cancer probability.
    disease_probability = probability()
    probability_for_recommendation = False
    name_to_recommend = list_of_items[0]
    for q in range(len(patients_info)):
        if name_to_recommend == patients_info[q][0]:
            treatment_risk = float(patients_info[q][5]) * 100
            name_to_recommend = patients_info[q][0]
            if float(treatment_risk) > float(disease_probability):
                return f"System suggests {name_to_recommend} NOT to have the treatment.\n"
            elif float(treatment_risk) < float(disease_probability):
                return f"System suggests {name_to_recommend} to have the treatment.\n"
    return f"Recommendation for {name_to_recommend} cannot be calculated due to absence.\n"


def list_of_data():
    first_line = f'{"Patient": <8}{"Diagnosis": <16}{"Disease": <16}{"Disease": <12}{"Treatment": <16}{"Treatment"}'
    second_line = f'{"Name": <8}{"Accuracy": <16}{"Name": <16}{"Incidence": <12}{"Name": <16}{"Risk"}'
    third_line = "-" * 77
    all_person_list = ""
    for person_number in range(len(patients_info)):
        patient_name = patients_info[person_number][0]
        diagnosis_accuracy = float(patients_info[person_number][1]) * 100
        diagnosis_accuracy = "{:.2f}".format(diagnosis_accuracy)
        diagnosis_accuracy = str(diagnosis_accuracy) + "%"
        disease_name = patients_info[person_number][2]
        disease_incidence = patients_info[person_number][3]
        treatment_name = patients_info[person_number][4]
        treatment_risk = float(patients_info[person_number][5]) * 100
        if treatment_risk == int(treatment_risk):
            treatment_risk = int(treatment_risk)
        treatment_risk = str(treatment_risk) + "%"
        each_person_list = f"{patient_name: <8}{diagnosis_accuracy: <16}{disease_name: <16}{disease_incidence: <12}{treatment_name: <16}{treatment_risk}\n"
        all_person_list += each_person_list
    return f"{first_line}\n{second_line}\n{third_line}\n{all_person_list}"


with open("doctors_aid_outputs.txt", "w", encoding="utf-8")as output_file:
    with open("doctors_aid_inputs.txt", "r", encoding="utf-8") as file:
        read_input_file()
# İbrahim Emek 2210356032