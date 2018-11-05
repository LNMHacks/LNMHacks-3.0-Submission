#%matplotlib inline
import sys,csv,operator
from  matplotlib import pyplot as plt
import pandas as pd
from pandas import Series, DataFrame
import numpy as np

value_list=['Horror', 'Drama', 'Comedy', 'Action', 'Biography', 'Crime',
       'Thriller', 'Adventure', 'Family', 'Animation', 'Mystery', 'Sci-Fi',
       'Fantasy', 'Western', 'Romance', 'Music',  'War', 'Musical', 'History']
def toCSV():
	for k in value_list:
		with open(k+'.txt', 'r') as in_file:
			stripped = (line.strip() for line in in_file)
			lines = (line.split(",") for line in stripped if line)
			with open(k+'.csv', 'w') as out_file:
				writer = csv.writer(out_file)
				writer.writerow(("movie_name", "genre", "year", "rating"))
				writer.writerows(lines)

