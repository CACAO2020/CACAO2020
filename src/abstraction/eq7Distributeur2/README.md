Tamara ORBAN username : Tamaraorban
Leandre BERTHAULT username : Araawn
Raphael CABY username : R19caby
Raphael GARNIER usurname : Rafarazh

Erreur possible dans la classe stock : null pointer exception
LIGNE 119 : if (this.chocoEnStockParEtape.get(etape).get(chocoDeMarque) == 0.)
Cette erreur ne nous est jamais arrivée localement mais est arrivée à d'autres équipes.
Il semble qu'il arrive que la hashmap chocoEnStockParEtape ne s'initialie pas correctement à une étape donnée.
Nous n'avons pas su détecter la source de cette erreur (difficile puisqu'elle ne nous arrive pas).

Erreur possible lors de la mise en péremption des stocks :
Cette erreur apparaît dans le journal des stocks en couleur rouge et indique qu'on tente de retirer des stocks une quantité supérieure au stock possédé. Néanmoins, le delta de stock entre la quantité possédée et la quantité à retirer est toujours inférieur à 0.01, on pense donc qu'il s'agit d'une erreur due aux arrondis lors des calculs. Nous n'avons pas eu le temps de régler ce problème.

