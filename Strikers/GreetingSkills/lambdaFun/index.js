'use strict';

var http = require('http');

exports.handler = function(event,context) {

  try {

    if(process.env.NODE_DEBUG_EN) {
      console.log("Request:\n"+JSON.stringify(event,null,2));
    }



    var request = event.request;
    var session = event.session;

    if(!event.session.attributes) {
      event.session.attributes = {};
    }

    /*
      i)   LaunchRequest       Ex: "Open greeter"
      ii)  IntentRequest       Ex: "Say hello to John" or "ask greeter to say hello to John"
      iii) SessionEndedRequest Ex: "exit" or error or timeout
    */

    if (request.type === "LaunchRequest") {
      handleLaunchRequest(context);

    } else if (request.type === "IntentRequest") {

      if (request.intent.name === "HelloIntent") {

        handleHelloIntent(request,context);

      } else if (request.intent.name === "QuoteIntent") {

        handleQuoteIntent(request,context,session);

      } else if (request.intent.name === "NextQuoteIntent") {

        handleNextQuoteIntent(request,context,session);

      } else if (request.intent.name === "AMAZON.StopIntent" || request.intent.name === "AMAZON.CancelIntent") {
        context.succeed(buildResponse({
          speechText: "Good bye. ",
          endSession: true
        }));

      } else {
        throw "Unknown intent";
      }

    } else if (request.type === "SessionEndedRequest") {

    } else {
      throw "Unknown intent type";
    }
  } catch(e) {
    context.fail("Exception: "+e);
  }

}

function getQuote(callback) {
  var url = "http://api.forismatic.com/api/1.0/json?method=getQuote&lang=en&format=json";
  var req = http.get(url, function(res) {
    var body = "";

    res.on('data', function(chunk) {
      body += chunk;
    });

    res.on('end', function() {
      body = body.replace(/\\/g,'');
      var quote = JSON.parse(body);
      callback(quote.quoteText);
    });

  });

  req.on('error', function(err) {
    callback('',err);
  });
  
}

function getWish() {
  var myDate = new Date();
  var hours = myDate.getUTCHours() - 8;
  if (hours < 0) {
    hours = hours + 24;
  }

  if (hours < 12) {
    return "Good Morning. ";
  } else if (hours < 18) {
    return "Good afternoon. ";
  } else {
    return "Good evening. ";
  }
  
}


function buildResponse(options) {

  if(process.env.NODE_DEBUG_EN) {
    console.log("buildResponse options:\n"+JSON.stringify(options,null,2));
  }

  var response = {
    version: "1.0",
    response: {
      outputSpeech: {
        type: "SSML",
        ssml: "<speak>"+options.speechText+"</speak>"
      },
      shouldEndSession: options.endSession
    }
  };

  if(options.repromptText) {
    response.response.reprompt = {
      outputSpeech: {
        type: "SSML",
        ssml: "<speak>"+options.repromptText+"</speak>"
      }
    };
  }

  if(options.cardTitle) {
    response.response.card = {
      type: "Simple",
      title: options.cardTitle
    }

    if(options.imageUrl) {
      response.response.card.type = "Standard";
      response.response.card.text = options.cardContent;
      response.response.card.image = {
        smallImageUrl: options.imageUrl,
        largeImageUrl: options.imageUrl
      };

    } else {
      response.response.card.content = options.cardContent;
    }
  }




  if(options.session && options.session.attributes) {
    response.sessionAttributes = options.session.attributes;
  }

  if(process.env.NODE_DEBUG_EN) {
    console.log("Response:\n"+JSON.stringify(response,null,2));
  }

  return response;
}

function handleLaunchRequest(context) {
  let options = {};
  options.speechText =  "Welcome to Greetings skill. Using our skill you can greet your guests. Whom you want to greet? ";
  options.repromptText = "You can say for example, say hello to John. ";
  options.endSession = false;
  context.succeed(buildResponse(options));
}

function handleHelloIntent(request,context) {
  let options = {};
  let name = request.intent.slots.FirstName.value;
  options.speechText = `Hello <say-as interpret-as="spell-out">${name}</say-as> ${name}. `;
  options.speechText += getWish();

  options.cardTitle = `Hello ${name}!`;

  getQuote(function(quote,err) {
    if(err) {
      context.fail(err);
    } else {
      options.speechText += quote;
      options.cardContent = quote;
      options.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/5b/Hello_smile.png";
      options.endSession = true;
      context.succeed(buildResponse(options));
    }
  });
}

function handleQuoteIntent(request,context,session) {
  let options = {};
  options.session = session;

  getQuote(function(quote,err) {
    if(err) {
      context.fail(err);
    } else {
      options.speechText = quote;
      options.speechText += " Do you want to listen to one more quote? ";
      options.repromptText = "You can say yes or one more. ";
      options.session.attributes.quoteIntent = true;
      options.endSession = false;
      context.succeed(buildResponse(options));
    }
  });

}

function handleNextQuoteIntent(request,context,session) {
  let options = {};
  options.session = session;

  if(session.attributes.quoteIntent) {
    getQuote(function(quote,err) {
      if(err) {
        context.fail(err);
      } else {
        options.speechText = quote;
        options.speechText += " Do you want to listen to one more quote? ";
        options.repromptText = "You can say yes or one more. ";
        //options.session.attributes.quoteIntent = true;
        options.endSession = false;
        context.succeed(buildResponse(options));
      }
    });
  } else {
    options.speechText = " Wrong invocation of this intent. ";
    options.endSession = true;
    context.succeed(buildResponse(options));
  }

}
