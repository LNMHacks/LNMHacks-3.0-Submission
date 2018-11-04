import sys
import requests
import ast


name = sys.argv[1]
u = sys.argv[2]


url = str(u)
name = name.split(".")


def translate():
    img_filename = str(name[0])+'.png'
    with open(img_filename, 'rb') as f:
        img_data = f.read()
        
    key = "b9a2ab397bc547ec95264b89af65f7d0"

    head = {"Prediction-Key":key,
            "Content-Type":"application/octet-stream"}
    r = requests.post(url=url, headers=head, data=img_data)
    data = r.text
    
    d = ast.literal_eval(data)
    prob1 = d["Predictions"][0]['Probability']
    prob2 = d["Predictions"][1]['Probability']
    if prob1 <=0.001:
        prob1 = int(prob1)
    if prob2<= 0.001:
        prob2 = int(prob2)
    prob1*= 100
    prob2*= 100
    res = [d["Predictions"][0]['Tag'],prob1,d["Predictions"][1]['Tag'],prob2]
    print res

translate()
