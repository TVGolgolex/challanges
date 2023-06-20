package de.pascxl.challanges.coloring;

import de.pascxl.challanges.utils.config.PaperConfigPaper;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pascal K. on 20.06.2023.
 */
@Getter
public class ColoringConfig extends PaperConfigPaper {

    private Map<String, Coloring> coloringMap = new HashMap<>();

    public ColoringConfig(File file) {
        this.CONFIG_FILE = file;
    }

    public void saveConfig() {
        try {
            this.save();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
