\rm -fr lambda_upload.zip
zip -r lambda_upload.zip index.js food_db.json node_modules fruits_db.json
aws lambda update-function-code --function-name foodNutriLookup --zip-file fileb://lambda_upload.zip
