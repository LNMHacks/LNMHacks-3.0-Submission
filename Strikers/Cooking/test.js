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



var event = {
  session: {
    new: false,
    sessionId: 'session1234',
    attributes: {},
    user: {
      userId: 'usrid123'
    },
    application: {
      applicationId: 'amzn1.ask.skill.f5cf61da-007e-4d6f-8db4-9325c0735787'
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




describe('All intents', function() {
  var ctx = new Context();
  var storedSession;


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
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/<speak>Hi,.*<\/speak>/);
     });
    
     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/<speak>For example.*<\/speak>/);
     });

  });

  var expResults = {
    'butter salted': {
      endSession: true,
      searchResults: 1
    },
    'orange': {
      endSession: false,
      searchResults: 18
    },
    'apples raw': {
      endSession: false,
      searchResults: 11
    },
    'toy': {
      endSession: true,
      searchResults: 0
    }
  };

  for(var key in expResults) {

    describe(`Test GetNutritionInfo ${key}`, function() {
        var options = expResults[key];
        var testFood = key;


        before(function(done) {
          event.request.intent = {};
          event.session.attributes = {};
          event.request.type = 'IntentRequest';
          event.request.intent.name = 'GetNutritionInfo';
          event.request.intent.slots = {
            FoodItem: {
              name: 'FoodItem',
              value: testFood
            }
          };
          ctx.done = done;
          lambdaToTest.handler(event , ctx);
        });

       it('valid response', function() {
         validRsp(ctx, options);
       });

       it('valid card', function() {
         validCard(ctx);
       });

       it('valid outputSpeech', function() {
         if(options.searchResults === 0) {
           expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Could not find any food item/);
         } else {
           var num = ctx.speechResponse.response.outputSpeech.ssml.match(/100 grams/g).length;
           if(options.searchResults > 3) {
             expect(num).to.be.equal(3);
           } else {
             expect(num).to.be.equal(options.searchResults);
           }
         }
       });

      if(!options.endSession) {
       it('valid reprompt', function() {
         expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/You can say/);
       });
      }

    });

    if (!expResults[key].endSession) {

      describe(`Test GetNextEventIntent ${key}`, function() {
          var options = expResults[key];
          var testFood = key;

          before(function(done) {
            event.request.intent = {};
            event.session.attributes = ctx.speechResponse.sessionAttributes;
            event.request.type = 'IntentRequest';
            event.request.intent.name = 'GetNextEventIntent';
            event.request.intent.slots = {};
            ctx.done = done;
            lambdaToTest.handler(event , ctx);
          });

         it('valid response', function() {
           validRsp(ctx, {endSession: true});
         });

         it('valid card', function() {
           validCard(ctx);
           expect(ctx.speechResponse.response.card.content).to.match(new RegExp(`Your search resulted in ${options.searchResults} food items`));
         });

         it('valid outputSpeech', function() {
           expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(new RegExp(`Your search resulted in ${options.searchResults} food items`));
         });

         //it('valid reprompt', function() {
         //  validReprompt(ctx);
         //  //expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/<speak>You.*<\/speak>/);
         //});

      });

      describe(`Test AMAZON.StopIntent ${key}`, function() {
          var options = expResults[key];
          var testFood = key;

          before(function(done) {
            event.request.intent = {};
            event.session.attributes = ctx.speechResponse.sessionAttributes;
            event.request.type = 'IntentRequest';
            event.request.intent.name = 'AMAZON.StopIntent';
            event.request.intent.slots = {};
            ctx.done = done;
            lambdaToTest.handler(event , ctx);
          });

         it('valid response', function() {
           validRsp(ctx, {endSession: true});
         });

         it('valid outputSpeech', function() {
           expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Good Bye./);
         });

         //it('valid reprompt', function() {
         //  validReprompt(ctx);
         //  //expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/<speak>You.*<\/speak>/);
         //});

      });


    }
  }

  describe(`Test GetNutritionInfo empty slot`, function() {

      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'GetNutritionInfo';
        event.request.intent.slots = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession:false});
     });


     it('valid outputSpeech', function() {
         expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Looks like/);
     });

     it('valid reprompt', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/For example/);
     });

  });


  describe(`Test GetQuizIntent`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = {};
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'GetQuizIntent';
        event.request.intent.slots = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       storedSession = ctx.speechResponse.sessionAttributes;
       validRsp(ctx, {endSession:false});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/How many/);
     });

     it('valid reprompt', function() {
       expect(ctx.speechResponse.response.reprompt.outputSpeech.ssml).to.match(/Please tell/);
     });

  });

  describe(`Test QuizAnswerIntent correct answer`, function() {
      before(function(done) {
        var fruitInfo = storedSession.fruit;
        event.request.intent = {};
        event.session.attributes = ctx.speechResponse.sessionAttributes;
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'QuizAnswerIntent';
        event.request.intent.slots = {
          Answer: {
            name: 'Answer',
            value: fruitInfo[1].toString()
          }
        };
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession:true});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Correct answer./);
     });

  });

  describe(`Test QuizAnswerIntent close answer`, function() {
      before(function(done) {
        var fruitInfo = storedSession.fruit;
        var answer = Number(fruitInfo[1]) + Math.floor(Math.random()*8) - 4;
        if(answer === Number(fruitInfo[1])) {
          answer += 1;
        }
        event.request.intent = {};
        event.session.attributes = storedSession;
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'QuizAnswerIntent';
        event.request.intent.slots = {
          Answer: {
            name: 'Answer',
            value: answer.toString()
          }
        };
        ctx.done = done;
        lambdaToTest.handler(event, ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession:true});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/You are pretty close./);
     });

  });

  describe(`Test QuizAnswerIntent wrong answer`, function() {
      before(function(done) {
        var fruitInfo = storedSession.fruit;
        var answer = Number(fruitInfo[1]) + Math.floor(Math.random()*10) + 5;
        event.request.intent = {};
        event.session.attributes = storedSession;
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'QuizAnswerIntent';
        event.request.intent.slots = {
          Answer: {
            name: 'Answer',
            value: answer.toString()
          }
        };
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession:true});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/Wrong answer./);
     });

  });

  describe(`Test DontKnowIntent`, function() {
      before(function(done) {
        event.request.intent = {};
        event.session.attributes = storedSession;
        event.request.type = 'IntentRequest';
        event.request.intent.name = 'DontKnowIntent';
        event.request.intent.slots = {};
        ctx.done = done;
        lambdaToTest.handler(event , ctx);
      });

     it('valid response', function() {
       validRsp(ctx, {endSession:true});
     });

     it('valid outputSpeech', function() {
       expect(ctx.speechResponse.response.outputSpeech.ssml).to.match(/No problem./);
     });

  });



});
