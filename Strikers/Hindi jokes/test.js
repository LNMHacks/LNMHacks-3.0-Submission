'use strict'

var expect = require('chai').expect,  

lambdaToTest = require('./index')


function Context() {
  this.speechResponse = null;
  this.speechError = null;

  this.succeed = function(rsp) {
    this.speechResponse = rsp;
    this.done();
  };

  this.fail = function(rsp) {
    this.speechError = rsp;
    this.done();
  };

}

function validRsp(ctx,options) {
     expect(ctx.speechError).to.be.null;
     expect(ctx.speechResponse.version).to.be.equal('1.0');
     expect(ctx.speechResponse.response).not.to.be.undefined;
     expect(ctx.speechResponse.response.outputSpeech).not.to.be.undefined;
     expect(ctx.speechResponse.response.outputSpeech.type).to.be.equal('SSML');
     expect(ctx.speechResponse.response.outputSpeech.ssml).not.to.be.undefined;
     expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/<speak>.*<\/speak>/);
     if(options.endSession) {
       expect(ctx.speechResponse.response.shouldEndSession).to.be.true;
       expect(ctx.speechResponse.response.reprompt).to.be.undefined;
     } else {
       expect(ctx.speechResponse.response.shouldEndSession).to.be.false;
       expect(ctx.speechResponse.response.reprompt.outputSpeech).to.be.not.undefined;
       expect(ctx.speechResponse.response.reprompt.outputSpeech.type).to.be.equal('SSML');
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/<speak>.*<\/speak>/);
     }

}

function validCard(ctx) {
     expect(ctx.speechResponse.response.card).not.to.be.undefined;
     expect(ctx.speechResponse.response.card.type).to.be.equal('Simple');
     expect(ctx.speechResponse.response.card.title).not.to.be.undefined;
     expect(ctx.speechResponse.response.card.content).not.to.be.undefined;
}


var usrId = 'testUsr';
var token = 'ya29.GlwBBAT1WRYK1YxDC_5AxVgakjSFjqYz8TRUVOMDDyYqghNHRRefWK5SjzTdI9CQd_MyvYbtMmaC-yIkI7DIcSc8Z1Z05HLGehcK-t8zLLXxNrN-CTCMxLL5TGgAbg';


var event = {
  session: {
    new: false,
    sessionId: 'session1234',
    attributes: {},
    user: {
      userId: usrId,
      accessToken: token
    },
    application: {
      applicationId: 'amzn1.ask.skill.8015dfcb-2b86-4c0f-abdf-b6f018c17f2b'
    }
  },
  version: '1.0',
  request: {
    intent: {
      slots: {
        SlotName: {
          name: 'SlotName',
          value: 'slot value'
        }
      },
      name: 'intent name'
    },
    type: 'IntentRequest',
    requestId: 'request5678'
  }
};


var expMails = 4;
var AWS = require("aws-sdk");
AWS.config.update({
  region: "us-east-1",
});
var bcrypt = require('bcryptjs');
var docClient = new AWS.DynamoDB.DocumentClient();

describe('All intents', function() {
  var ctx = new Context();
  var prevResponse;
  var authPin = Math.floor(Math.random() * 90000)+10000;

  before(function(done) {
    deletePin(usrId, function() {
      done();
    });

  });


  describe('Test LaunchIntent', function() {

      before(function(done) {
        event.request.type = 'LaunchRequest';
        event.request.intent = {};
        event.session.attributes = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });


     it('valid response', function() {
       validRsp(ctx,{
         endSession: false,
       });
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/<speak>Welcome to.*<\/speak>/);
     });
    
     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/<speak>What you.*<\/speak>/);
     });

  });

  describe(`Test EmailCheckIntent`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'EmailCheckIntent';
        event.request.intent.slots = {}
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession: false});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/You haven't set/);
     });

     it('valid reprompt', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/For example/);
     });
  });

  describe(`Test SetPinIntent`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'SetPinIntent';
        event.request.intent.slots = {
          "SPin": {
            "name": "SPin",
            "value": authPin.toString()
          }
        };
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession: true});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(new RegExp(`Succefully updated pin to.*${authPin}`));
     });

     //it('valid reprompt', function() {
     //  expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/For example/);
     //});
  });

  describe(`Test EmailCheckIntent`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'EmailCheckIntent';
        event.request.intent.slots = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       prevResponse = ctx.speechResponse;
       validRsp(ctx, {endSession: false});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Please tell/);
     });

     it('valid reprompt', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/whats your/);
     });
  });

  describe(`Test EmailAuthIntent correct pin`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = prevResponse.sessionAttributes;
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'EmailAuthIntent';
        event.request.intent.slots = {
          "CPin": {
            "name": "CPin",
            "value": authPin.toString()
          }
        };
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession: false});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(new RegExp(`You have total ${expMails} unread mails`));
     });

     it('valid repromptSpeech', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/You can say/);
     });
  });

  describe(`Test AMAZON.YesIntent`, function() {

      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'AMAZON.YesIntent';
        event.session.attributes = ctx.speechResponse.sessionAttributes;
        event.request.intent.slots = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {
         endSession: true
       });
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Mail from/);
     });
  
     //it('valid outputSpeech', function() {
     //  expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/<speak>For example.*<\/speak>/);
     //});

  });

  describe(`Test EmailAuthIntent wrong pin`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = prevResponse.sessionAttributes;
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'EmailAuthIntent';
        event.request.intent.slots = {
          "CPin": {
            "name": "CPin",
            "value": (authPin-1).toString()
          }
        };
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession: true});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Wrong secret/);
     });

  });

  describe(`Test ForgotPinIntent`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'ForgotPinIntent';
        event.request.intent.slots = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession: true});
     });

     it('valid card', function() {
       validCard(ctx);
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Please check your/);
     });

     it('valid pin', function(done) {
      var match = ctx.speechResponse.response.card.content.match(/Your recovery pin is (\S+)\./);
       readPin(usrId, function(res,err) {
          bcrypt.compare(match[1], res.Item.pin, function(err, res) {
            expect(res).to.be.true;
            done();
          });
       });
     });

     //it('valid reprompt', function() {
     //  expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/whats your/);
     //});
  });




});

function deletePin(usrId,callback) {
  var params = {
    TableName:"UsrPins",
    Key:{
        "UsrId": usrId
    }
  };

  console.log("Updating the item...");
  docClient.delete(params, function(err, data) {
      if (err) {
          console.log("Unable to delete item. Error JSON:", JSON.stringify(err, null, 2));
          callback(false,err);
      } else {
          console.log("DeleteItem succeeded:", JSON.stringify(data, null, 2));
          callback(true);
      }
  });

}

function readPin(usrId, callback) {

  var params = {
      TableName: "UsrPins",
      Key:{
          "UsrId": usrId
      }
  };

  docClient.get(params, function(err, data) {
      if (err) {
          callback(false,err);
          console.log("Unable to read item. Error JSON:", JSON.stringify(err, null, 2));
      } else {
        console.log(data);
        if(Object.keys(data).length === 0) {
          callback(false);
        } else {
          callback(data);
        }

      }
  });
}
