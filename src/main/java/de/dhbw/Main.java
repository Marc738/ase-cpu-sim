package de.dhbw;

import de.dhbw.io.input.*;
import de.dhbw.io.inputconverter.InputConverter;
import de.dhbw.io.inputconverter.InputConverterImpl;
import de.dhbw.io.output.OutputHandler;
import de.dhbw.io.output.OutputHandlerImpl;
import de.dhbw.io.resulthandler.ResultHandler;
import de.dhbw.io.resulthandler.ResultHandlerImpl;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.alu.ALU;
import de.dhbw.units.control.ControlUnit;
import de.dhbw.units.control.storagemanager.StorageManager;
import de.dhbw.units.control.decoder.ArgDecoder;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.units.control.decoder.command.*;
import de.dhbw.units.register.Register;
import de.dhbw.utils.data.Word;

public class Main {

    public static void main(String[] args) {
        ProcessingUnit[] processingUnits = new ProcessingUnit[]{
                new ALU(),
                new Register(8)
        };
        Decoder decoder = new Decoder(new ArgDecoder(), new CommandDecoder[]{
                new AddCommandDecoder(),
                new SubtractCommandDecoder(),
                new SetCommandDecoder(),
                new GetCommandDecoder(),
                new StoreCommandDecoder(),
        });
        ControlUnit controlUnit = new ControlUnit(processingUnits, decoder, new StorageManager(), new Word());
        InputHandler inputHandler = new InputHandlerImpl();
        OutputHandler outputHandler = new OutputHandlerImpl();
        InputConverter inputConverter = new InputConverterImpl();
        ResultHandler resultHandler = new ResultHandlerImpl();
        CPUSimulator cpuSimulator = new CPUSimulator(processingUnits, controlUnit, inputHandler, outputHandler, inputConverter, resultHandler);
        cpuSimulator.run();
    }

}