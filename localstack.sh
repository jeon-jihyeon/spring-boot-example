#!/bin/bash
function create_sqs() {
    topic_name=$1
    topic_arn=$2
    message_type=${3^^}
    queue_name="$topic_name-$3"                # topic-param3
    dlq_name="$topic_name-$3-$DLQ_SUFFIX.fifo" # topic-param3-dlq

    echo "-----------------------------------------------------------"
    echo "       Create DLQ for SQS queue: $dlq_name"
    echo "-----------------------------------------------------------"
    dlq_url=$(awslocal sqs create-queue --queue-name "$dlq_name" --attributes FifoQueue=true --query 'QueueUrl' --output text)
    dlq_arn=$(awslocal sqs get-queue-attributes --queue-url "$dlq_url" --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)
    queue_attributes="RedrivePolicy='{\"deadLetterTargetArn\":\"$dlq_arn\",\"maxReceiveCount\":\"3\"}'"

    echo "-----------------------------------------------------------"
    echo "           Create a main queue: $queue_name"
    echo "-----------------------------------------------------------"
    queue_url=$(awslocal sqs create-queue --queue-name "$queue_name" --attributes "$queue_attributes" --query 'QueueUrl' --output text)
    queue_arn=$(awslocal sqs get-queue-attributes --queue-url "$queue_url" --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)

    echo "-----------------------------------------------------------"
    echo "           Subscribe SNS topic: $topic_name"
    echo "-----------------------------------------------------------"
    subscription_arn=$(awslocal sns subscribe \
            --topic-arn "$topic_arn" \
            --protocol sqs \
            --notification-endpoint "$queue_arn" | grep SubscriptionArn | awk -F' ' '{print $2}' | tr -d '"')

    echo "-----------------------------------------------------------"
    echo "    Setting subscription attributes for filtering: $message_type"
    echo "-----------------------------------------------------------"
    awslocal sns set-subscription-attributes \
            --subscription-arn "$subscription_arn" \
            --attribute-name FilterPolicy \
            --attribute-value "{ \"message-type\": \"$message_type\" }"

    echo "-----------------------------------------------------------"
    echo "                  Complete: $queue_name"
    echo "-----------------------------------------------------------"
}

function create_sns() {
    topic_name=$1
    dlq_name="$topic_name-$DLQ_SUFFIX.fifo"

    echo "-----------------------------------------------------------"
    echo "       Create a dead letter queue: $dlq_name"
    echo "-----------------------------------------------------------"
    dlq_url=$(awslocal sqs create-queue --queue-name "$dlq_name" --attributes FifoQueue=true --query 'QueueUrl' --output text)
    dlq_arn=$(awslocal sqs get-queue-attributes --queue-url "$dlq_url" --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)

    echo "-----------------------------------------------------------"
    echo "           Create a topic of sns: $topic_name"
    echo "-----------------------------------------------------------"
    awslocal sns create-topic --name "$topic_name"
    topic_arn=$(awslocal sns list-topics | grep "$topic_name" | awk -F' ' '{print $2}' | tr -d '"')

    echo "-----------------------------------------------------------"
    echo "          Setting DLQ for SNS topic: $topic_name"
    echo "-----------------------------------------------------------"
    awslocal sns set-topic-attributes --topic-arn "$topic_arn" --attribute-name RedrivePolicy --attribute-value "{\"deadLetterTargetArn\":\"$dlq_arn\"}"

    for type in "${@:2}"
    do
      create_sqs "$topic_name" "$topic_arn" "$type"
    done
}

create_sns "$TOPIC_TEAM" "create" "command"
create_sns "$TOPIC_PLAYER" "command"