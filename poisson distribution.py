# -*- coding: utf-8 -*-
# file_name.py
# Python 3.6
"""
Author: Diana Kafkes
Created: Thu Sep 14 21:59:56 2017
Modified: Thu Sep 14 21:59:56 2017

Description
___________________
"""

import numpy as np
import matplotlib.pyplot as plt
from numpy.random import random as rng
from scipy.special import factorial

numtrials = 10000
numberflips = 100
psuccess = 0.08

def poisson(successes, probability, trials) :
    probability_of_l_successes = np.exp(-1 * probability * trials) * (8**successes)/factorial(successes)
    return probability_of_l_successes

def heads_from_trials(numtrials, numflips, probability):
    numheads = np.zeros(numtrials)
    trialarray = (rng((numflips,numtrials)) < probability)
    for i in range(numtrials):
        numheads[i] = np.sum(trialarray[:,i])
    return numheads


plt.figure("Poisson distribution for different values")
lvals = np.arange(1,30,1)
funcvals = poisson(lvals, psuccess, numberflips) 
plt.plot(lvals,funcvals)

numbersuccesses = np.zeros(numberflips)

plt.figure("Histogram of number of heads per trial")
numbersuccesses = heads_from_trials(numtrials,numberflips,psuccess)
plt.hist(numbersuccesses, ec = "black")
plt.plot(lvals,funcvals*numtrials)