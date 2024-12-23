#!/bin/bash
function create_sqs_with_dlq() {
    topic_name=$1
    queue_name="$topic_name-$2"                # param1-param2
    dlq_name="$topic_name-$2-$DLQ_SUFFIX.fifo" # param1-param2-dlq.fifo

    echo "-----------------------------------------------------------"
    echo "       Create DLQ for SQS queue: $dlq_name"
    echo "-----------------------------------------------------------"

    dlq_url=$(awslocal sqs create-queue \
            --queue-name "$dlq_name" \
            --endpoint-url "$ENDPOINT_URL" \
            --attributes FifoQueue=true \
            --query 'QueueUrl' --output text)
    dlq_arn=$(awslocal sqs get-queue-attributes \
            --queue-url "$dlq_url" \
            --attribute-names QueueArn \
            --query 'Attributes.QueueArn' --output text)

    queue_attributes="RedrivePolicy='{\"deadLetterTargetArn\":\"$dlq_arn\",\"maxReceiveCount\":\"3\"}'"

    echo "-----------------------------------------------------------"
    echo "           Create a main queue: $queue_name"
    echo "-----------------------------------------------------------"
    queue_url=$(awslocal sqs create-queue --queue-name "$queue_name" --endpoint-url "$ENDPOINT_URL" --attributes "$queue_attributes" --query 'QueueUrl' --output text)
    queue_arn=$(awslocal sqs get-queue-attributes --queue-url "$queue_url" --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)
}

function subscribe_sns() {
    topic_arn=$1
    queue_arn=$2
    message_type=${3^^}

    echo "-----------------------------------------------------------"
    echo "                  Subscribe SNS topic"
    echo "-----------------------------------------------------------"
    subscription_arn=$(awslocal sns subscribe \
            --topic-arn "$topic_arn" \
            --protocol sqs \
            --notification-endpoint "$queue_arn" \
            --endpoint-url "$ENDPOINT_URL" | grep SubscriptionArn | awk -F' ' '{print $2}' | tr -d '"')

    echo "-----------------------------------------------------------"
    echo "     Setting attributes for filtering: $message_type"
    echo "-----------------------------------------------------------"
    awslocal sns set-subscription-attributes \
            --subscription-arn "$subscription_arn" \
            --attribute-name FilterPolicy \
            --attribute-value "{ \"message-type\": [\"$message_type\"] }" \
            --endpoint-url "$ENDPOINT_URL"
}

function create_sns() {
    topic_name=$1
    dlq_name="$topic_name-$DLQ_SUFFIX.fifo"

    echo "-----------------------------------------------------------"
    echo "       Create a dead letter queue: $dlq_name"
    echo "-----------------------------------------------------------"
    dlq_url=$(awslocal sqs create-queue --queue-name "$dlq_name" --endpoint-url "$ENDPOINT_URL" --attributes FifoQueue=true --query 'QueueUrl' --output text)
    dlq_arn=$(awslocal sqs get-queue-attributes --queue-url "$dlq_url" --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)

    echo "-----------------------------------------------------------"
    echo "           Create a topic of sns: $topic_name"
    echo "-----------------------------------------------------------"
    awslocal sns create-topic --name "$topic_name" --endpoint-url "$ENDPOINT_URL"
    topic_arn=$(awslocal sns list-topics | grep "$topic_name" | awk -F' ' '{print $2}' | tr -d '"')

    echo "-----------------------------------------------------------"
    echo "          Setting DLQ for SNS topic: $topic_name"
    echo "-----------------------------------------------------------"
    awslocal sns set-topic-attributes --topic-arn "$topic_arn" --attribute-name RedrivePolicy --attribute-value "{\"deadLetterTargetArn\":\"$dlq_arn\"}"

    for type in "${@:2}"
    do
      queue_arn=""
      create_sqs_with_dlq "$topic_name" "$type"
      subscribe_sns "$topic_arn" "$queue_arn" "$type"
    done
}

# fixme: fixing to FilterPolicy of localstack
# create_sns "$TOPIC_TEAM" "create" "command"
# create_sns "$TOPIC_PLAYER" "command"
create_sqs_with_dlq "team" "create-domain"
create_sqs_with_dlq "team" "create-persistence"
create_sqs_with_dlq "player" "create-persistence"