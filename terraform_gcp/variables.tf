variable "project_id" {
  description = "GCP Project ID"
  type        = string
}

variable "region" {
  description = "GCP Region"
  type        = string
  default     = "us-central1"
}

variable "repository_id" {
  description = "Artifact Registry Repository Name"
  type        = string
  default     = "fintech-rewards"
}
