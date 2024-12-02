#!/usr/bin/env python
# coding: utf-8

# In[ ]:


x = int(input("Enter a number: "))
y = 2
z = 1
binfor = ""
while x / y > 2:
    y = y*2
    z = z + 1
binfor = "1" + binfor
x = x - 2**z
z = z - 1
while z >= 0:
    if x / 2**z >= 1:
        binfor = binfor + "1"
        x = x - 2**z
        z = z - 1
        
    else:
        binfor = binfor + "0"
        z = z-1
print (binfor)

