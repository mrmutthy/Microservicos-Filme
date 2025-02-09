POST http://localhost:8080/usuarios/login
Content-Type: application/json
{
  "email": "usuario1@example.com",
  "senha": "senha123"
}
HTTP 200
[Captures]
token: jsonpath "$['token']"
