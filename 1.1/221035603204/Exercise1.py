#!/usr/bin/env python
# coding: utf-8

# In[12]:


n = int(input("Enter a number N: "))
sum_even = 0
sum_odd = 0
num_even = 0

for ints in range(1 , n + 1):
    if ints % 2 == 0:
        sum_even = sum_even + ints
        num_even = num_even + 1
    if ints % 2 == 1:
        sum_odd = sum_odd + ints
if n > 1:
    average = sum_even / num_even
if n == 1:
    average = 0
print ("Average of evens:" , average)
print ("Sum of odds:" , sum_odd)


# In[ ]:




