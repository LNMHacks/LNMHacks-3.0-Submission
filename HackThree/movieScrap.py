import requests
from bs4 import BeautifulSoup
def main(max_pages = 200):
	page = 0
	count = 0	
	while page < max_pages:
		index = page * 50 + 1
		#url = 'http://www.imdb.com/search/title?languages=en%7C1&num_votes=10000,&sort=user_rating,desc&start='+str(index)+'&title_type=feature'		
		yr=input("enter the year")
		url =  'http://www.imdb.com/search/title?sort=year&start='+str(index)+'&title_type=feature&year='+yr+','+yr
		#print (url)
		page1=requests.get(url)
		soup=BeautifulSoup(page1.text,'html.parser')
		#soup = BeautifulSoup(plain_text)
		get_single_item_data(soup)
		#print(soup.findAll('td',{'class': 'image'},'a'))
		page+=1
def get_single_item_data(soup):
	name_box = soup.find_all('div', attrs={'class': 'lister-item-content'})
	for item in name_box:
		try:
			#name_box_inside = item.find_all('span', attrs={'class': 'lister-item-index unbold text-primary'})
			#print("*************************************************************")
			title=item.find('h3',attrs={'class':'lister-item-header'})
			year=soup.find('span',attrs={'class':'lister-item-year text-muted unbold'})
			rating=soup.find('div',attrs={'class':'inline-block ratings-imdb-rating'})
			genre=soup.find('span',attrs={'class': 'genre'})
			genre_names=genre.getText().strip().split(',')
			#print(title.find('a').getText())
			#print(year.getText()[1:-1])
			if(year==None):
				year=" "
			else:
				year=year.getText()[1:-1]
			if(title==None):
				title=" "
			else:
				title=title.find('a').getText()
			if(rating==None):
				rating=" "
			else:
				rating=rating.getText().strip()
		
					#print(rating.getText().strip())
			for x in genre_names:
				#print(x.strip())
				f=open(str(x.strip())+'.txt','a')
				f.write(title + ',' + x + ',' + year + ',' + rating + '\n')

			#a=name_box_inside.find_all('a')
			#movie=a['href']
		except:
			print("ignored")
main()

