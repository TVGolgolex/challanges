package de.pascxl.challanges.coloring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Created by Pascal K. on 20.06.2023.
 */
@Getter
@AllArgsConstructor
public class Coloring {

    private final String firstGradientColor;
    private final String secondGradientColor;
    private final NamedTextColor namedTextColor;

}
