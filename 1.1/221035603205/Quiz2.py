try:
    import sys

    i = sys.argv[1] #2 points
    ii = sys.argv[2] #3 points
    iii = sys.argv[3] #1 points

    score = int(i) * 2 + int(ii) * 3 + int(iii) * 1
    print(score)

except:
    print(-1)


def healthStatus(height, mass):
    bmi = mass / (height * height)

    if bmi >= 30:
      return ("obese")
    elif bmi >= 24.9:
      return ("overweight")
    elif bmi >= 18.5:
      return ("healthy")
    else:
      return ("underweight")

# İbrahim Emek  2210356032