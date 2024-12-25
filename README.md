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

## Building and Deploying with Docker

To build a Docker image for the Lambda function, use the `docker/Dockerfile.lambda.native` file.

### Building the Docker Image

Navigate to the `docker` directory and build the Docker image:

```sh
cd docker
docker build -t quarkus-lambda-native -f Dockerfile.lambda.native .
```

### Creating a Repository and Pushing the Image

1. Create a new ECR repository:

```sh
aws ecr create-repository --repository-name quarkus-lambda-native
```

2. Tag the Docker image:

```sh
docker tag quarkus-lambda-native:latest <aws_account_id>.dkr.ecr.<region>.amazonaws.com/quarkus-lambda-native:latest
```

3. Authenticate Docker to your ECR registry:

```sh
aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin <aws_account_id>.dkr.ecr.<region>.amazonaws.com
```

4. Push the Docker image to the ECR repository:

```sh
docker push <aws_account_id>.dkr.ecr.<region>.amazonaws.com/quarkus-lambda-native:latest
```

### Defining the Image for the Lambda Function

Update your SAM template to use the Docker image for the Lambda function:

```yaml
Resources:
  QuarkusLambdaFunction:
    Type: AWS::Serverless::Function
    Properties:
      PackageType: Image
      ImageUri: <aws_account_id>.dkr.ecr.<region>.amazonaws.com/quarkus-lambda-native:latest
      MemorySize: 512
      Timeout: 15
```

For more details, refer to the [Quarkus AWS Lambda Guide](https://quarkus.io/guides/aws-lambda).