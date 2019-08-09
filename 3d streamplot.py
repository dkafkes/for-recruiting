# -*- coding: utf-8 -*-
"""
Created on Fri Apr 26 00:49:59 2019

@author: diana
"""

#import necessary modules, functions, and pi
import numpy as np; import matplotlib.pyplot as plt
from scipy.integrate import odeint, quad
from mpl_toolkits.mplot3d import Axes3D
from matplotlib.patches import Circle, PathPatch
import mpl_toolkits.mplot3d.art3d as art3d
from math import pi as pi

#define constants
#decided to simplify and set both radius and current equal to 1
mu_0 = 4*pi*(10**(-7))
radius = 1
I = 1
c = 1 
w = 2*pi*c/3/radius

#lump common constants together into one factor
A = mu_0*I*radius/4/pi

#define the function that I will be running through the odeint solver
#function Biot_Savart takes in a position tuple (x,y,z) and returns the (B_x,B_y,B_z) at that point
def Biot_Savart_time_dependent(r, t):
    x,y,z = r
 
    #define necessary subfunctions to separately calculate B_x, B_y, B_z component
    #see attached derivation
    def x_direct(phi, T):
        R_xy = np.sqrt(radius**2+x**2+y**2+z**2-2*y*radius*np.sin(phi)-2*x*radius*np.cos(phi))
        arg_xy = w*T -w/c*R_xy
        
        R_xz = np.sqrt(radius**2+x**2+y**2+z**2-2*x*radius*np.cos(phi)-2*z*radius*np.sin(phi))
        arg = w*T -w/c*R_xz +pi/2
        
        return(-np.cos(phi)/R_xy**2*(w/c*np.sin(arg_xy)-np.cos(arg_xy)/R_xy) - np.cos(phi)/R_xz**2*(-w/c*np.sin(arg)+np.cos(arg)/R_xz))


    def y_direct(phi, T):
        R_xy = np.sqrt(radius**2+x**2+y**2+z**2-2*y*radius*np.sin(phi)-2*x*radius*np.cos(phi))
        arg_xy = w*T -w/c*R_xy
        
        R_xz = np.sqrt(radius**2+x**2+y**2+z**2-2*x*radius*np.cos(phi)-2*z*radius*np.sin(phi))
        arg_xz = w*T -w/c*R_xz +pi/2
        
        return(-np.sin(phi)/R_xy**2*(w/c*np.sin(arg_xy)-np.cos(arg_xy)/R_xy) + (z*np.sin(phi)+x*np.cos(phi)-radius)/R_xz**2*(-w/c*np.sin(arg_xz)+np.cos(arg_xz)/R_xz))
    
    def z_direct(phi, T):
        R_xy = np.sqrt(radius**2+x**2+y**2+z**2-2*y*radius*np.sin(phi)-2*x*radius*np.cos(phi))
        arg_xy = w*T -w/c*R_xy
        
        R_xz = np.sqrt(radius**2+x**2+y**2+z**2-2*x*radius*np.cos(phi)-2*z*radius*np.sin(phi))
        arg_xz = w*T -w/c*R_xz +pi/2
        
        return((x*np.cos(phi)+y*np.sin(phi)-radius)/R_xy**2*(w/c*np.sin(arg_xy)-np.cos(arg_xy)/R_xy) - np.sin(phi)/R_xz**2*(-w/c*np.sin(arg_xz)+np.cos(arg_xz)/R_xz))
        
    #set the time
    T = 0
    
    #perform integration and grab the correct value from the array output by quad command
    int_x = quad(x_direct, 0, 2*pi, args=(T))[0]
    
    int_y = quad(y_direct, 0, 2*pi, args=(T))[0]
    
    int_z = quad(z_direct, 0, 2*pi, args=(T))[0]
    
    #normalize the B components
    normalize = np.sqrt(int_x**2+int_y**2+int_z**2)
    
    B_x= int_x/normalize
    B_y = int_y/normalize
    B_z = int_z/normalize
            
    return[B_x, B_y, B_z]
    
#%%
    
#create time array
dt = 0.1
times = np.arange(0., 100.+dt, dt)

#create four initial conditions
IC =[]
IC.append([[.9,0,0], [2.5,0,0], [1.25,0,0], [4,0,0]])
flattened = [val for sublist in IC for val in sublist]

#%%
myfig = plt.figure()
myax = Axes3D(myfig)

#plot the wire loop
p = Circle([0,0], radius = 1, fill=0)
q = Circle([0,0], radius = 1, fill=0)
myax.add_patch(p)
myax.add_patch(q)
art3d.pathpatch_2d_to_3d(p, z=0, zdir="z")
art3d.pathpatch_2d_to_3d(q, z=0, zdir="y")

#%%

for i in flattened:
    #plot both the positive and negative solutions to complete the loops
    out_pos = odeint(Biot_Savart_time_dependent, i, times)
    myax.plot(out_pos[:,0], out_pos[:,1], out_pos[:,2])
    myax.plot(-out_pos[:,0], -out_pos[:,1], -out_pos[:,2])
myax.set_xlabel('x'); myax.set_ylabel('y'); myax.set_zlabel('z')
plt.show()
