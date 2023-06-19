package de.pascxl.challanges.modules;

/**
 * Created by Pascal K. on 19.06.2023.
 */
public interface IModule {

    void initial();

    void setTimerPause(boolean b);

    void setTimerSeconds(long l);

    boolean isTimerPause();

    void terminate();

}
