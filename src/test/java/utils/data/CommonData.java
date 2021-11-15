package utils.data;

import com.google.gson.Gson;
import testdata.user.UserDataObject;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommonData {

    public static UserDataObject buildUserDataObject(String jsonUserDataLocation) {
        return buildDataObjectFrom(jsonUserDataLocation, UserDataObject.class);
    }

    private static <T> T buildDataObjectFrom(String jsonDataObjectLocation, Class<T> dataType) {
        String currentLocation = System.getProperty("user.dir");
        try (
                Reader reader = Files.newBufferedReader(Paths.get(currentLocation + jsonDataObjectLocation));
        ) {
            Gson gson = new Gson();
            return gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
