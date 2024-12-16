#!/bin/sh
echo "Initialize localstack"
echo "Create a sqs queue"
awslocal sqs create-queue --queue-name "$SQS_QUEUE_NAME" --port "$SQS_PORT" --attributes FifoQueue=true