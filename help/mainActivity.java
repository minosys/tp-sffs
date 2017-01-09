    private void startGame() {
        setToFullScreen();
        mScore = 0;
        mLevel = 0;
        mHumanKilled = 0;
        for (ImageView human : mHumanImages) {
            human.setImageResource(R.drawable.human);
        }
        //TODO: mGameStopped= ? 

        startLevel();
    }

    private void startLevel() {
        mLevel++;
        updateDisplay();
        HelicopterLauncher launcher = new HelicopterLauncher();
        launcher.execute(mLevel);
        //TODO: mPlaying= ? 
        mHelicoptersDestroyed = 0;
        //TODO: mettre à jour le texte du bouton à "Stop game"
    }

    private void finishLevel() {
        Toast.makeText(this, String.format("You finished level %d", mLevel),
                Toast.LENGTH_SHORT).show();
        //TODO: mPlaying= ? 
        //TODO: mettre à jour le texte du bouton à "Start level X": utilisez String.format()
    }

    private void gameOver(boolean allHumansEliminated) {
        Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();

        for (Helicopter helicopter : mHelicopters) {
            mContentView.removeView(helicopter);
            helicopter.setToDestroyed(true);
        }
        mHelicopters.clear();

        //TODO: mPlaying= ? 
        //TODO: mGameStopped= ? 
        //TODO: mettre à jour le texte du bouton à "Start game": utilisez String.format()

    }





