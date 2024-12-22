// send-sqs-message.js
require('dotenv').config(); // Load environment variables from .env file
const AWS = require('aws-sdk');
const { v4: uuidv4 } = require('uuid');
const fs = require('fs');
const path = require('path');

AWS.config.update({ region: 'ap-southeast-1' }); // Update to Singapore region

const sqs = new AWS.SQS();

const sendMessage = async () => {
  const queueUrl = process.env.QUEUE_URL; // Get Queue URL from environment variable

  if (!queueUrl) {
    console.error("Queue URL is not defined in the environment variables.");
    return;
  }

  const payloadPath = path.resolve(__dirname, '../payload.json');
  let payload;

  try {
    payload = JSON.parse(fs.readFileSync(payloadPath, 'utf8'));
  } catch (err) {
    console.error("Error reading payload file:", err);
    return;
  }

  const params = {
    QueueUrl: queueUrl, 
    MessageBody: JSON.stringify({
      ...payload,
      messageId: uuidv4()
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
