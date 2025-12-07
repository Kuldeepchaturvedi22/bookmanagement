#!/bin/bash

# Set your GCP project ID
PROJECT_ID="your-gcp-project-id"

echo "Setting up GCP project..."
gcloud config set project $PROJECT_ID

echo "Enabling required APIs..."
gcloud services enable cloudbuild.googleapis.com
gcloud services enable run.googleapis.com
gcloud services enable containerregistry.googleapis.com

echo "Creating Cloud Build trigger..."
gcloud builds triggers create github \
  --repo-name=book-management \
  --repo-owner=your-github-username \
  --branch-pattern="^main$" \
  --build-config=cloudbuild.yaml

echo "Building and deploying application..."
gcloud builds submit --config=cloudbuild.yaml .

echo "Deployment complete!"
echo "Your application will be available at the Cloud Run URL provided above."