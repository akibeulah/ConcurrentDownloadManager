import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        final String FILE_PATH = "C:\\Users\\akibe\\Downloads\\Scott Pilgrim vs the World (2010) [1080p]\\Scott.Pilgrim.vs.the.World.2010.Bluray.1080p.x264.YIFY.mp4";

        System.out.println("Welcome to Beulah's Concurrent Download Manager!");
        System.out.println("Usage: BCDM -u <url>");

        BCDMContainer bcdmContainer = new BCDMContainer(new File(FILE_PATH));

        boolean splitFileSuccess = FileSplitter.splitFile(bcdmContainer);

        if (!splitFileSuccess)
            System.out.println("Directory failed to create!");
        else {
            if (FileMerger.mergeChunks(bcdmContainer))
                System.out.println("Download completed successfully!");
            else
                System.out.println("Error in download!");
        }
    }
}