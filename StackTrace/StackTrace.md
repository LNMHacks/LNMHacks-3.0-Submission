# Allisto
#### AI Powered Ecosystem for Early Diagnosis of Autism in Children and General Healthcare.
![Imgur](https://i.imgur.com/WOztSlt.jpg)

# Team Name : StackTrace
### Team Members : 
#### Aashis Kumar Nedheesh Hasija Aditya Thakur Rishi Banerjee

## The Problem
Lack of safe and feasible methods for detection and early diagnosis of autism in infants and an approachable and readily available ecosystem to provide better medical services. Detection of autism in one year old babies is difficult. The difference between the crying of babies who are developing normally and those with autism may be too subtle for people to notice without audio analysis by a computer.There's also the question of what could be done, if anything, to help babies at high risk of autism. Now, physicians are unable to diagnose autism disorders until about the age of 18 months.

## Approach Taken
The analysis of the cries of babies "might allow us to target the right babies to monitor or intervene with," said study lead author Stephen Sheinkopf, an assistant professor of psychiatry and human behavior at Brown Medical School in Providence, R.I.
So for the classification of autistic babies from neurotypical or allistic kids, we use a normal Artificial Neural Network. An RNN would have been a better approach, but an ANN is strong enough to show the proof of concept. We use a raw voice dataset which is tweaked to suite our needs. Link to Research Paper : https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3517274/

## Setting up the Neural Network
```
cd StackTrace/project/ai_and_audio/
```
#### model.py 
This file contains the Neural Network Details. It has 1 input layer having 19 input dimensions and 6 output dimension, one hidden layer with 6 input dimensions and a output layer which uses the adam optimizer to give a binary classification of the audio dataset.

#### audio.py
This file helps convert the sample.wav file contained in this directory or any other audio file of the .wav format to a spectogram image in the .png format.

#### audio_analyze.py
The AudioAnalyze class helps extract features (total 34) out of which 19 are used by the neural network from the sample audio provided. 

## Setting up the Flask API
```
cd StackTrace/project/ai_and_audio/
export FLASK_APP=app.py
flask run
```
the api will be taking get requests on localhost:5000/api
We have already hosted the API on allisto.pythonanywhere.com, so no setting up on *localhost* 

## Setting up the electron app
```
cd StackTrace/project/electron/
npm install
npm start
```
The Electron Application will open up in the debugging mode in a separate window.

## Setting up the Android Application
```
Open the folder in Android Studio to compile and install the application. You have to generate an API Key from DialogFlow. 
```
For convinience in installation of the Android Application, we have provided the compiled APK along with trained DialogFlow Agent. Link : https://drive.google.com/open?id=1UjveS2cQui6VtPHAj4B7UzvFCUGeqTJj

## Workflow
1. Download the Android APK File and Register the user *this will give the user a Unique ID that would be used for any further queries*
2. Fill in the required details in the Application and submit.
3. Go for first evaluation of Autism (by recording the audio of the crying child) or Chat with the chatbot for quick query resolutions
4. Doctor will enter the unique ID of the patient to get the name and any previous history
5. Upload vaccines taken and any other medication by the doctor side to keep a track forever




