#%matplotlib inline
import sys,csv,operator
from  matplotlib import pyplot as plt
import pandas as pd
from pandas import Series, DataFrame
import numpy as np
import mail as m

value_list=['Horror', 'Drama', 'Comedy', 'Action', 'Biography', 'Crime',
       'Thriller', 'Adventure', 'Family', 'Animation', 'Mystery', 'Sci-Fi',
       'Fantasy', 'Western', 'Romance', 'Music',  'War', 'Musical', 'History']
'''
for k in value_list:
	with open(k+'.txt', 'r') as in_file:
		stripped = (line.strip() for line in in_file)
		lines = (line.split(",") for line in stripped if line)
		with open(k+'.csv', 'w') as out_file:
			writer = csv.writer(out_file)
			writer.writerow(("movie_name", "genre", "year", "rating"))
			writer.writerows(lines)


		with open(k+'.csv', newline='') as out_file:
			spanreader=csv.DictReader(out_file, delimiter=";")
			sortedList=sorted(spanreader,key=itemgetter(3))
			print(sortedList)
			#top_five =  spanreader.sort('rating', ascending=False).head(5)
'''

	
def abstractData(x):
	data = pd.read_csv(x+'.txt', sep=",", header = None,error_bad_lines=False)
	data.columns = ["movie_name", "genre", "year", "rating"]
	top_five =  data.sort('rating', ascending=False).head(5)
	print(top_five)
	m.email("parultshandilya@gmail.com",str(top_five))

def rec_movie(genre):
       for q in genre:
               print (user_name + ' according to your choice of gener and year we recomend theses movies'+q)
               abstractData(q)

user_name  = input("Enter YOUR NAME: ")
print ("Select the input: " + '\n '.join(value_list) + "\n")
user_genre = input("What genre of movies do you like?")
genre_names=user_genre.strip().split(',')
print(genre_names)
rec_movie(genre_names)
