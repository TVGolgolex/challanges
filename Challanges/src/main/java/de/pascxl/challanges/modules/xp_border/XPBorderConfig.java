package de.pascxl.challanges.modules.xp_border;

import com.sun.source.doctree.SeeTree;
import de.pascxl.challanges.utils.config.PaperConfigPaper;
import de.pascxl.challanges.utils.config.YamlConfigPaper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Pascal K. on 20.06.2023.
 */
@Getter
@Setter
public class XPBorderConfig extends PaperConfigPaper {

    private long timerSeconds;

    private double exp;

    private int distance;

    private HashMap<String, String> inventory = new HashMap<>();

    public XPBorderConfig(File file) {
        this.CONFIG_FILE = file;
    }
}
