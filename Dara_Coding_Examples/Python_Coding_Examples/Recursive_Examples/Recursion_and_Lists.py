from __future__ import division
import random

#######################
#Binary search helper #
#######################

def binary_search_helper(a, x, i, n):
		if n == 0: # fast
			return False # fast
		index = i + n//2 # fast
		if x == a[index]:# fast
			return index # fast
		elif a[index] > x and a[index-1] < x:
			return index
		else:
			if x < a[index]:
				return binary_search_helper(a, x, i, n//2)
			else:
				return binary_search_helper(a, x, index+1, n-(n//2)-1)
				
#######################
#Helping sort function#
#######################

def quick_sort(a):
	if len(a) == 0:
		return a
	else:
		x = random.choice(a)
		small = [ y for y in a if y < x ]
		equal = [ y for y in a if y == x ]
		bigger = [ y for y in a if y > x ]
		return quick_sort(small) + equal + quick_sort(bigger)
	
#######################
#    Question 1       #
#######################
	
def recursive_binary_search_better(a,x): 
	b = quick_sort(a)
	i = binary_search_helper(b, x, 0, len(b))
	if i == False:
		return 
	else:
		for t in range(len(a)):
			if a[t] == b[i]:
				return t
		
print ("\nQUESTION 1 TEST \n")
print (recursive_binary_search_better([0,4,1,3,8,4,2], 10))
print (recursive_binary_search_better([0,4,1,3,8,4,2], 5))
print (recursive_binary_search_better(range(1000000),256))

print ("\n")
		
########################
#    Question 2        #
########################
		
def sum_of_two_largest(a):
	b = quick_sort(a)
	return b[len(b)-1] + b[len(b)-2]
	
print ("\nQUESTION 2 TEST \n")
print (sum_of_two_largest([0,2,4,6,8]))
print (sum_of_two_largest([8,12,4,9,6,3,10,2,15]))
print (sum_of_two_largest([5,9,3,24,6,2,7,14]))
print ("\n")


########################
#    Question 3        #
########################

def sum_of_smallest_half(a):
	b = quick_sort(a)
	sum = 0
	for i in range(len(b)//2):
		sum = sum + b[i]
	return sum
	
print ("\nQUESTION 3 TEST \n")
print (sum_of_smallest_half([0,2,4,6,8]))
print (sum_of_smallest_half([8,12,4,9,6,3,10,2,15]))
print (sum_of_smallest_half([7,3,4,12,5,7,7,1,3,22,3,5,15]))
print ("\n")

########################
#    Question 4        #
########################

def median(a):
	b = quick_sort(a)
	return b[len(b)//2]
	
print ("\nQUESTION 4 TEST \n")
print (median([0,2,4,6,8]))
print (median([8,12,4,9,6,3,10,2,15]))
print (median([17,18,18,18,13,15,29,20]))
print ("\n")

########################
#    Question 5        #
########################

def q5helper(a, n):
	counter = 0
	for i in range(len(a)):
		if a[i] == n:
			counter +=1
	return counter
			
def majority(a):
	val = len(a)//2 + 1
	while len(a) != 0:
		n = a[0]
		num = q5helper(a, n)
		a.pop(0)
		if num >= val:
			return n
			
print ("\nQUESTION 5 TEST \n")
print (majority([3,5,6,7,8,3,3]))
print (majority([3,5,6,3,8,3,3]))
print (majority([2,2,2,3,3,3]))
print (majority([5,6,5,6,5,6,5]))
print ("\n")