clear
####USER COMMANDS######
KEYCLOAK_URL=http://localhost:8080/auth/realms
KEYCLOAK_REALM=test

#KEYCLOAK_CLIENT_ID=user
#KEYCLOAK_CLIENT_SECRET=user
#
#TKN=$(curl -X POST "${KEYCLOAK_URL}/${KEYCLOAK_REALM}/protocol/openid-connect/token" \
# -H "Content-Type: application/x-www-form-urlencoded" \
# -d "username=${KEYCLOAK_CLIENT_ID}" \
# -d "password=${KEYCLOAK_CLIENT_SECRET}" \
# -d "grant_type=password" \
# -d "client_id=login-app" | jq -r ".access_token")

#echo "$TKN"

#curl -H "Authorization: Bearer $TKN" http://localhost:9000/api/products

#curl -H "Authorization: Bearer $TKN" http://localhost:9000/api/products/1

#curl -X POST http://localhost:9000/api/products  \
#-H "Content-Type: application/json" \
#-H "Authorization: Bearer $TKN" \
#-d '{"title":"updatedTitle"}'

#curl -X PUT http://localhost:9000/api/products  \
#-H "Content-Type: application/json" \
#-H "Authorization: Bearer $TKN" \
#-d '{"title":"updatedTitle"}'

#curl -H "Authorization: Bearer $TKN" -X DELETE http://localhost:9000/api/products/1


####MODERATOR COMMANDS######
KEYCLOAK_CLIENT_ID=moderator
KEYCLOAK_CLIENT_SECRET=moderator

TKN=$(curl -X POST "${KEYCLOAK_URL}/${KEYCLOAK_REALM}/protocol/openid-connect/token" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=${KEYCLOAK_CLIENT_ID}" \
 -d "password=${KEYCLOAK_CLIENT_SECRET}" \
 -d "grant_type=password" \
 -d "client_id=login-app" | jq -r ".access_token")

#curl -H "Authorization: Bearer $TKN" http://localhost:9000/api/products

#curl -H "Authorization: Bearer $TKN" http://localhost:9000/api/products/1

#curl -X POST http://localhost:9000/api/products \
#-H "Content-Type: application/json" \
#-H "Authorization: Bearer $TKN" \
#-d '{"title":"newTitle"}'

#curl -X PUT http://localhost:9000/api/products/1 \
#-H "Content-Type: application/json" \
#-H "Authorization: Bearer $TKN" \
#-d '{"title":"updatedTitle"}'

curl -H "Authorization: Bearer $TKN" -X DELETE http://localhost:9000/api/products/1