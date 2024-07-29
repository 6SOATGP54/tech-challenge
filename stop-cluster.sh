#!/bin/bash

if command -v kubectl &> /dev/null
then
	echo "Stopping cluster..."
else
	echo "Error. Please install Kubectl"
	echo "visit: https://kubernetes.io/docs/reference/kubectl/kubectl/"
	exit 1
fi

# Secrets
kubectl delete -f infra/yamls/postgres.secret.yaml

# Deployments
kubectl delete -f infra/yamls/app.deployment.yaml

# Services
kubectl delete -f infra/yamls/redis-service.yaml
kubectl delete -f infra/yamls/postgres.service.yaml
kubectl delete -f infra/yamls/app-food-service.yaml

# AutoScale
kubectl delete -f infra/yamls/hpa.yaml
