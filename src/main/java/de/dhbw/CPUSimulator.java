package de.dhbw;

import de.dhbw.io.input.*;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.control.Command;
import de.dhbw.units.control.ControlUnit;
import de.dhbw.units.control.StorageManager;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.utils.result.Result;

public class CPUSimulator {

    private ControlUnit controlUnit;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    private InputConverter inputConverter;

    private final String exitCommand = "exit";

    public CPUSimulator(ProcessingUnit[] processingUnits, ControlUnit controlUnit, InputHandler inputHandler, OutputHandler outputHandler, InputConverter inputConverter) {
        this.controlUnit = controlUnit;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.inputConverter = inputConverter;
    }

    public void run() {
        while(true) {
            String input = inputHandler.read();
            if(input.contentEquals(exitCommand)) {
                break;
            }
            if(input.contentEquals("")) {
                continue;
            }
            Command command = inputConverter.toCommand(input);
            Result<?> processResult = controlUnit.process(command);
            outputHandler.print(processResult);
        }
    }

}
