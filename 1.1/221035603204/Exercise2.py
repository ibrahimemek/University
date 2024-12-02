#!/usr/bin/env python
# coding: utf-8

# In[10]:


mail = str(input("Enter your e-mail adress: "))
x = 0
word_str = "@" , "."
for s in word_str:
    if s in mail:
        x = x + 1
if x == 2:
    print ("It is a valid e-mail.")
else:
    print ("It is not a valid e-mail.")


# In[ ]:




