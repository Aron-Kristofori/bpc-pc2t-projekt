package people;

public abstract class zamestnanec {
        static int nextID;
        private int ID;
        private int YoB;
        private String name;
        private String surname;

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

        public zamestnanec(int YoB, String name, String surname) {
            if(nextID == 0){
                this.ID = 1;
                nextID = this.ID + 1;
            } else {
                this.ID = nextID;
                nextID = this.ID + 1;
            }
            this.YoB = YoB;
            this.name = name;
            this.surname = surname;
        }

}
