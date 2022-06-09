package tjd.mas.projectman.extent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExtentFile {

    public static Extent readExtent(){
        Extent extent = new Extent();
        try{
            FileInputStream fiStream = new FileInputStream("/Users/tjd/IdeaProjects/projectMan/appdata.dat");
            ObjectInputStream objectStream = new ObjectInputStream(fiStream);
            extent = (Extent) objectStream.readObject();
            fiStream.close();
            objectStream.close();
        }
        catch (Exception e){
            //nie obsługuję tego błędu świadomie - plik może nie istnieć
            //lub nie zawierać danych dających się deserializować i to jest ok
            //po prostu aplikacja wystartuje "pusta"
        }

        return extent;
    }

    public static void writeExtent(Extent extent){
        try {
            FileOutputStream fileOutput = new FileOutputStream("/Users/tjd/IdeaProjects/projectMan/appdata.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
            outputStream.writeObject(extent);
            fileOutput.close();
            outputStream.close();
        }
        catch (Exception e){
            System.out.println("Błąd zapisu danych.");
        }
    }
}
