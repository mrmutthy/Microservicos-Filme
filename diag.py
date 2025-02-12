from diagrams import Diagram, Cluster
from diagrams.onprem.compute import Server
from diagrams.onprem.client import Client
from diagrams.generic.storage import Storage
from diagrams.programming.framework import Spring

with Diagram("Avaliação e Recomendação de Filmes", show=False, direction="LR") as diag:
  client = Client("Cliente")
  gateway = Server("Gateway")

  with Cluster("Banco de Dados"):
    db_u = Storage("Usuários")
    db_f = Storage("Filmes")
    db_a = Storage("Avaliações")

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

  user >> db_u
  film >> db_f
  review >> db_a

  auth >> user

  review >> film
  recs >> review
  recs >> user
