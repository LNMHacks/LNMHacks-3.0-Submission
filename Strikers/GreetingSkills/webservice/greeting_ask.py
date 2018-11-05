from flask import Flask
from flask_ask import Ask, statement, question, session
import datetime
import requests
import logging
import os

app = Flask(__name__)
app.config['ASK_APPLICATION_ID'] = 'amzn1.ask.skill.2b01e5b0-9f38-41eb-bb0c-d57ab17b5d5a'

ask = Ask(app, '/alexa_end_point')

if os.getenv('GREETINGS_DEBUG_EN', False):
    logging.getLogger('flask_ask').setLevel(logging.DEBUG)

@ask.launch
def launch():
    speech_text = '<speak>Welcome to Greetings skill. Using our skill you can greet your guests.</speak>'
    reprompt_text = '<speak>Whom you want to greet? You can say for example, say hello to John</speak>'
    return question(speech_text).reprompt(reprompt_text)

@ask.intent('HelloIntent', mapping={'first_name': 'FirstName'}, default={'first_name': 'Unknown'})
def hello(first_name):
    speech_text = 'Hello <say-as interpret-as="spell-out">{0}</say-as> {0}. '.format(first_name);
    speech_text += get_wish()
    quote = get_quote()
    speech_text += quote
    return statement('<speak>{}</speak>'.format(speech_text)).standard_card('Hello {}!'.format(first_name), quote, small_image_url='https://upload.wikimedia.org/wikipedia/commons/5/5b/Hello_smile.png',large_image_url='https://upload.wikimedia.org/wikipedia/commons/5/5b/Hello_smile.png')

@ask.intent('QuoteIntent')
def quote_intent():
    speech_text,reprompt_text = get_quote_text()
    session.attributes['quote_intent'] = True
    return question(speech_text).reprompt(reprompt_text)

def get_quote_text():
    speech_text = get_quote()
    speech_text += ' Do you want to listen to one more quote? ' 
    reprompt_text = ' You can say yes or one more. '
    speech_text = get_ssml(speech_text)
    reprompt_text = get_ssml(reprompt_text)
    return speech_text,reprompt_text

@ask.intent('NextQuoteIntent')
def next_quote_intent():
    if 'quote_intent' in session.attributes:
        speech_text,reprompt_text = get_quote_text()
        return question(speech_text).reprompt(reprompt_text)
    else:
        speech_text = get_ssml('Wrong invocation of this intent. Please say get me a quote to get quote.')
        return statement(speech_text)


def get_ssml(msg):
    return '<speak>{}</speak>'.format(msg)

@ask.intent('AMAZON.StopIntent')
def stop_intent():
    speech_text = get_ssml('Good Bye.')
    return statement(speech_text)

@ask.session_ended
def session_ended():
    return "", 200


def get_quote():
    r = requests.get('http://api.forismatic.com/api/1.0/json?method=getQuote&lang=en&format=json')
    r._content = r._content.decode('unicode_escape').encode('ascii','ignore')
    r._content = r._content.replace('\\','')
    quote = r.json()['quoteText']
    return quote

def get_wish():
    'Return good morning/afternoon/evening depending on time of the day'

    current_time = datetime.datetime.utcnow()
    hours = current_time.hour-8
    if hours <0:
        hours = 24+hours

    if hours < 12:
        return  'Good morning. ' 
    elif hours < 18:
        return 'Good afternoon. '
    else:
        return 'Good evening. '

if __name__ == '__main__':
    #app.run()
    port = int(os.getenv('PORT', 5000))
    print "Starting app on port %d" % port
    app.run(debug=False, port=port, host='0.0.0.0')
