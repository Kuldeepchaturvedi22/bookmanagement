pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    environment {
        DOCKER_HUB_USER = '5122771'
        APP_NAME = 'bookmanagement'
        IMAGE_TAG = "v${BUILD_NUMBER}"

        KUBECONFIG = 'C:\\Users\\Kuldeep\\.kube\\config'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Kuldeepchaturvedi22/bookmanagement'
            }
        }

        stage('Build JAR') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${DOCKER_HUB_USER}/${APP_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Push to Hub') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub-creds') {
                        bat "docker push ${DOCKER_HUB_USER}/${APP_NAME}:${IMAGE_TAG}"
                        bat "docker tag ${DOCKER_HUB_USER}/${APP_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${APP_NAME}:latest"
                        bat "docker push ${DOCKER_HUB_USER}/${APP_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    echo 'Deploying to Minikube...'
                    // Apply the yaml file we created in Step 2
                    bat "kubectl apply -f k8s/deployment.yaml"

                    // Restart to force the new image download
                    bat "kubectl rollout restart deployment/spring-boot-app"
                }
            }
        }
    }
}