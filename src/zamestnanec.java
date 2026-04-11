public abstract class zamestnanec {
        static int nextID;
        private int ID;
        private int YoB;
        private String name;
        private String surname;

        public static int getNextID() {
            return nextID;
        }

        public int getID() {
            return this.ID;
        }

        public int getYoB() {
            return this.YoB;
        }

        public String getName() {
            return this.name;
        }

        public String getSurname() {
            return this.surname;
        }

        public zamestnanec() {
            if(nextID == 0){
                this.ID = 1;
                nextID = this.ID + 1;
            } else {
                this.ID = nextID;
                nextID = this.ID + 1;
            }
        }
}
