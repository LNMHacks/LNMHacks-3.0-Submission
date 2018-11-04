# CyAnalytics
## An Intelligent Way To Analyse And Visualize Data
### Shrey Batra, Mayank, Arvind PJ, Ankit Khandelwal

## Problem Statement
We regularly face the problem of good quality Generic Data Analysis and Visualisation Software. Most softwares like SAP, DataPine, etc are build on SQL based model which limits our power to extract derived information from the data.

## Our Solution
Our solution is based on a Fully Generic NoSQL based model to enhance our power to extract information from the data with many varied features like Regex search, Geo-Index Based searches, toggle between single to range queries etc, all on Real Time Basis.

We have developed a powerful algorithm which uses it's own intelligence to analyse the uploaded dataset.

It also provides features to visualise the information and play with the structure of the data. As time constraints were an issue, API's for Data Pre-Processing were not built onto the frontend, and can be just added in the near future. 

**Main Features**
- Upload the whole dataset (csv files currently) to the server, and make an independent populated database for each dataset dynamically.
- Maintain meta data, column information and extra information for quick access.
- Maintaining a history of all previous uploaded datasets, queries in the db for further making our platform powerful.
- Select a slice of the data by Various Generic Powerful Filter Section like geo_index, regex match, sub document match, etc.
- Visualise the selected data slice in various types of Interactive Graphs/Charts.
- Implemented a highly robust generic filter algorithm for heavy load tolerance by using distributed systems.

## How To Run The Project

1. The whole project is available in the **CyAnalytics** folder.
2. You must have python > v3.6.6.
3. ```pip install -r requirements.txt```
4. You must have installed NodeJs, ReactJs, ChartJs, ElasticSearch (v6.0 from the original website), MongoDB (v4.0).
5. To run, you must run this command ```python main.py``` inside the CyAnalytics folder, after setting correct MongoDB User Access in your system.
6. Now visit ```http://localhost:5000/``` to start your journey.
7. For your ease, we have bipassed the login system for now as you might have to setup the data explicitly.
8. You can use the provided .csv files for testing purposes. (NBA dataset and IRIS dataset)

## Tech Stack
1. Python
2. ElasticSearch to develop highly powerful REST API's
3. MongoDB for high scalability.
4. Flask for API management.
5. Flask_WTF for Real Time Form Management and access.
6. ChartJs for Real Time Interactive Charts/Graphs.