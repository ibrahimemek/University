#!/usr/bin/env python
# coding: utf-8

# In[4]:


x = int(input("Enter a year: "))
if x % 4 == 0:
    if x % 100 == 0:
        if x % 400 == 0:
            print("It is a leap year.")
        else :
            print("It is not a leap year.")
    else:
        print("It is a leap year.")
else:
    print("It is not a leap year.")


# In[ ]:




