from collections import namedtuple,OrderedDict
import re
from pprint import pprint
import json

food_db = OrderedDict()

food_slot_values = []

with open("usdaFiles/ABBREV.txt","r") as f:
    for ln in f:
        info = ln.rstrip().split('~')
        nutri_info = info[4].split('^')
        food_db[info[1]] = [info[3],nutri_info[2],nutri_info[3],nutri_info[4]] #abbr name, Calories, protein, fat (per 100g)

with open("usdaFiles/FOOD_DES.txt","r") as f:
    for ln in f:
        info = ln.rstrip().split('~')
        food_db[info[1]].append(info[3])
        food_db[info[1]][0] = info[5].lower()


food_info_fnl = []
fruits_info = []

for id,val in food_db.iteritems():
    val.append(id)
    food_info_fnl.append(val)
    if (val[4] == '0900') and re.search('raw',val[0]):
        fruits_info.append([re.sub(r',','',val[0]),val[1]])


with open("food_db.json","w") as f:
    #json.dump(food_info_fnl, f)
    json.dump(food_info_fnl, f, indent=4, separators=(',',': '))

with open("fruits_db.json","w") as f:
    #json.dump(food_info_fnl, f)
    json.dump(fruits_info, f, indent=4, separators=(',',': '))

food_grps = {}

with open("usdaFiles/FD_GROUP.txt","r") as f:
    for ln in f:
        info = ln.rstrip().split('~')
        info[3] = info[3].lower()
        #info[3] = re.sub(r'\s+.*','',info[3])
        food_grps[info[3]]=info[1]

with open("food_grps.json","w") as f:
    json.dump(food_grps, f, indent=4, separators=(',',': '))

def gen_slot_values(food_db):
    vals = []
    used = []
    for id,val in food_db.iteritems():
        fname = re.sub(r'[,"]','',val[0])
        vals.append(fname)
        if len(fname.split())>1:
           vals.append(fname.split()[0])
           vals.append(' '.join(fname.split()[0:2]))
           vals.append(' '.join(fname.split()[0:3]))
    unique = [x for x in vals if x not in used and (used.append(x) or True)]
    return unique

with open('speechAssets/FOOD_ITEMS','w') as f:
    f.write('\n'.join(gen_slot_values(food_db)))
    

