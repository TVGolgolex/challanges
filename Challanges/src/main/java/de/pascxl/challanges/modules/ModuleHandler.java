package de.pascxl.challanges.modules;

import de.pascxl.challanges.modules.xp_border.XPBorder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pascal K. on 19.06.2023.
 */
@Getter
public class ModuleHandler {

    @Setter
    private IModule activeModule = null;

    private final List<IModule> modules = new ArrayList<>();

    public ModuleHandler() {
        this.modules.add(new XPBorder());
    }

    public <T extends IModule> T getActiveModule() {
        return (T) activeModule;
    }

}
