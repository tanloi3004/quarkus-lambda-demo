# Quarkus Lambda Native Application

This project is a Quarkus Lambda application built with a native runtime.

## Building the Project

To build the project with a native runtime, use the following command:

```sh
quarkus build --native -Dquarkus.native.container-build=true
```
Or 
```sh
```sh
quarkus build --native -Dquarkus.native.container-build=true -DskipTests
```

## Managing the Lambda Function

To create the Lambda function, use the `target/manage.sh` script:

```sh
target/manage.sh native create
```

## Deploying with AWS SAM

To deploy the application using AWS SAM, follow these steps:

1. Ensure the CloudFormation stack is deleted:

```sh
aws cloudformation wait stack-delete-complete --stack-name quarkus-native-stack
```

2. Deploy the application with guided prompts:

```sh
sam deploy --guided
```

The SAM default will load the template from `./template.yaml`.


## Invoking the Lambda Function Locally

To invoke the Lambda function locally using AWS SAM, use one of the following commands:

For the `payload` file:
```sh
sam local invoke --event payload
```

For the `payload.sqs.json` file:
```sh
sam local invoke --event payload.sqs.json
```

## Additional Information

- The `sam.native.yaml` file is used for deploying the native runtime.
- The `target/manage.sh` script includes functions for creating, deleting, and invoking the Lambda function.

For more details, refer to the [Quarkus AWS Lambda Guide](https://quarkus.io/guides/aws-lambda).