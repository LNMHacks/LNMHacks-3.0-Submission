import xlrd 
  
loc = ("path of file") 
  
wb = xlrd.report(loc) 
sheet = wb.sheet_by_index(0) 
sheet.cell_value(0, 0) 
  
for i in range(sheet.nrows): 
    print(sheet.cell_value(i, 0)) 
import time
fl=1
print("R R")
while fl==1:
    n=int(input())
    m=int(input())
    if n!=0 and m!=0:
        print("G R")
        for i in range(20):
            time.sleep(1)
            n = int ( input () )
            m = int ( input () )

            if n==0 or m==0:
                break
    if n!=0 and m!=0:
        print("R G")
        for i in range(20):
            time.sleep(1)
            if n==0 or m==0:
                break

    if n==0 and m!=0:
        print("R G")
        for i in range(20):
            time.sleep(1)
            n = int ( input () )
            m = int ( input () )

            if n==0 and m==0:
                print ( "R R" )
                break

    if n!=0 and m==0:
        print("G R")
        for i in range(20):
            time.sleep(1)
            n = int ( input () )
            m = int ( input () )

            if n==0 and m==0:
                print ( "R R" )
                break

    if n==0 and m==0:
        print("R R")