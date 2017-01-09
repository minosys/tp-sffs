    private class HelicopterLauncher extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int level = params[0];
            int maxDelay = Math.max(MIN_ANIMATION_DELAY,
                    (MAX_ANIMATION_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            int helicoptersLaunched = 0;
            while (helicoptersLaunched < 3) {
                //genere une position aleatoire de l'écran pour le prochain helicoptere
                Random random = new Random(new Date().getTime());
                int xPosition = random.nextInt(mScreenWidth - 200);
                publishProgress(xPosition);
                helicoptersLaunched++;

                //attend un petit moment avant de boucler
                int delay = random.nextInt(minDelay) + minDelay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int xPosition = values[0];
            int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (mLevel * 1000));
            launchHelicopter(xPosition,duration);
        }

    }