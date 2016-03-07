package team14.project110;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {
    // current context
    Private Context currContext;
    // name of writefile
    String writeFile;

    // constructor to get write file
    public FileWriter (String w) {
        writeFile = w;
    }
    // Constructor for FileWriter
    public FileWriter (Context c) {
        this.currContext = c;
    }
    //Writes the users to the text
    public writeProfile (UserProfile u) {
        AssetManager asset = currContext.getAssets();
        try {
            OutputStream output = asset.open(path);
            OutputStreamWriter out = new OutputStreamWriter (output);
            out.write(u.name);
            for (Course c : u.myCourses) {
                out.write ("/");
                out.write (c.name);
            }
            out.close();
        }
        catch (IOException e) {
            e("Exception", "File write failed:  " + e.toString());
        }
        out.write("\e");

    }

    public writeCourses (Course c, boolean first) {
        try {
            OutputStreamWriter out = new OutputStreamWriter (context.openFileOutput(writeFile, Context.MODE_PRIVATE));
            if (first) {
                out.write (c.name);
            }
            else {
                out.write ("/");
                out.write (c.name);
            }

        }
        catch (IOException e) {
            e("Exception", "File write failed:  " + e.toString());
        }
    }


}