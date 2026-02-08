# Resumen de Aprendizaje: POC de Sistema de Recompensas Fintech

Este repositorio documenta una Prueba de Concepto (POC) diseñada para demostrar una arquitectura moderna, resiliente y escalable para un sistema financiero de recompensas.

## 🎯 Objetivo de la POC
El objetivo es simular un sistema de pagos de renta donde los usuarios ganan puntos de fidelidad. El reto técnico principal es **desacoplar** el cobro del otorgamiento de puntos para garantizar que el pago siempre se procese rápido, mientras que los puntos se calculan de manera asíncrona y confiable.

## 🛠️ Tecnologías Clave Aplicadas

### 1. Java 21 & Spring Boot 3.4.1
Utilizamos la versión más moderna de Java LTS (21) para aprovechar características como los **Records** (para DTOs inmutables) y el nuevo recolector de basura ZGC. Spring Boot 3.4 nos provee el framework base con soporte nativo para imágenes Docker optimizadas.

### 2. Arquitectura Limpia (Clean Architecture)
El código no está organizado por capas técnicas (Controller, Service, Dao) sino por **Dominio**.
- **Domain:** Entidades y reglas de negocio puras.
- **Application:** Casos de uso y orquestación.
- **Infrastructure:** Implementaciones concretas (Repositorios JPA, Configuración de Kafka).
- **API:** Controladores REST (la puerta de entrada).

### 3. Comunicación Asíncrona con Apache Kafka
En lugar de que el servicio de pagos llame directamente al servicio de recompensas (HTTP sincrónico), publica un **Evento de Dominio** (`PaymentConfirmedEvent`).
- **Ventaja:** Si el servicio de recompensas cae, el usuario igual puede pagar su renta. Kafka guarda el mensaje hasta que el consumidor vuelva a estar en línea.

### 4. Patrón Consumidor Idempotente (Idempotent Consumer)
En sistemas distribuidos, un mensaje puede llegar dos veces.
- **Solución:** Antes de dar puntos, el servicio de recompensas verifica en su base de datos (`processed_events`) si ya procesó esa `transactionId`.
- **Resultado:** Integridad de datos financiera. Nunca damos puntos dobles por error.

### 5. Observabilidad con Prometheus & Grafana
No basta con que funcione; debemos saber **cómo** funciona.
- **Micrometer:** Instrumenta el código para contar cuántos puntos se han dado.
- **Prometheus:** Recolecta ("scrapea") esos números cada 5 segundos.
- **Grafana:** (Opcional en esta POC) Visualizaría esos datos en dashboards.

## 🚀 Cómo Empezar
Revisa el archivo `README.md` (en inglés) para las instrucciones técnicas de ejecución. Este proyecto es una base sólida para entender cómo las fintechs modernas construyen sistemas desacoplados y resilientes.
