TP Android
===================
## Objectif
L'objectif de ce TP est de s'initier au développement Android, d'assimiler les bases essentielles et de découvrir des librairies utilitaires pour pouvoir créer des applications simples et compatibles avec la majorité des terminaux. 
On développera un petit jeu :  
Le but du jeu est simple, éliminer le maximum d'hélicoptères avant qu'ils s'échappent et éliminent les humains.
5 hélicoptères sont suffisants pour détruire la population.

Prérequis : 
     - Des bases avancées en OCa... **JAVA**. (Vous êtes en 5ème année)
     - XML. (Vous venez de passez l'examen du BDA donc pas d'excuses)
     - Un cerveau fonctionnel. (No comment)

*NB: En cas de blocage, servez vous des hashtags/indices :) *


----------


## Commencer!
Commencez par Lancer Android Studio, l'IDE officiel d'Android, puis créez un nouveau projet: 

 - **Start a new Android Studio project.** 
ou
 - **File > New Project.**


On choisit un nom pour notre jeu puis la version minimale d'Android qui supportera notre jeu et comme on est en INFO et pas en GCU on partira "from scratch" en choisissant 'Empty Activity' comme modèle d'application et pour finir "Finish" :D.

Afin de compiler et exécuter notre application, il nous faut évidemment un environnement Android. Pour ce faire, on crée un AVD (Android Virtual Device) à l'aide de l'outil AVD Manager :

 - ![alt text](http://i.imgur.com/3hIBuuX.png "Logo Title Text 1")
 ou
 - **Tools > Android > AVD Manager**
 
NB: Vous pouvez sauter cette étape si vous voulez utiliser votre propre téléphone pour l’exécution.
On peut désormais compiler et lancer notre application. 

La majorité des jeux sont affichés en mode plein écran pour rendre l’expérience plus immersive. 

 1. Créez une fonction `setToFullScreenMode()` et utilisez-la pour basculer le jeu en mode plein écran dès son lancement.

>  Hashtags:
>  `#setSystemUiVisibility(), #OnCreate, #View`
 
----------
## Présentation
Non, on ne va pas refaire la présentation. Cette fois il s'agit de la couche présentation de notre joli jeu.
#### Background
Avant de mettre un arrière plan, on veut imposer l'orientation horizontale à notre jeu. Rendez-vous alors dans le fichier `AndroidManifest.xml` et rajoutez à la balise `<activity>` ces paramètres:
> `android:configChanges="orientation|keyboardHidden|screenSize"`
> `android:screenOrientation="landscape"`

Maintenant pour rendre le jeu joyeux et coloré (ou pâle et déprimant), rajoutez un joli arrière plan a votre jeu.

Pour ne pas avoir de problèmes de fluidité ou d'allocation de mémoire pour les images de grandes tailles, on vous fournis un Jar ici : https://git.io/vMlvV , qui crée à partir d'une image des duplicatas de différentes tailles pour prendre en charge toutes les densités de pixel des différents écrans.  

Si vous n'avez pas d'inspiration, des ressources vous sont fournies à : https://git.io/vMWj8
 
> Hashtags:
>  `#getWindow #setBackgroundDrawableResource`

----------

#### Mini tableau de bord
Comme chaque jeu qui se respecte on doit avoir des niveaux et un score. Pour cela :

3. Personnalisez l’interface (xml) de votre jeu et rajoutez y une barre d'outils afin d'obtenir un écran comme indiqué si dessous:

    ![alt text](https://gitlab.insa-rennes.fr/mohammed-amine-alifdal/tp-android/raw/0042b02d8f098880b6e45ba9c1f0839c485bd3fe/captures/gameActivity.PNG "MainActivity")
    
Utilisez ces valeurs : 

>     [à copier dans colors.xml]
>     
>     <color name="lightGrey">#DDDDDD</color>
>     
>     [à copier dans in strings.xml]
>     
>     <string name="level_label">Level:</string>
>     <string name="score_label">Score:</string>
>     <string name="max_number">99</string>
>     <string name="play_game">Play game</string>
>
>     [à copier dans dimens.xml]
>
>     <dimen name="status_text_size">20sp</dimen>


----------


#### Les objets du jeu
Maintenant qu'on a personnalisé un peu l'interface de notre jeu, on passe à la création des objets qu'on utilisera dans le jeu.

Pour modéliser les humains on utilisera ces deux ressources/icônes du Material Design fournies par Google: 

human.xml

![human](https://gitlab.insa-rennes.fr/mohammed-amine-alifdal/tp-android/raw/0042b02d8f098880b6e45ba9c1f0839c485bd3fe/captures/human.PNG)

dead.xml

![dead human](https://gitlab.insa-rennes.fr/mohammed-amine-alifdal/tp-android/raw/0042b02d8f098880b6e45ba9c1f0839c485bd3fe/captures/dead.PNG)

Récupérez les fichiers depuis ce lien : https://git.io/vMleF, mettez les dans votre dossier Drawable et en utilisant un LinearLayout horizontal, alignez 5 copies de la ressource **human** de droite à gauche. Utilisez :
>     [à copier dans colors.xml]
>     
>     <color name="iconColor">#000000</color>
>     
>     [à copier dans dimens.xml]
>
>     <dimen name="icon_size">40dp</dimen>

Le resultat devrait ressembler à ça :

![mainActivity with Human](https://gitlab.insa-rennes.fr/mohammed-amine-alifdal/tp-android/raw/0042b02d8f098880b6e45ba9c1f0839c485bd3fe/captures/humans.PNG)

*NB: On n'a pas à inclure ces ressources en différentes tailles pour car ce sont des Vectors, donc redimensionnables dynamiquement.*


----------


Maintenant avec l'objet Helicopter

Ajoutez l'image helicopter aux ressources de l'application.
Créez un package Utils, rajoutez une classe pixelHelper, et copiez y cette methode statique :

     public static int pixelsToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }

Comme son nom l'indique, cette méthode reçoit le nombre de pixels absolus, une référence sur le contexte du jeu, et retourne la même valeur mais en pixels relatifs (Device Indepedent Pixels) de l'appareil où le jeu est lancé.

Maintenant on est prêt pour créer notre objet Helicopter.   

 1. Créez une classe Helicopter qui hérite de ImageView (Android.widget).
 2. Créez un constructeur par défaut, et un constructeur qui reçoit : 
 - le contexte où on rajoutera l'objet Helicopter,  context:Context 
 - la couleur de l'hélicoptère, couleur:int 
 - la hauteur en pixels absolus, aHeight: int
 3. Associez l'image à l'hélicoptère au moment de la création de l'objet, récupérez les dimensions relatives à l'écran sachant que Width = height / 2  et rajoutez-les à l'objet pour les mettre à l'échelle de l'écran à l'aide de cette ligne de code:
 
 > `this.setLayoutParams(new ViewGroup.LayoutParams(dpWidth, dpHeight)); `
 
*NB: Vous pouvez mettre une couleur à votre image, Une idée simple serait d'attribuer des valeurs RGB aléatoires (0 - 255) ou de créer une liste statique des couleurs. Utilisez ce bout de code: *

```
        this.setImageResource(R.drawable.copter);
        this.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
```

Maintenant on peut créer des objets Helicopter et les ajouter à l'écran de l'application. Vous pouvez tester l'ajout des hélicoptères avec ce code: 

     mContentView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        Helicopter heli = new Helicopter(MainActivity.this,
                                                 Color.argb(255, 255, 0, 0), 
                                                 100);
                        heli.setX(motionEvent.getX());
                        heli.setY(motionEvent.getY());
                        mContentView.addView(heli);
                    }    
                    return false;
                }
            });




----------


####  Animation

Pour pouvoir animer le mouvement des hélicoptères du bas vers le haut, on aura besoin d'implémenter l'animation dans chaque objet Helicopter.

1. Commencer par implémenter les interfaces `Animator.AnimatorListener` et `valueAnimator.AnimatorUpdateListener` pour recevoir les `Callbacks` de chaque petit mouvement d'animation après qu'il soit calculé.
2. Créer une méthode `launchHelicopter` avec comme paramètres la hauteur de l'écran (`screenHeight`) et la durée de l'animation (`duration`).
2. Faites-nous une animation des hélicoptères avec un mouvement vertical du bas (`screenHeight`) vers le haut (`0f`).  
    - Utilisez un objet de type `ValueAnimator` (dans la classe) qui s'occupera de l'animation.

> Hashtags:
> `#setDuration(), #setFloatValues(), #setInterpolator(), #LinearInterpolator, #setTarget(this), #trop_cest_trop`


----------
Lancement des Helicopter: 

On doit maintenant récupérer la hauteur (`screenHeight`) et la largeur (`screenWidth`) de l'écran où le jeu est lancé. Pour cela, juste après la mise du jeu en mode plein écran on les demande à l'aide de cet événement:

      ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        mScreenWidth = mContentView.getWidth();
                        mScreenHeight = mContentView.getHeight();
                        
                    }
                });
            }

Désormais, vous pouvez tester votre animation en lançant les hélicoptères de la position du bas (`screenHeight`) avec l'appel `heli.launchHelicopter(screenHeight, 3000);`après avoir rajouté l'objet `heli` à votre activité.
 
----------

## Logique
Il ne s'agit pas d'une partie Prolog, mais de la partie "Moteur" de notre jeu.
#### Les niveaux
On va modéliser les niveaux de façon à ce que plus on monte en niveau, plus la vitesse des hélicoptères augmente et plus ça devient difficile de les éliminer.

Nous allons donc nous baser sur le paramètre `duration` de la méthode `launchHelicopter` qui détermine la durée du mouvement de l'hélicoptère du bas vers le haut de l'écran. Par exemple, l'animation la plus lente durera 8 secondes. Cette durée baissera jusqu’à 1 seconde. Nous rajoutons donc ces constantes :

    private static final int MIN_ANIMATION_DELAY = 500;
    private static final int MAX_ANIMATION_DELAY = 1500;
    private static final int MIN_ANIMATION_DURATION = 1000;
    private static final int MAX_ANIMATION_DURATION = 8000;
    private int mLevel;
    private int mScore;
   
Pour gérer les niveaux, créez une méthode `startLevel()`, déclarez et instanciez-y une instance du `HelicopterLauncher` (dont le code est fourni ici : https://git.io/vMleN ) puis lancer sa méthode `execute(currentLevel)`. Cela exécutera un job asynchrone en arrière plan qui s'occupera de lancer les hélicoptères pour un niveau donné.
N'oubliez pas de copier la fonction  `launchHelicopter(int position, int duration)` aussi.

Pour finir, créez un `onClickHandler` pour le bouton que vous avez mis dans la barre d'outils, qui va appeler la fonction `startLevel()`.
Astuce: Placez vous sur le bouton  dans le fichier `xml` de l'activité concernée, ajoutez un attribut `OnClick="something"` puis `Alt+Entr`  et choisir `"Ajouter un event handler"`.

Créez aussi une méthode `finishLevel()` et mettez y ce code pour l'instant: 

```
   private void finishLevel() {
        Toast.makeText(this, String.format("You finished level %d", mLevel),
                Toast.LENGTH_SHORT).show();
    }
```

Vous pouvez tester votre code. Maintenant, à chaque clic sur le bouton "Play Game", vous montez d'un niveau. Chaque niveau comprend un nombre (`HELICOPTERS_PER_LEVEL`) d'hélicoptères. Le premier groupe d'hélicoptères prend 8 secondes pour disparaître et le prochain groupe prend moins de temps.


----------

 
#### Chasser les hélicoptères 
Jusqu’à maintenant, on a réalisé nos animations, et on a trouvé un moyen de gestion des niveaux. Maintenant on veut permettre au joueur de monter en score et en niveau, en détruisant les hélicoptères.
On va dire que si l'hélicoptère arrive au haut de l'écran, il s'enfuit et cause des dégâts humains. Par-contre si un joueur touche l'hélicoptère avec son doigt avant qu'il ne s'enfuit, il monte en score. Et la population reste intacte.

Pour implémenter cette logique, on commence par la classe Helicopter. Cette classe écoutera les `Touch Events` et quand cela se produira, elle informera l'activité principale du jeu `main_activity`.
On utilisera cette interface :

     public interface HelicopterListener {
            void killHelicopter(Helicopter helicopter, boolean userTouch);
        }
        
Pour garder une trace, on rajoute une référence du Listener dans notre classe Helicopter, et on lui caste le context de notre application.

     private HelicopterListener mListener; 
      public Helicopter(Context context, int color, int rawHeight) {
        super(context);

        mListener = (HelicopterListener) context;
        ...
        }
On a désormais un moyen pour envoyer un message à l'activité principale du jeu.
On rajoute un `Boolean` par defaut à `false` pour garder une trace de l'état de l'hélicoptère (détruit ou pas). Cette valeur sera mise à `true` quand le joueur touche l'hélicoptère.

 1. Pour le cas ou c'est **le joueur** qui détruit l'hélicoptère:

    Rajoutez un `onTouchEvent(MotionEvent event)`  dans la classe `Helicopter`:
    Si l'hélicoptère n'est pas détruit et l'**action** de l’événement est une  `MotionEvent.ACTION_DOWN`.
             - détruire l'hélicoptère à l'aide du `Listener` 
             - mettre à jour le `Boolean` 
             - annuler son animation

 2. Pour le cas où l'hélicoptère prend fuite:
     On le détruit quand même à l'aide du `Listener`. 
     `#onAnimationEnd`

Maintenant on est prêt pour implémenter l'interface`HelicopterListener` et redéfinir sa méthode unique dans notre `MainActivity`.  
On veut faire disparaître l'hélicoptère de l'écran quand il se détruit, augmenter le score si c'est le joueur qui l'a détruit et mettre à jour l'affichage avec une méthode qu'on va appeler `updateDisplay()`.
Implémentez la méthode `destroyHelicopter(Helicopter helicopter, boolean userTouch)`.

Testez votre jeu avec les nouvelles modifications et chassez des hélicoptères!

----------

#### Dynamisation du tableau de bord
La barre d'outils de notre jeu contient déjà deux TextView. On va les utiliser pour afficher le niveau et le score.
1. Recuperez-les  (`score_display, level_display`) au **démarrage du jeu** à l'aide de la fonction `findViewById()`.
2. Mettez à jour ces éléments dans la méthode `updateDisplay()`.
3. rajouter cette méthode dans `onCreate()` et `startLevel()` pour réinitialiser l'affichage a chaque nouvelle partie.
4. Testez votre application pour voir le changement au moment de la destruction d'un hélicoptère.

---
#### Gestion des ressources
On est capable, de calculer le score des hélicoptères battues. On essaye de maintenant de limiter les dégâts humains causés par les hélicoptères qui se sont enfuits. 
Pour rappel, le but du jeu est d’éliminer le plus grand nombre des hélicoptères en minimisant les dégâts humains. Le jeu s'arrête (Game Over) quand toute la population est éliminée (N ressources humaines épuisées, vous pouvez considérer que 1 unité = 1000). 
On gérera cela avec la constante `POPULATION_NUMBER`, la variable `mHumanKilled` et une liste de références sur les `ImageView` qu'on remplira pendant le démarrage du jeu (`onCreate`).
```
private static final int POPULATION_NUMBER = 5;
private int mLevel, mScore, mHumanKilled;

private List<ImageView> mHumanImages = new ArrayList<>();
```
La liste nous permettra de contrôler ce que le joueur verra quand un hélicoptère s'enfuit. Rendez vous dans la méthode destroyHelicopter() et faites les modifications nécessaires. 
Un modèle pour vous aider :
```
@Override
    public void destroyHelicopter(Helicopter helicopter, boolean userTouch) {
        mContentView.removeView(helicopter);
        if (userTouch) {
            mScore++;
        } else {
            mHumanKilled++;
            if (mHumanKilled <= mHumanImages.size()) {
                //TODO : Remplacer la ressource 'human' de l'imageView toute à gauche 
                //par la ressource 'dead'
            }
            if (mHumanKilled == POPULATION_NUMBER) {
                gameOver(true); //TODO: plus tard :D
                return;
            } else {
                Toast.makeText(this, "Missed One: Humans dying !!", Toast.LENGTH_SHORT).show();
            }
        }
        updateDisplay();
    }
```

Testez votre code après les modifications, et sacrifiez 1000 humains en laissant un hélicoptère s'enfuir pour voir le changement visuel au niveau des images.  

----
#### Finalisation

On essaye maintenant de finaliser le jeu, faire un peu du ménage en définissant le reste de sa logique. 
On commence d'abord par garder la référence de tous les hélicoptères (à chaque création), pour pouvoir les détruire quand on arrête le jeu.
```
private List<Helicopter> mHelicopters = new ArrayList<>();
```
Modifiez le code tel que :
1. A chaque création ou destruction d'hélicoptère, ajoutez ou supprimez de la liste.
2. Quand le jeu est terminé (GameOver) supprimez tous les hélicoptères restants (Inspirez vous du model ci-dessous).
```
private void gameOver(boolean allHumansEliminated) {
        Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();
        
        for (Helicopter helicopter : mHelicopters) {
           //TODO: retirez l'objet de l'écran 
            helicopter.setToDestroyed(true);
        }
        //TODO: Supprimez les hélicoptères réstants

    }
```

Vous aurez besoin de la fonction setToDestroyed() qui a pour but d’arrêter les animation qui sont encore en cours. Car le retrait de l'objet de l'écran, n'implique pas l'arrêt de l'animation de cet objet.
```
    public void setToDestroyed(boolean destroyed) {
        mDestroyed = destroyed;
        if (destroyed) {
            mAnimator.cancel();
        }
    }
```
Testez ces modifications et passez à l'étape suivante.

---
Maintenant il faut qu'on s'assure qu'un seul niveau peut être joué en même temps. On doit aussi changer le texte du bouton à chaque clique pour qu'il corresponde au contexte du jeu.
1. Déclarer une propriété `mGoButton` et référencez votre bouton au démarrage du jeu à l'aide de la méthode findViewByID().
2. Ajoutez deux propriétés `Boolean`, pour gérer les états du jeu à n'importe quel moment:
    - `mPlaying`: Indiquera si le joueur est entrain de jouer...
    - `mGameStopped=true`: Pendant que le joueur joue et entre les niveaux, elle serait à `false`. Quand le jeu termine (Game Over) elle sera remise à `true`. 
3. Localisez la boucle du jeu (dans la classe `HelicopterLauncher`) et modifiez sa condition de façon à ce qu'elle lance les hélicoptères que si le joueur est en état `mPlaying` et remplacez le `'3'` par une constante que vous déclariez `HELICOPTERS_PER_LEVEL` .
4. Créer une fonction startGame() qui démarrera le jeu et remettra à zéro les ressources du jeu :
```
private void startGame() {
        setToFullScreen();
        mScore = 0;
        mLevel = 0;
        mHumanKilled = 0;
        for (ImageView human : mHumanImages) {
            human.setImageResource(R.drawable.human);
        }
        startLevel();
    }
```
5. Complétez la logique des fonctions du jeu en vous basant sur les `TODO` de ce fichier : https://git.io/vMlej .

---
Maintenant après avoir effectué les modifications nécessaires pour gérer les états du jeu, Nous devons savoir combien d'hélicoptères ont été détruit ou disparus pour passer d'un niveau à l'autre et changer l'état du jeu.

Au niveau de la fonction `destroyHelicopter()`:
1. Ajoutez une proprieté `mHelicoptersGone` et incrémentez la.
2. Terminer le niveau si `mHelicoptersGone == HELICOPTERS_PER_LEVEL`.

Finalement, mettez à jour la fonction du clic du bouton qui, actuellement ne fait que démarrer le niveau, pour qu'elle fasse appel à ces nouvelles fonctions. 
```
public void goButtonClickHandler(View view) {

        if (mPlaying) {  //Le joueur est entrain de jouer et les hèlicoptères sont entrain de voler :D
            //TODO:  Question pour un champion: gameOver(false) , startGame() ou startLevel() ? 
        } else if (mGameStopped) {// le jeu est arrêté.
            //TODO:  Question pour un champion: startGame(), gameOver(false) ou startLevel() ? 
        } else {    //Le joueur vient de terminer un niveau
            //TODO:  Question pour un champion: startLevel(), startGame() ou gameOver(false) ? 
        }
    }
```
> Hashtags:
> `#premiere_Reponse.`

Et maintenant vous êtes prêts à exécuter l'application à nouveau et voir comment votre logique se comporte.

---
#### Bonus 
Chaque joueur aime voir, sauvegarder et se vanter de son score. On va donc ajouter un petit traitement pour garder trace du score.
Il existe beaucoup de solutions pour un stockage persistant. Pour les données complexes, vous pouvez utiliser SQLite, le moteur de base de données fourni avec le SDK Android, ou utiliser une autre bibliothèque de données persistantes comme Realm.
Pour ce TP on stockera juste le score le plus élevé dans le dispositif du joueur.

Créez une nouvelle classe `HighScoreHelper` dans le package `Utils` et copiez y ce code : https://git.io/vMlvm

Jusqu'à maintenant, dans la méthode `gameOver()`, on n'a pas encore utilisé le boolean `allHumansEliminated` qui s'ignifie, si égale à `true`, qu'on a quitté le jeu parce qu'on a perdu et pas car on l'a voulu. On stockera le score que si le joueur a perdu.
1. Mettez à jour votre fonction `gameOver()`:
```
 if (/* si le score actuel est supérieur à celui qui est stocké*/) {
               //stockez le
                SimpleAlertDialog dialog = SimpleAlertDialog.newInstance("New High Score!",
                        String.format("Congrats! The new high score is %d", mScore));
                dialog.show(getSupportFragmentManager(), null);
            }
```

Vous trouverez la classe `SimpleAlertDialog` ici : https://git.io/vMlvs

Testez votre code à nouveau et essayez de grimper en score.

---
## Audio
#### Background music
Les jeux utilisent souvent de la musique en arrière-plan pour améliorer l'expérience de jeu. 
1. Récupérez les deux fichiers .wav et .mp3. Copiez les, Créez un Android Ressource Directory, choisissez "raw" dans la liste déroulante *Resource Type* et cliquez sur ok. Ensuite faites un clique droit sur votre dossier "raw" et coller les deux fichiers.
2. Créez une nouvelle classe `SoundHelper` dans le package `Utils` et récupérez son contenu de ce lien : https://git.io/vMlvc . 
3. Rajoutez un objet `private SoundHelper mSoundHelper;` à votre `mainActivity`, initialisez le dans `onCreate`, dites lui de préparer son lecteur de média avec `prepareMusicPlayer()` (La ressource *musique.mp3* est fournie à l'intérieur de cette méthode).
4. Localisez la méthode `startGame()` et ajouter le bout de code pour lancer la musique (play).
5. Localisez la méthode `gameOver()` et ajouter le bout de code pour arrêter la musique.

Lancez votre jeu and enjoy the music.

---
#### Effets sonores
En plus de la musique, les jeux utilisent aussi les effets sonores pour rendre plus immersive l'environnement du jeu. ces effets sont généralement des fichiers audio .wav très courts. Ce type de fichier audio est géré par un composant Android nommé `SoundPool` qui fonctionne avec des fichiers audio qui ne pèsent pas plus d'un mégaoctet car ils sont stockés en mémoire dans un format non compressé pour les rendre instantanément accessibles.
1. Devinez ou vous devez appeler la méthode `mSoundHelper.playSound()` pour lancer le son d’explosion de l'hélicoptère.
2. appelez la!

> Hashtags:
> `#destroyHelicopter().`

#### Félicitation! Vous avez terminé le TP!
S'il vous reste encore le temps et vous êtes intéressés, demandez nous des suggestions pour rajouter des fonctionnalités au jeu. Sinon, bonne fin d'après-midi :) !

