# -*- coding: utf-8 -*-
# file_name.py
# Python 3.6
"""
Author: Diana Kafkes
Created: Thu Oct 26 21:42:27 2017
Modified: Thu Oct 26 21:42:27 2017

Description
___________________
"""
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

from scipy.integrate import quad
from math import e
from pylab import xlabel, ylabel

def integralreal(x):
    return (e**(1j*x**2)).real

def integralimag(x):
    return (e**(1j*x**2)).imag

xvals=np.arange(0,20, .01)

real=[]
imaginary=[]

for i in range(0,len(xvals)):
    a, realerror= quad(integralreal, 20, 20+2*i)
    b, imagerror= quad(integralimag, 20, 20+2*i)
    
    real.append(a)
    imaginary.append(b)
    
plt.plot(xvals,real, 'r')
plt.plot(xvals,imaginary, 'b')
plt.title('Fresnel Integral')
red_patch= mpatches.Patch(color='red', label= 'Real')
blue_patch=mpatches.Patch(color='blue', label= 'Imaginary')
plt.legend(handles=[red_patch, blue_patch])
xlabel('B')
ylabel('integral value')