# base version
This version do nothing, only created by spring boot initialization (https://start.spring.io/)

# Components and references
- Java 21
- Spring boot 3.5.0
- Spring web
- Spring devtools
- Spring validation
- Spring Data JPA
- lombook
- FlyWay
- Postgres 16

# Ports
- 5432: postgres
- 8000: application

# Pre-requeriments
- Still with ports used by solution released;
- Docker

# Execution by compose
- Runs docker 
```bash
docker compose -p stockcontrol up -d 
```


