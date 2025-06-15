package de.dhbw;

import de.dhbw.io.input.*;
import de.dhbw.io.inputconverter.InputConverter;
import de.dhbw.io.output.OutputHandler;
import de.dhbw.io.resulthandler.ResultHandler;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.utils.data.Command;
import de.dhbw.units.control.ControlUnit;
import de.dhbw.utils.result.Result;

public class CPUSimulator {

    private ControlUnit controlUnit;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;
    private InputConverter inputConverter;
    private ResultHandler resultHandler;

    private final String exitCommand = "exit";

    public CPUSimulator(ProcessingUnit[] processingUnits, ControlUnit controlUnit, InputHandler inputHandler, OutputHandler outputHandler, InputConverter inputConverter, ResultHandler resultHandler) {
        this.controlUnit = controlUnit;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.inputConverter = inputConverter;
        this.resultHandler = resultHandler;
    }

    public void run() {
        System.out.println("System up!");
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
            String text = resultHandler.process(processResult);
            outputHandler.print(text);
        }
    }

}
