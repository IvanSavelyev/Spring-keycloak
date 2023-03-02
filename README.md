# Spring keycloak

Test spring boot project with keycloak (authorization server).

## Start

To run all apps just run following command in a terminal from docker directory.

```bash
> docker-compose up
```
or
```bash
> docker compose up
```


## User credentials

| Username  | Password | Roles           |
|-----------| -------- |-----------------|
| user      | user | USER            |
| moderator | moderator | USER, MODERATOR |


To enter the Keyclaok admin page use `http://localhost:8080` url where credentials are `admin`, `password`.

For check api end points run script.sh from root directory

## API Description

| Action      | HTTP   | URI                  | Access        |
|-------------|--------|----------------------|---------------|
| Get all     | GET    | /api/products        | authenticated |
| Get by id   | GET    | /api/products/{{id}} | authenticated |    
| Creation    | POST   | /api/products        | MODERATOR     |
| Update      | PUT    | /api/products/{{id}} | MODERATOR     | 
| Delete      | DELETE | /api/products/{{id}} | MODERATOR     |

