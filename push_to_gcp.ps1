$ErrorActionPreference = "Stop"

# CONFIGURACIÓN (Cambia esto)
$PROJECT_ID = "TU_PROYECTO_ID_AQUI"
$REGION = "us-central1"
$REPO_NAME = "fintech-rewards"
$REGISTRY = "$REGION-docker.pkg.dev/$PROJECT_ID/$REPO_NAME"

Write-Host "Configurando autenticación con GCP..." -ForegroundColor Cyan
# Autentica docker con Google Artifact Registry
gcloud auth configure-docker "$REGION-docker.pkg.dev"

Write-Host "Construyendo imágenes locales..." -ForegroundColor Cyan
# Build Payment Service
docker build -t payment-service -f payment-service/Dockerfile .
docker tag payment-service:latest "$REGISTRY/payment-service:latest"

# Build Rewards Service
docker build -t rewards-service -f rewards-service/Dockerfile .
docker tag rewards-service:latest "$REGISTRY/rewards-service:latest"

Write-Host "Subiendo imágenes a Artifact Registry..." -ForegroundColor Cyan
docker push "$REGISTRY/payment-service:latest"
docker push "$REGISTRY/rewards-service:latest"

Write-Host "¡Listo! Imágenes en la nube de Google." -ForegroundColor Green
