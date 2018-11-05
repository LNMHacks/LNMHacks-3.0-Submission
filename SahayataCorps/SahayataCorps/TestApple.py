import numpy as np
import mahotas
import cv2

import urllib
import sys
import json
import pandas as pd

df = pd.read_csv('Mean.csv')
df2 = pd.read_csv('Max.csv')

Appleminn = df.iloc[:,1].values
Applemaxx = df2.iloc[:,1].values

df = pd.read_csv('TomatoMean.csv')
df2 = pd.read_csv('TomatoMax.csv')

Tomatominn = df.iloc[:,1].values
Tomatomaxx = df2.iloc[:,1].values

file = sys.argv[1]
name = sys.argv[2]

bins = 8
api = {"Apple":["https://ussouthcentral.services.azureml.net/workspaces/96c958c826904849b74df9dcee941f58/services/701429dac5914def9d679e1735366602/execute?api-version=2.0&details=true","5ZMnPUKvoIZSrbTgAtCDUdyrepcZYeFLuP30LOAVi48WI7llAO8MVg3h+a/dTYUXxMTvlTUcLRFKQ7OeAixlTg==","https://ussouthcentral.services.azureml.net/workspaces/96c958c826904849b74df9dcee941f58/services/c7f9cb547211462b905cdcadd4fcc253/execute?api-version=2.0&details=true","Xgn9fB+IkkkwBNpAWJCM6BDVNZOdX4VhivCOTyjT8O03Mpg3bQ+0aSUOInA8x92YGSW5jtqs44tBgNBmKphxbQ=="],
       "Tomato":["https://ussouthcentral.services.azureml.net/workspaces/96c958c826904849b74df9dcee941f58/services/5c0746577b7b488aaf5b8fb19c1704b7/execute?api-version=2.0&details=true","rkWnoxd9Ri/YyTOo6UZmCQ9tN4ZFXPUEn4QRdJQsalXgJyleLIDvaOepSdK3DwC0F5l4QiyX3peEr6jT2YrcYg==","https://ussouthcentral.services.azureml.net/workspaces/96c958c826904849b74df9dcee941f58/services/ac739aeae82946e384d57c85802b3868/execute?api-version=2.0&details=true", "W1eQJtIld55KXZwf81UvaDMdSIpV3T2gq3FBB/UHMYy7jqhkrlSi1WK5V0/seSINBCt3DFT6n2wjPuGZ/lRNjw=="]}

mapping = {"Apple":['Apple___Apple_scab','Apple___Black_rot','Apple___Cedar_apple_rust','Apple___healthy'],
           "Tomato":['Tomato___Bacterial_spot','Tomato___Early_blight','Tomato___Healthy','Tomato___Late_blight','Tomato___Leaf_Mold','Tomato___Septoria_leaf_spot','Tomato___Spider_mites Two-spotted_spider_mite','Tomato___Target_Spot','Tomato___Tomato_Mosaic_virus','Tomato___Tomato_Yellow_Leaf_Curl_Virus']}
def fd_hu_moments(image):
    image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    feature = cv2.HuMoments(cv2.moments(image)).flatten()
    return feature

def fd_haralick(image):
    # convert the image to grayscale
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # compute the haralick texture feature vector
    haralick = mahotas.features.haralick(gray).mean(axis=0)
    # return the result
    return haralick

def fd_histogram(image, mask=None):
    # convert the image to HSV color-space
    image = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    # compute the color histogram
    hist  = cv2.calcHist([image], [0, 1, 2], None, [bins, bins, bins], [0, 256, 0, 256, 0, 256])
    # normalize the histogram
    cv2.normalize(hist, hist)
    # return the histogram
    return hist.flatten()



image = cv2.imread(file)

global_feature =[]
fv_hu_moments = fd_hu_moments(image)
fv_haralick   = fd_haralick(image)
fv_histogram  = fd_histogram(image)

global_feature = np.hstack([fv_histogram, fv_haralick, fv_hu_moments])

global_feature = global_feature.reshape(1,-1)
MajorXstd = []
def normalize(name):
    if name == 'Apple':
        
        Xstd = global_feature - Appleminn.tolist()
        Xstd = Xstd.tolist()
        Xstd = Xstd[0]
        maxx = Applemaxx.tolist() 
        minn =  Appleminn.tolist()
        
        for x in range(532):
            diff = maxx[x] - minn[x]
            if diff == 0:
                X_std = 1/635
            else:
                X_std = Xstd[x]/diff
            MajorXstd.append(X_std)
    elif name == 'Tomato':
        Xstd = global_feature - Tomatominn.tolist()
        Xstd = Xstd.tolist()
        Xstd = Xstd[0]
        maxx = Tomatomaxx.tolist() 
        minn =  Tomatominn.tolist()
        
        for x in range(532):
            diff = maxx[x] - minn[x]
            if diff == 0:
                X_std = 1/3631
            else:
                X_std = Xstd[x]/diff
            MajorXstd.append(X_std)
normalize(name)
#scaler = MinMaxScaler(feature_range=(0, 1))
#rescaled_features = scaler.fit_transform(global_feature)


data =  {

        "Inputs": {

                "input1":
                {
                    "ColumnNames": ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "150", "151", "152", "153", "154", "155", "156", "157", "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168", "169", "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192", "193", "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "225", "226", "227", "228", "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250", "251", "252", "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275", "276", "277", "278", "279", "280", "281", "282", "283", "284", "285", "286", "287", "288", "289", "290", "291", "292", "293", "294", "295", "296", "297", "298", "299", "300", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "325", "326", "327", "328", "329", "330", "331", "332", "333", "334", "335", "336", "337", "338", "339", "340", "341", "342", "343", "344", "345", "346", "347", "348", "349", "350", "351", "352", "353", "354", "355", "356", "357", "358", "359", "360", "361", "362", "363", "364", "365", "366", "367", "368", "369", "370", "371", "372", "373", "374", "375", "376", "377", "378", "379", "380", "381", "382", "383", "384", "385", "386", "387", "388", "389", "390", "391", "392", "393", "394", "395", "396", "397", "398", "399", "400", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416", "417", "418", "419", "420", "421", "422", "423", "424", "425", "426", "427", "428", "429", "430", "431", "432", "433", "434", "435", "436", "437", "438", "439", "440", "441", "442", "443", "444", "445", "446", "447", "448", "449", "450", "451", "452", "453", "454", "455", "456", "457", "458", "459", "460", "461", "462", "463", "464", "465", "466", "467", "468", "469", "470", "471", "472", "473", "474", "475", "476", "477", "478", "479", "480", "481", "482", "483", "484", "485", "486", "487", "488", "489", "490", "491", "492", "493", "494", "495", "496", "497", "498", "499", "500", "501", "502", "503", "504", "505", "506", "507", "508", "509", "510", "511", "512", "513", "514", "515", "516", "517", "518", "519", "520", "521", "522", "523", "524", "525", "526", "527", "528", "529", "530", "531"],
                    "Values": [MajorXstd,]           
                    },        }
            ,
            "GlobalParameters": {
}
    }

body = str.encode(json.dumps(data))

url = api[name][0]
api_key = api[name][1]
headers = {'Content-Type':'application/json', 'Authorization':('Bearer '+ api_key)}
def send():
    req = urllib.request.Request(url, body, headers) 

    try:
        response = urllib.request.urlopen(req)

    # If you are using Python 3+, replace urllib2 with urllib.request in the above code:
    # req = urllib.request.Request(url, body, headers) 
    # response = urllib.request.urlopen(req)

        result = json.loads(response.read().decode('utf-8'))
        probability = result['Results']['output1']['value']['Values'][0]
        prob = list(map(float,probability))
        temp = prob[:]
        temp.sort()
        prob1 = max(prob)
        prob2 = temp[-2]
        firP = prob.index(max(prob))
        secp = prob.index(temp[-2])
        #print(result)
        #print(mapping[name][firP])
        #print(mapping[name][secp])
        return([mapping[name][firP],prob1,mapping[name][secp],prob2] )
    except urllib.request.HTTPError as error:
        print("The request failed with status code: " + str(error.code))
            
            # Print the headers - they include the requert ID and the timestamp, which are useful for debugging the failure
        print(error.info())
        
        print(json.loads(error.read()))         

def LRsend():
    url2 = api[name][2]
    api_key2 = api[name][3]
    headers2 = {'Content-Type':'application/json', 'Authorization':('Bearer '+ api_key2)}
    req = urllib.request.Request(url2, body, headers2) 

    try:
        response = urllib.request.urlopen(req)

    # If you are using Python 3+, replace urllib2 with urllib.request in the above code:
    # req = urllib.request.Request(url, body, headers) 
    # response = urllib.request.urlopen(req)

        result = json.loads(response.read().decode('utf-8'))
        probability = result['Results']['output1']['value']['Values'][0]
        prob = list(map(float,probability))
        temp = prob[:]
        temp.sort()
        prob1 = max(prob)
        prob2 = temp[-2]
        firP = prob.index(max(prob))
        secp = prob.index(temp[-2])
        #print(result)
        #print(mapping[name][firP])
        #print(mapping[name][secp])
        return([mapping[name][firP],prob1,mapping[name][secp],prob2] )
    except urllib.request.HTTPError as error:
        print("The request failed with status code: " + str(error.code))
            
            # Print the headers - they include the requert ID and the timestamp, which are useful for debugging the failure
        print(error.info())
        
        print(json.loads(error.read()))         
        
result1 = send()
result2 = LRsend()

netResult=[]
if result1[0] == result2[0]:
    netResult.append(result1[0])
    mean = (result1[1] + result2[1])/2
    netResult.append(mean*100)
    netResult.append(result1[2])
    mean = (result1[3] + result2[3])/2
    netResult.append(mean*100)
elif result1[0] == result2[2]:
    netResult.append(result1[0])
    mean = (result1[1] + result2[3])/2
    netResult.append(mean*100)
    netResult.append(result1[2])
    mean = (result1[3] + result2[1])/2
    netResult.append(mean*100)
print(netResult);