pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'book-management'
        DOCKER_IMAGE_TAG  = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                // This will check out the code from the repository configured in the Jenkins job
                checkout scm
            }
        }

        stage('Build Application') {
            steps {
                script {
                    // Ensure you have a Maven tool configured in Jenkins Global Tool Configuration
                    // or that the 'mvn' command is available on your Jenkins agent.
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // This builds the Docker image using the Dockerfile in the project root
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Deploy to Minikube') {
            steps {
                script {
                    // This applies the Kubernetes configurations from the 'k8s' directory
                    sh 'kubectl apply -f k8s/'
                }
            }
        }
    }
}

