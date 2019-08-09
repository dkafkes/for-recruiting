# -*- coding: utf-8 -*-
# file_name.py
# Python 3.6
"""
Author: Diana Kafkes
Created: Tue Nov 14 23:09:32 2017
Modified: Tue Nov 14 23:09:32 2017

Description
___________________
"""
import numpy as np
import matplotlib.pyplot as plt

from math import pi

plt.close('all')

#create variables
w=5e-3
x=w
d=1/3.
lamb=500e-9
f=w
#for focused case, f=.25/ for unfocused case, f=.2/ for no lens set f=large
L=1
A=2*pi*1j/lamb

us=np.linspace(-w/2, w/2, 600) #subdivide the width by 600 to get 600 values
 
#correct for fact that np.quiver starts with end of first arrow by adding
#zero as first entry of list
riemann=[0]

#run for loop to get probability amplitude approximation by evaluating light
#hypothesis integrand at range and riemann sum approximating by multiplying by
#width of interval then append to riemann list 
for i in range(len(us)):
    integrand=np.exp(A*((-w/2+i*w/600)**(2)/2*(L**(-1)+d**(-1)-f**(-1))-(-w/2+i*w/600)*x/d))
    newentry=integrand*w/600
    riemann.append(newentry)
    
#remove last entry of list to correct for fact that np. quiver
#ends at end of last arrow, but we want to end it at the start of the last
#arrow because that's our final sum
expunge=riemann.pop() 

#creating proxy for riemann now that we have removed last entry
basepoints=riemann

#find modulus of overall length of vector at x=0, and multiple basepoints by it
#to normalize so that prob amp has modulus equal to 1.


#initialize value of factor for when evaluating x!=0..replace this value after
#running code at x=0 for each of the three cases at x=0 with the proper value
#to normalize which will be printed out wohoo

normalmag=8.33333333333e-06

#plug in values here based on what python prints after running at x=0

if x==0:
    normalmag=np.absolute(riemann[-1]-riemann[0])
    print(normalmag)

#creating new lists to go along with the argument input of np.quiver
a=[0]
b=[0]
u=[]
v=[]

for i in range(len(basepoints)):
    ww=basepoints[i]/normalmag #normalize
#running basepoints through loop that iterates through each value and splits
#into real and imaginary values as well as real and imaginary sums up to that
#specific iteration
    u+=[ww.real]
    v+=[ww.imag]
    a+=[np.sum(u)]
    b+=[np.sum(v)]
    
#plotting  
plt.quiver(a,b,u,v, units='xy', scale=1)
plt.title('No Lens System, x=w') #change title
plt.axis('equal')