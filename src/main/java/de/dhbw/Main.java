package de.dhbw;

import de.dhbw.io.input.*;
import de.dhbw.units.ProcessingUnit;
import de.dhbw.units.alu.ALU;
import de.dhbw.units.control.ControlUnit;
import de.dhbw.units.control.StorageManager;
import de.dhbw.units.control.decoder.ArgDecoder;
import de.dhbw.units.control.decoder.Decoder;
import de.dhbw.units.control.decoder.command.*;
import de.dhbw.units.register.Register;

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
        ControlUnit controlUnit = new ControlUnit(processingUnits, decoder, new StorageManager());
        InputHandler inputHandler = new InputHandlerImpl();
        OutputHandler outputHandler = new OutputHandlerImpl();
        InputConverter inputConverter = new InputConverterImpl();
        CPUSimulator cpuSimulator = new CPUSimulator(processingUnits, controlUnit, inputHandler, outputHandler, inputConverter);
        cpuSimulator.run();
    }

}