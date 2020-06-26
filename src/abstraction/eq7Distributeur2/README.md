Tamara ORBAN - Tamaraorban
Léandre BERTHAULT - Araawn
Raphael CABY - R19caby
Raphael GARNIER - Rafarazh

Erreur possible dans la classe stock : null pointer exception
LIGNE 119 : if (this.chocoEnStockParEtape.get(etape).get(chocoDeMarque) == 0.)
Cette erreur ne nous est jamais arrivée localement mais est arrivée à d'autres équipes.
Nous avons remplacé la ligne 118 "while (etape <= etapeActuelle)" par "while (etape <= etapeActuelle-1)" et cela a corrigé le problème chez les autres équipes. Il semble qu'il arrive que la hashmap chocoEnStockParEtape ne s'initialise pas correctement à une étape donnée.

Erreur possible lors de la mise en péremption des stocks :
Cette erreur apparaît dans le journal des stocks en couleur rouge et indique qu'on tente de retirer des stocks une quantité supérieure au stock possédé. Néanmoins, le delta de stock entre la quantité possédée et la quantité à retirer est toujours inférieur à 0.01, on pense donc qu'il s'agit d'une erreur due aux arrondis lors des calculs. Nous n'avons pas eu le temps de régler ce problème.

Les modes kalm et panik sont à revoir car ils ne fonctionnent pas comme on le souhaiterait. En mode panik, nous sommes censés écouler un maximum de stock à prix élevés pour récupérer de l'argent, ce qui n'est pas vraiment le cas en pratique (demande trop faible ?). En général, notre acteur parvient à trouver un équilibre jusqu'à un certain nombre de steps après lesquelles il perd de l'argent irrémédiablement jusqu'à la faillite (le cours augmente exponentiellement).

Nous avons mis en place un acheteur contrat-cadre mais celui-ci est inefficace car il n'y a pas de vendeur par contrats-cadres dans la simulation.