import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HistoryManager {

    public HistoryManager() {

    }

    public void writeBuildResultToFile(String repositoryName, HistoryEntry historyEntry) throws IOException {
        String filePath = "history/" + repositoryName + ".json";
        File file = new File(filePath);
        Gson gson = new Gson();
        FileWriter writer;
        if (!file.exists()) {
            //Files.createDirectory();
            writer = new FileWriter(filePath);
        } else {
            writer = new FileWriter(filePath, true);
            writer.append("\n");
        }

        gson.toJson(historyEntry, writer);
        writer.close();
    }


}
