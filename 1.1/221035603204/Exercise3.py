#!/usr/bin/env python
# coding: utf-8

# In[6]:


import random
number = int(random.randint(1,25))

guess = int(input("Guess a number between 1-25: "))

while number != guess:
    if guess < number:
        guess = int(input("Increase your number: "))
        
    else:
        guess = int(input("Decrease your number: "))

print("You won.")


# In[ ]:




