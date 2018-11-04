import os
print("choices available :"+"\n"+"1 : enter the tv series and will tell the latest upcoming episode date"+"\n"+"2: enter your movie genre choice,year and will recommend top 5 movies in that year ")
c=int(input("enter your choice : 1 or 2 "))
if(c==1):
	os.system("python3 LNMHack_scrap_status.py")
elif(c==2):
	os.system("python3 imdb1.py")
	os.system("python3 toCSV.py")
	os.system("python3 recommend.py")
else:
	print("error in choice")

