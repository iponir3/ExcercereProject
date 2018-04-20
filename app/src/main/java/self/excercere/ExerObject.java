package self.excercere;

import java.util.ArrayList;

/**
 * Created by iponi on 4/20/2018.
 */

public class ExerObject {


    public int Reps;
    public int Sets;
    public ArrayList<String> Exercises;

    public ExerObject(ArrayList<String> Exercises, int Reps, int Sets) {

        this.Exercises = Exercises;
        this.Reps = Reps;
        this.Sets = Sets;
    }
    public ExerObject() {

    }

}
