

Mélissa Tamine ; melissatamine
Thibault Avril ; thibaultavril
Luca Pinguet ; lpinguet
Martial Gil ; MarsLig 




Bug potentiel sur la fonction d'achat en bourse (du à la fonction d'estimation de la demande dans laquelle nous rencontrons des problèmes : problème méthode getVente() et problème sur la hashmap  VenteSiPasRuptureDeStock). Ou peut-être  ce problème vient des transformateurs, car en testant notre code avec  notre fillière test AchatBourseSeul, nous arrivons à acheter du chocolat en bourse.

Partie achatContratCadre codée, mais non utilisée, à finaliser car nous n'arrivons pas à l'appeler dans le next()(surement quelques bugs à corriger).

Au bout d'un certain moment, nous ne rentrons presque plus dans la fonction vendre. Le problème est que dans cette méthode, on met à jour les hashmaps d'actualisation des ventes qui sont essentielles pour certaines parties de notre code. Or nos achats se basent sur ces maps, et si elle ne contiennent pas de valeur, on n'achète plus.


