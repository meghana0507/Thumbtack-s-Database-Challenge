import java.util.*;

/**
 * Created by Meghana on 3/3/2016.
 */

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input;

        int numCounter=0;
        int beginCounter = 0;

        ArrayList<Database> objects = new ArrayList<>();


        boolean exit = false;

        while (!exit) {
            input = sc.nextLine();
            String[] token = input.split(" ");

            if (token[0].equals("BEGIN")) {

                beginCounter++;
                for(int i=0; i<objects.size(); i++)
                {
                    objects.get(i).rollBack.push(objects.get(i).curr_value);
                }
            }

            else if(token[0].equals("SET"))
            {

                boolean found = false;
                int index = -1;
                for(int i=0; i < objects.size(); i++) {
                    if (objects.get(i).key.equals(token[1])){
                        found = true;
                        index = i;
                    }
                }
                if(found)
                {
                    objects.get(index).curr_value = token[2];

                    if(beginCounter == 0)
                    {
                        objects.get(index).commit_value = token[2];
                    }
                }

                else {
                    Database temp = new Database(token[1]);
                    temp.curr_value = token[2];

                    if(beginCounter == 0)
                    {
                        temp.commit_value = token[2];
                    }

                    objects.add(temp);

                }
            }

            else if(token[0].equals("UNSET"))
            {
                boolean found = false;
                int index = -1;
                for(int i=0; i < objects.size(); i++) {
                    if (objects.get(i).key.equals(token[1])){
                        found = true;
                        index = i;
                    }
                }
                if(found)
                {
                    objects.get(index).curr_value = "NULL";

                    if(beginCounter==0) {
                        objects.get(index).commit_value = "NULL";
                    }
                }

            }

            else if(token[0].equals("ROLLBACK"))
            {
                if(beginCounter==0)
                {
                    System.out.println("NO TRANSACTION");
                }

                else {
                    for (int i = 0; i < objects.size(); i++) {
                        if(objects.get(i).rollBack.isEmpty())
                            objects.get(i).curr_value = "NULL";
                        else
                            objects.get(i).curr_value = objects.get(i).rollBack.pop();
                    }
                    beginCounter--;
                }
            }

            else if(token[0].equals("COMMIT"))
            {
                if(beginCounter==0)
                {
                    System.out.println("NO TRANSACTION");
                }

                else {
                    for (int i = 0; i < objects.size(); i++) {

                        objects.get(i).commit_value = objects.get(i).curr_value; //this is the final committed value
                        objects.get(i).rollBack = new Stack<>(); //initializing rollBack since all transactions are committed

                        //The above commit value can be permanently saved into a database.
                    }
                    beginCounter = 0;
                }

            }

            else if(token[0].equals("GET"))
            {
                boolean found = false;
                int index = -1;
                for(int i=0; i < objects.size(); i++) {
                    if (objects.get(i).key.equals(token[1])){
                        found = true;
                        index = i;
                    }
                }

                if(found) {

                    if(beginCounter==0)
                    {
                        System.out.println(objects.get(index).commit_value);
                    }

                    else{
                        System.out.println(objects.get(index).curr_value);
                    }
                }

                else
                    System.out.println("NULL");

            }

            else if(token[0].equals("NUMEQUALTO"))
            {
                numCounter = 0;
                for(int i=0; i<objects.size(); i++)
                {
                    if(objects.get(i).curr_value.equals(token[1]))
                        numCounter++;
                }

                System.out.println(numCounter);
            }

            else if(input.equals("END"))
            {
                exit = true;
            }
        }
    }
}
