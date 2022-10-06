# CryptageVote



## Description

Classes permettant le crypage de messages afin d'envoyer des votes sécurisés.

Le projet est composé de 2 packages :

- Concept : contient les classes de fonctionnement du cryptage
- Cryptage : permet une utilisation simplifiée du modèle de cryptage


## Package Concept

Le Package Concept contient les classes suivantes:

- Agrege : permet l'agrégation de deux messages cryptés
- Decrypt : décrypte un message crypté 
- Encrypt : encrypte un message clair
- KeyGen : génère les clés pour crypter, décrypter et agréger

**KeyGen**

prend en entrée une longueur de clé en bits

retourne :  

- clé privée (de la forme BigInteger x)
- clé publique (de la forme HashMap<String, BigInteger> = (p, g, h))

**Encrypt**




