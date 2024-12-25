// send-sqs-message.js
const AWS = require('aws-sdk');
const { v4: uuidv4 } = require('uuid');

AWS.config.update({ region: 'ap-southeast-1' }); // Update to Singapore region

const sqs = new AWS.SQS();

const sendMessage = async () => {
  const params = {
    QueueUrl: 'https://sqs.us-east-1.amazonaws.com/807149668622/InputQueue', // Replace with Queue A URL
    MessageBody: JSON.stringify({
      url: 'https://www.lottemart.vn',
      options: [{ key: 'value' }],
      request_id: uuidv4()
    })
  };

  try {
    const data = await sqs.sendMessage(params).promise();
    console.log("Message sent. MessageId:", data.MessageId);
  } catch (err) {
    console.error("Error sending message:", err);
  }
};

sendMessage();
