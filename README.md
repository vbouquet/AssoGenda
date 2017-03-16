# AssoGenda

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![GitHub version](https://badge.fury.io/gh/bouquet2%2Fassogenda.svg)](http://badge.fury.io/gh/bouquet2%2Fassogenda)

Master | Develop
--- | --- | --- 
[![Build Status](https://travis-ci.org/Bouquet2/AssoGenda.svg?branch=master)](https://travis-ci.org/Bouquet2/AssoGenda) | [![Build Status](https://travis-ci.org/Bouquet2/AssoGenda.svg?branch=develop)](https://travis-ci.org/Bouquet2/AssoGenda)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/726fc6a1a7514acc91f321082a3d2b51?branch=master)](https://www.codacy.com/app/Bouquet2/AssoGenda?utm_source=github.com&utm_medium=referral&utm_content=Bouquet2/AssoGenda&utm_campaign=badger) | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/726fc6a1a7514acc91f321082a3d2b51?branch=develop)](https://www.codacy.com/app/Bouquet2/AssoGenda?utm_source=github.com&utm_medium=referral&utm_content=Bouquet2/AssoGenda&utm_campaign=badger)
[![Coverage Status](https://coveralls.io/repos/github/Bouquet2/AssoGenda/badge.svg?branch=master)](https://coveralls.io/github/Bouquet2/AssoGenda?branch=master) | [![Coverage Status](https://coveralls.io/repos/github/Bouquet2/AssoGenda/badge.svg?branch=develop)](https://coveralls.io/github/Bouquet2/AssoGenda?branch=develop)

Asso’Genda est une application android de gestion et de partage d’événements associatifs ayant deux objectifs principaux :  

  - Permettre aux étudiants de différentes universités de générer un agenda des événements associatifs étudiants qui leur sont accessible au sein de leur université et en fonction de leurs préférences. Une bonne opportunité pour découvrir des associations et les suivre.
  - Centraliser les événements associatifs par université. Une association peut gérer ses informations et ses membres. Elle peut créer et mettre en avant ses événements et avoir plus de visibilité.
  
 Vous pouvez voir [AssoGenda website](https://bouquet2.github.io/AssoGenda/) pour plus d'informations.

### Installation ###
AssoGenda utilise android sdk version 23.0.2. 
Gradle télécharge automatiquement le sdk si vous avez déjà accepté les licences avec le SDK manager (cf: https://goo.gl/mxlqC5) et que soit votre variable d'environnement ANDROID_HOME est présente ou qu'un fichier local.properties est présent avec le chemin vers votre sdk local.

Sinon vous pouvez télécharger le sdk grâce au sdk manager disponible ici : https://goo.gl/z45c8g ensuite ajoutez la variable d'environnement ANDROID_HOME={path-to-sdk}/android-sdk-{os-version} ou créez un fichier local.properties dans le répertoire du projet avec la ligne "sdk.dir={path-to-sdk}/android-sdk-{os-version}"

### Use Case ###

![](/diagrams/usecase_associations.png)

![](/diagrams/usecase_etudiant.png)
