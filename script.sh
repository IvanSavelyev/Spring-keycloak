clear

KEYCLOAK_URL=http://localhost:8080/auth/realms
KEYCLOAK_REALM=test
KEYCLOAK_CLIENT_ID=user
KEYCLOAK_CLIENT_SECRET=user

TKN=$(curl -X POST "${KEYCLOAK_URL}/${KEYCLOAK_REALM}/protocol/openid-connect/token" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=${KEYCLOAK_CLIENT_ID}" \
 -d "password=${KEYCLOAK_CLIENT_SECRET}" \
 -d "grant_type=password" \
 -d "client_id=login-app" | jq -r ".access_token")

echo "$TKN"

curl -H "Authorization: Bearer $TKN" http://localhost:9000/api/products