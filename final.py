from PIL import Image
import os
from shutil import copyfile

path_1 = "input/"
path_2 = "output/"
ii = 0
os.system("rm -rf output/")
os.system("mkdir output")
stri = "firefox "
listing = os.listdir(path_1)
for file in listing:
	ii = ii + 1 
	copyfile(path_1+file, path_2+file)	
	im = Image.open(path_2+file)
	im.convert('RGB')
	pix = im.load()
	# pix2 = im.load()
	# print im.size
	x, y = im.size
	img = Image.new(im.mode, im.size)
	pixelsNew = img.load()
	for i in range(0, x):
		for j in range(0, y):
		    if pix[i, j] == (1, 2, 3):
		        pixelsNew[i, j] = (255, 255, 255)
		    else:
		        pixelsNew[i, j] == (0, 0, 0)
	img.save('mask.png')
	im.close()
	os.system("th inpaint.lua --input "+path_2+file+" --mask mask.png --nopostproc")
	copyfile("out.png",path_2+"1"+file)
	os.system("rm mask.png")
	os.system('echo'+' "<!DOCTYPE html><html><head><title>Image Comparision</title><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></head><body><div class="col-xs-6"><h1>Input:</h1><img height="auto" width=500 src="'+file+'"></div><div class="col-xs-6"><h1>Output:</h1><img height="auto" width=500 src="'+"1"+file+'"></div></body></html>" >> '+path_2 + str(ii) + '.html')
	ii = ii + 1
	stri += "-new-tab -url "+os.getcwd()+"/"+path_2+str(ii-1)+".html "
os.system(stri)
#print(stri)
