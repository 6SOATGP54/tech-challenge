#!/bin/bash

if command -v kubectl &> /dev/null
then
	echo "Starting cluster..."
else
	echo "Error. Please install Kubectl"
	echo "visit: https://kubernetes.io/docs/reference/kubectl/kubectl/"
	exit 1
fi

# Secrets
kubectl apply -f infra/yamls/postgres.secret.yaml

# Deployments
kubectl apply -f infra/yamls/app.deployment.yaml

# Services
kubectl apply -f infra/yamls/redis-service.yaml
kubectl apply -f infra/yamls/postgres.service.yaml
kubectl apply -f infra/yamls/app-food-service.yaml

# AutoScale
kubectl apply -f infra/yamls/hpa.yaml
