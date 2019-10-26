import java.nio.file.Path;
import java.nio.file.Paths;

class test2 {

    public static void main(String args[]){

        int i=0;
        String filename="menu.jpg";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath());

    }

}