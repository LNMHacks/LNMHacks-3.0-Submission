## Project Topic: Using Neural Networks to recreate lost parts of images
### Team Name: Sleepy Heads

### Input
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/input/boat.png"> 
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/input/gazebo.png"> 
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/input/input2.png"> 
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/input/tree.png">



### Output
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/output/1boat.png"> 
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/output/1gazebo.png"> 
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/output/1input2.png"> 
<img display="inline" float="left" width ="350" src="https://github.com/art-hack/LNMHacks-3.0-Submission/blob/master/output/1tree.png">


### Explaination:

The traditional implementation uses the texture of surrounding areas and fill the area but it does not usually produce good results, so we used a combination of **Object Detection and surrounding textures** to get the desired output.

The model was trained on Places2 Dataset: (http://places2.csail.mit.edu/) so it works best on **Outdoor Natural Images** and can be extended to other datasets as well very easily.

For every image we take input the files 

We learn to inpaint missing regions with a deep convolutional network. Our network completes images of arbitrary resolutions by filling in missing regions of any shape. We use global and local context discriminators to train the completion network to provide both locally and globally consistent results.

### Installation and Running

#### INSTALLING DEPENDENCIES
```
git clone https://github.com/torch/distro.git ~/torch --recursive
cd ~/torch; bash install-deps;
./install.sh
luarocks install image
```
Once installed we can run torch using th command


#### INSTALLING FILES 
```
git clone https://github.com/utkarsh-maheshwari/image-completion.git
cd image-completion
bash download_model.sh
```

#### USAGE 
```
cd image-completion
mkdir input
mkdir output
```
### **Note: The image should be redacted (erased) with HEx values: #010203 and NOT any other color and should use Paint 3d in Windows and gnome-paint drawing editor in Linux**
Copy the redacted images into the input folder

```
python final.py
```

This code provides an extension to the the research paper:

  "Soheil Darabi, Eli Shechtman, Connelly Barnes, Dan B Goldman, and Pradeep Sen.
2012. Image Melding: Combining Inconsistent Images using Patch-based Synthesis.
ACM Transactions on Graphics"
