# -*- coding: utf-8 -*-
"""
Created on Fri Apr 26 14:58:41 2019

@author: chris
"""

import numpy as np
import matplotlib.pyplot as plt
import sys

X = []
Y = []

for line in open('data_1d.csv'):
    x, y = line.split(',')
    X.append(float(x))
    Y.append(float(y))
    
X = np.array(X)
Y = np.array(Y)
    
plt.scatter(X, Y)
plt.show()

denominator = X.dot(X) - X.mean() * X.sum()
a = (X.dot(Y) - Y.mean() * X.sum()) / denominator
b = (Y.mean() * X.dot(X) - X.mean() * X.dot(Y)) / denominator

Yhat = a*X + b

plt.scatter(X, Y)

plt.plot(X, Yhat)

plt.show()