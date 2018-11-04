var mqtt = require('../node_modules/mqtt')
var request = require('../node_modules/request')

if (process.env.NODE_ENV !== 'production') {
    require('../node_modules/dotenv').load()
}

var client = mqtt.connect({
    host: process.env.MQTThost,
    port: process.env.MQTTport,
    username: process.env.MQTTusername,
    password: process.env.MQTTpassword
})

var headers = {
  "Prediction-Key": "",
  "Content-Type": "application/json"
}

client.on('connect', function () {
  client.subscribe(process.env.MQTTtopic)
})

module.exports = function (context, myBlob) {
    context.log("JavaScript blob trigger function processed blob \n Name:", context.bindingData.name, "\n Blob Size:", myBlob.length, "Bytes");
    Bloburl='https://garbagedecabe0.blob.core.windows.net/trashbro'+context.bindingData.name;

var options = {
    url: 'https://southcentralus.api.cognitive.microsoft.com/customvision/v1.1/Prediction/94944944-6adf-4cce-b7e3-51811fefb085/url?iterationId=55f734d3-0f9b-4290-93fc-65c49be941b4',
    method: 'POST',
    headers: headers,
    form: {'url': Bloburl}
}

request(options, function (error, response, body) {
    if (!error && response.statusCode == 200) {
        var obj= JSON.parse(body)
        context.log(obj.Predictions[0].Probability)
        if(obj.Predictions[0].Probability>0.7){
          client.publish(process.env.MQTTtopic, '1')
        }
        else{
            client.publish(process.env.MQTTtopic, '0')
        }
    }
})

  context.log(client.connected)
  context.done();
};