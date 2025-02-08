from diagrams import Diagram, Cluster
from diagrams.onprem.compute import Server
from diagrams.onprem.client import Client
# from diagrams.onprem.database import Postgresql
from diagrams.generic.storage import Storage
from diagrams.programming.framework import Spring

with Diagram("Avaliação e Recomendação de Filmes", show=False, direction="LR") as diag:
  client = Client("Cliente")
  gateway = Server("Gateway")
  db = Storage("Banco de Dados") # Postgresql("Banco de Dados")

  with Cluster("Micro Serviços"):
    auth = Spring("Autenticação")
    user = Spring("Usuários")
    film = Spring("Filmes")
    review = Spring("Avaliação")
    recs = Spring("Recomendações")

  client >> gateway

  gateway >> auth
  gateway >> user
  gateway >> film
  gateway >> review
  gateway >> recs

  user >> db
  film >> db
  review >> db

  auth >> user

  review >> film
  recs >> review
  recs >> user
