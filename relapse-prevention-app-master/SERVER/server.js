var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var port = 1337;

// Load the twilio module
var twilio = require('twilio');

// Create a new REST API client to make authenticated requests against the
// twilio back end
var client = new twilio.RestClient('AC8a451b54f075b8c4f53e12f626d0a73c', '9da3a187513d90f2e0ed1c40d71d29bd');

app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(bodyParser.json());

app.post('/sms', function(req,res) {

    console.log("Receiving post...");
    console.log("Body = " + req.body);

    var phoneNumber = req.body.num;
    var name = req.body.name;

    // Pass in parameters to the REST API using an object literal notation. The
    // REST client will handle authentication and response serialzation for you.
    client.sms.messages.create({
        to:phoneNumber,
        from:'+12482941062',
        body:'Name = ' + name + '.'
    }, function(error, message) {
        // The HTTP request to Twilio will run asynchronously. This callback
        // function will be called when a response is received from Twilio
        // The "error" variable will contain error information, if any.
        // If the request was successful, this value will be "falsy"
        if (!error) {
            // The second argument to the callback will contain the information
            // sent back by Twilio for the request. In this case, it is the
            // information about the text messsage you just sent:
            console.log('Success! The SID for this SMS message is:' + message.sid);

            console.log('Message sent on:' + message.dateCreated);
        } else {
            console.log('Oops! There was an error:');
            console.log(error)
        }

    });

})

app.get('/', function(req, res) {
    res.send('Relapse prevention app server.');
})

app.listen(port, function() {
    console.log("Listening on port " + port);
})
