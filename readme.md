*Arnaud DELOURMEL*
*Tom ROUSSEAU*

# Rendu AOC

## Architecture M3

### Mise en avant des patrons de conception

#### Pattern ActiveObject et Proxy pour le `Capteur`
![](https://codimd.math.cnrs.fr/uploads/upload_b17031a8df8fb2024e62924b2f453fba.png)


#### Pattern ActiveObject et Proxy pour l'`ObserveurDeCapteur`
![](https://codimd.math.cnrs.fr/uploads/upload_32c4170b42a3f3450e0a3e528907db6d.png)



#### Pattern Strategy

![](https://codimd.math.cnrs.fr/uploads/upload_605061111f2734d07047767cff8231b0.png)
> Pour choisir la stratégie de diffusion de valeur, on utilise le Pattern Strategy. La stratégie choisi, est injecté (injection de dépendance) lors de la création de l'objet `CapteurImpl`

#### Pattern Observer
![](https://codimd.math.cnrs.fr/uploads/upload_bfcdeaf444be16b4c22ad411330498b8.png)
> Pour notifier le ou les afficheurs le pattern Observer est utilisé

### Diagramme de Séquence
On retrouve ci-dessous un diagramme de séquence du fonctionnement complet (Update + GetValue). Dans ce diagramme on utilise l'algorithme de diffusion atomique et il n'y a qu'un seul observer attaché au capteur.
![](https://codimd.math.cnrs.fr/uploads/upload_9cbf72ed216aa84c0c6af152e6865f0e.png)


### Implémentation des algos de diffusion

#### Diffusion Atomique
Pour la diffusion atomique, on utilise un système de lock. Ce lock permet de bloquer l'incrémentation du capteur et la notification des observeurs. Ce lock s'active dès qu'un tick et émis et se désactive une fois que tous les observers sont bien mis à jour.

#### Diffusion Séquentielle
Pour la diffusion séquentielle, on utilise également le lock et on stocke la valeur à diffuser dans une variable. Lorsque le lock est activé, il bloque uniquement la notification des observeurs, mais pas l'incrémentation de la valeur. Lorsque le lock est désactivé on stocke la valeur à diffuser et on notifie tous les observers. Ce lock se désactive lorsque tous les observers sont mis à jour.

#### Diffusion Époque
Pour la diffusion époque, on bloque uniquement la notification des observeurs tant que tout les observers ne sont pas mis à jours. Durant le bloquage le compteur continue de s'incrémenter.

Nb: Cette manière d'implémentation suppose que chacun des afficheurs affichera le même nombre de valeurs. Nous n'avons pas trouvé une autre implémentation permettant aux afficheurs d'afficher des sous-ensembles de taille différentes.

### Explication des Tests
#### Diffusion Atomique
Le test vérifie que toutes les valeurs diffusées ont bien été reçues par tous les observers, que les valeurs reçues par chaque observer sont triées dans l'ordre croissant, et que chaque valeur reçue par chaque observer est bien une des valeurs produites par le capteur. Pour cela, le capteur stocke la liste des valeurs envoyée dans l'ordre chronologique.

#### Diffusion Séquentielle
Le test vérifie que tous les afficheurs affiche les même valeurs, que les valeurs reçues par chaque observer sont triées dans l'ordre croissant, et que chaque valeur reçue par chaque observer est bien une des valeurs produites par le capteur.

#### Diffusion Époque
Le test vérifie que les valeurs reçues par chaque observer sont triées dans l'ordre croissant, et que chaque valeur reçue par chaque observer est bien une des valeurs produites par le capteur.

### Rapport de Test
![](https://codimd.math.cnrs.fr/uploads/upload_54f5927fa68da1d8add1aa77e33e1fbb.png)

Toutes les classes sont couvertes par nos tests JUnit. Quelques lignes dans l'afficheur ne sont pas couvertes. Cela est dû au cas d'erreur que nous n'avons pas testé.


