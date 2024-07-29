@echo off

:: Check if kubectl is installed
where kubectl >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo Starting cluster...
) else (
    echo Error. Please install Kubectl
    echo visit: https://kubernetes.io/docs/reference/kubectl/kubectl/
    exit /b 1
)

:: Apply Kubernetes manifests
:: Secrets
kubectl apply -f infra/yamls/postgres.secret.yaml

:: Deployments
kubectl apply -f infra/yamls/app.deployment.yaml

:: Services
kubectl apply -f infra/yamls/redis-service.yaml
kubectl apply -f infra/yamls/postgres.service.yaml
kubectl apply -f infra/yamls/app-food-service.yaml

:: AutoScale
kubectl apply -f infra/yamls/hpa.yaml
